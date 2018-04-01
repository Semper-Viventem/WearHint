package com.example.konstantin.weartest.ui.hintitem.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.konstantin.weartest.R
import com.example.konstantin.weartest.extensions.inflate
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate
import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter
import kotlinx.android.synthetic.main.item_add_hint.view.*
import kotlinx.android.synthetic.main.item_hint_image.view.*
import kotlinx.android.synthetic.main.item_hint_text.view.*
import kotlinx.android.synthetic.main.item_hint_title.view.*
import ru.semper_viventem.common.dto.BaseHintItem


class HintItemsAdapter(
    private val onItemSelected: (position: Int) -> Unit,
    private val onAddTitle: () -> Unit,
    private val onAddText: () -> Unit,
    private val onAddImage: () -> Unit
) : ListDelegationAdapter<List<BaseHintItem>>() {

    private var isEditMode: Boolean = false

    init {
        items = emptyList()
        with(delegatesManager) {
            addDelegate(HintTitleDelegate())
            addDelegate(HintTextDelegate())
            addDelegate(HintImageDelegate())
            addDelegate(EditModeDelegate(onAddTitle, onAddText, onAddImage))
        }
    }

    override fun getItemCount(): Int = if (isEditMode) items.size + 1 else items.size

    private val editBlockPosition: Int get() = items.lastIndex + 1

    override fun setItems(items: List<BaseHintItem>) {
        super.setItems(items)
        notifyDataSetChanged()
    }

    fun setEditMode(isEditMode: Boolean) {
        this.isEditMode = isEditMode
        if (isEditMode) notifyItemInserted(editBlockPosition) else notifyItemRemoved(editBlockPosition)
    }

    /**
     * Делегат для картинок
     */
    private inner class HintImageDelegate : AdapterDelegate<List<BaseHintItem>>() {
        override fun isForViewType(items: List<BaseHintItem>, position: Int): Boolean = position < items.size && items[position] is BaseHintItem.ImageHintItem

        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
            ViewHolder(parent.inflate(R.layout.item_hint_image))

        override fun onBindViewHolder(items: List<BaseHintItem>, position: Int, holder: RecyclerView.ViewHolder, payloads: MutableList<Any>) {
            (holder as ViewHolder).bind(items[position] as BaseHintItem.ImageHintItem)
        }

        private inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

            fun bind(item: BaseHintItem.ImageHintItem) {

                itemView.setOnClickListener { if (isEditMode) onItemSelected.invoke(adapterPosition) }

                Glide.with(itemView)
                    .load(item.uri)
                    .into(itemView.image)
            }
        }
    }

    /**
     * Делегат для текста
     */
    private inner class HintTextDelegate : AdapterDelegate<List<BaseHintItem>>() {
        override fun isForViewType(items: List<BaseHintItem>, position: Int): Boolean = position < items.size && items[position] is BaseHintItem.TextHintItem

        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
            ViewHolder(parent.inflate(R.layout.item_hint_text))

        override fun onBindViewHolder(items: List<BaseHintItem>, position: Int, holder: RecyclerView.ViewHolder, payloads: MutableList<Any>) {
            (holder as ViewHolder).bind(items[position] as BaseHintItem.TextHintItem)
        }

        private inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            fun bind(item: BaseHintItem.TextHintItem) {

                itemView.setOnClickListener { if (isEditMode) onItemSelected.invoke(adapterPosition) }

                itemView.text.text = item.text
            }
        }
    }

    /**
     * Делегат для заголовков
     */
    private inner class HintTitleDelegate : AdapterDelegate<List<BaseHintItem>>() {
        override fun isForViewType(items: List<BaseHintItem>, position: Int): Boolean = position < items.size && items[position] is BaseHintItem.TitleHintItem

        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
            ViewHolder(parent.inflate(R.layout.item_hint_title))

        override fun onBindViewHolder(items: List<BaseHintItem>, position: Int, holder: RecyclerView.ViewHolder, payloads: MutableList<Any>) {
            (holder as ViewHolder).bind(items[position] as BaseHintItem.TitleHintItem)
        }

        private inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            fun bind(item: BaseHintItem.TitleHintItem) {

                itemView.setOnClickListener { if (isEditMode) onItemSelected.invoke(adapterPosition) }

                itemView.title.text = item.title
            }
        }
    }

    /**
     * Делегат нижнего блока для добавления новых элементов
     */
    private inner class EditModeDelegate(
        private val onAddTitle: () -> Unit,
        private val onAddText: () -> Unit,
        private val onAddImage: () -> Unit
    ) : AdapterDelegate<List<BaseHintItem>>() {

        override fun isForViewType(items: List<BaseHintItem>, position: Int): Boolean = position == editBlockPosition && isEditMode

        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = ViewHolder(parent.inflate(R.layout.item_add_hint))

        override fun onBindViewHolder(items: List<BaseHintItem>, position: Int, holder: RecyclerView.ViewHolder, payloads: MutableList<Any>) {
            // do nothing
        }

        private inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            init {
                itemView.addImage.setOnClickListener { onAddImage.invoke() }
                itemView.addText.setOnClickListener { onAddText.invoke() }
                itemView.addTitle.setOnClickListener { onAddTitle.invoke() }
            }
        }
    }
}