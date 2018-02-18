package com.example.konstantin.weartest.ui.hintitem

import android.net.Uri
import com.example.konstantin.weartest.domain.SaveHintInteractor
import com.example.konstantin.weartest.ui.common.ScreenPm
import me.dmdev.rxpm.widget.inputControl
import ru.semper_viventem.common.dto.BaseHintItem
import ru.semper_viventem.common.dto.Hint
import timber.log.Timber


class HintItemPm(
    private val hint: Hint,
    private val editable: Boolean,
    private val saveHintInteractor: SaveHintInteractor
) : ScreenPm() {

    val isEditMode = State(false)
    val items = State<List<BaseHintItem>>(hint.items)
    val name = inputControl()
    val toolbarTitle = State<String>(hint.title)
    val addNewImage = Action<Uri>()
    val addNewText = Action<String>()
    val addNewTitle = Action<String>()
    val removeItem = Action<Int>()
    val setEditMode = Action<Unit>()
    val saveHint = Action<Unit>()
    val savingError = Command<Unit>()

    override fun onCreate() {
        super.onCreate()

        isEditMode.consumer.accept(editable)
        name.text.consumer.accept(hint.title)

        name.textChanges.observable
            .subscribe { hint.title = it }
            .untilDestroy()

        saveHint.observable
            .filter { hint.items.isNotEmpty() && name.text.value.isNotBlank() }
            .subscribe {
                saveHintInteractor.execute(hint)
                    .subscribe(
                            {
                                isEditMode.consumer.accept(false)
                                toolbarTitle.consumer.accept(hint.title)
                            },
                            {
                                Timber.e(it)
                                savingError.consumer.accept(Unit)
                            }
                    )
            }
            .untilDestroy()

        setEditMode.observable
            .subscribe {
                isEditMode.consumer.accept(true)
                name.text.consumer.accept(hint.title)
                toolbarTitle.consumer.accept("")
            }
            .untilDestroy()

        removeItem.observable
            .subscribe {
                hint.items.removeAt(it)
                items.consumer.accept(hint.items)
            }
            .untilDestroy()

        addNewText.observable
            .filter { it.isNotBlank() }
            .subscribe {
                hint.items.add(BaseHintItem.TextHintItem(0, it))
                items.consumer.accept(hint.items)
            }
            .untilDestroy()

        addNewTitle.observable
            .filter { it.isNotBlank() }
            .subscribe {
                hint.items.add(BaseHintItem.TitleHintItem(0, it))
                items.consumer.accept(hint.items)
            }
            .untilDestroy()

        addNewImage.observable
            .subscribe {
                hint.items.add(BaseHintItem.ImageHintItem(0, it.toString()))
                items.consumer.accept(hint.items)
            }
            .untilDestroy()

    }
}