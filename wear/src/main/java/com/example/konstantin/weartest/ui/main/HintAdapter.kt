package com.example.konstantin.weartest.ui.main

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.konstantin.weartest.R
import com.example.konstantin.weartest.domain.dto.Hint
import com.example.konstantin.weartest.extansions.inflate
import com.example.konstantin.weartest.ui.common.BaseListAdapter
import kotlinx.android.synthetic.main.item_hint.view.*


class HintAdapter(
    private val itemSelected: (item: Hint) -> Unit
) : BaseListAdapter<Hint, HintAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.inflate(R.layout.item_hint))

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), BaseListAdapter.Bindable<Hint> {

        private lateinit var item: Hint

        init {
            itemView.setOnClickListener { itemSelected.invoke(item) }
        }

        override fun bind(item: Hint) {
            this.item = item

            itemView.text.text = item.title
        }
    }
}