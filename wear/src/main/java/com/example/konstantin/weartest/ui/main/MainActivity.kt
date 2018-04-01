package com.example.konstantin.weartest.ui.main

import android.os.Bundle
import android.support.wear.widget.WearableLinearLayoutManager
import com.example.konstantin.weartest.R
import com.example.konstantin.weartest.extensions.visible
import com.example.konstantin.weartest.ui.common.BaseWearPmActivity
import com.example.konstantin.weartest.ui.hintdetails.HintDetailsActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.standalone.StandAloneContext

class MainActivity : BaseWearPmActivity<MainPm>() {

    override val activityLayout: Int = R.layout.activity_main

    override fun providePresentationModel(): MainPm = StandAloneContext.koinContext.get()

    private val myAdapter = HintAdapter { item ->
        startActivity(HintDetailsActivity.getIntent(this, item))
    }

    override fun onInitView(savedInstanceState: Bundle?) {

        with(recyclerView) {
            setHasFixedSize(true)
            isEdgeItemsCenteringEnabled = true
            layoutManager = WearableLinearLayoutManager(context)
            adapter = myAdapter
        }

        // Enables Always-on
        setAmbientEnabled()
    }

    override fun onBindPresentationModel(pm: MainPm) {
        pm.hints.observable bindTo { myAdapter.setItems(it) }
        pm.progress.observable bindTo { progress.visible(it) }
    }
}
