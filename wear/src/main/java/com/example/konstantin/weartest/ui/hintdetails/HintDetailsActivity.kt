package com.example.konstantin.weartest.ui.hintdetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.example.konstantin.weartest.R
import com.example.konstantin.weartest.ui.UiModule
import com.example.konstantin.weartest.ui.common.BaseWearPmActivity
import com.example.konstantin.weartest.ui.image.ImageActivity
import kotlinx.android.synthetic.main.activity_hint.*
import org.koin.standalone.StandAloneContext
import ru.semper_viventem.common.dto.Hint

/**
 * @author Kulikov Konstantin
 * @since 21.01.2018.
 */
class HintDetailsActivity: BaseWearPmActivity<HintDetailsPm>() {

    companion object {
        private const val EXTRA_HINT = "extra_hint"
        fun getIntent(context: Context, hint: Hint) = Intent(context, HintDetailsActivity::class.java)
            .putExtra(EXTRA_HINT, hint)
    }

    override val activityLayout: Int = R.layout.activity_hint

    override fun providePresentationModel(): HintDetailsPm = StandAloneContext
        .koinContext
        .apply { setProperty(UiModule.HINT_PROPERTY, intent.getParcelableExtra(EXTRA_HINT)) }
        .get()

    private val hintAdapter = HintItemsAdapter {
        startActivity(ImageActivity.getIntent(this, it.uri))
    }

    override fun onInitView(savedInstanceState: Bundle?) {
        super.onInitView(savedInstanceState)

        with(recyclerView) {
            setHasFixedSize(true)
            isEdgeItemsCenteringEnabled = true
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL).apply {
                setDrawable(resources.getDrawable(R.drawable.decorator, null))
            })
            adapter = hintAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onBindPresentationModel(pm: HintDetailsPm) {
        pm.hintItems.observable bindTo { hintAdapter.items = it }
    }
}