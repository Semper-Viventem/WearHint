package com.example.konstantin.weartest.ui.hintlist

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.example.konstantin.weartest.R
import com.example.konstantin.weartest.extensions.visible
import com.example.konstantin.weartest.ui.common.Screen
import kotlinx.android.synthetic.main.screen_hint_list.view.*
import kotlinx.android.synthetic.main.toolbar.view.*
import org.koin.standalone.StandAloneContext


class HintListScreen : Screen<HintListPm>() {

    override val screenLayout: Int = R.layout.screen_hint_list

    override fun providePresentationModel(): HintListPm = StandAloneContext.koinContext.get()

    private val hintAdapter = HintAdapter { item ->
        item passTo presentationModel.hintSelected.consumer
    }

    override fun onInitView(view: View, savedViewState: Bundle?) {
        super.onInitView(view, savedViewState)

        view.toolbar.setTitle(R.string.app_name)
        with(view.recyclerView) {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = hintAdapter
            setHasFixedSize(true)
        }
    }

    override fun onBindPresentationModel(view: View, pm: HintListPm) {
        pm.error.observable bindTo { Toast.makeText(context!!, R.string.error_with_loading, Toast.LENGTH_SHORT).show() }
        pm.progress.observable bindTo { view.progress.visible(it) }
        pm.hints.observable bindTo { hintAdapter.setItems(it) }
        pm.addHintSelected bindTo view.fab
    }
}