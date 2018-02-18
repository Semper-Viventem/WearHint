package com.example.konstantin.weartest.ui.common

import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import io.reactivex.disposables.CompositeDisposable
import me.dmdev.rxpm.AndroidPmView
import me.dmdev.rxpm.PresentationModel
import me.dmdev.rxpm.delegate.PmActivityDelegate


abstract class BaseWearPmActivity<PM : PresentationModel> : WearableActivity(), AndroidPmView<PM> {

    private val delegate by lazy { PmActivityDelegate(this) }

    final override val compositeUnbind = CompositeDisposable()

    final override val presentationModel get() = delegate.presentationModel

    protected abstract val activityLayout: Int

    open fun onInitView(savedInstanceState: Bundle?) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityLayout)
        delegate.onCreate(savedInstanceState)
        onInitView(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        delegate.onStart()
    }

    override fun onResume() {
        super.onResume()
        delegate.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        delegate.onSaveInstanceState(outState)
    }

    override fun onPause() {
        super.onPause()
        delegate.onPause()
    }

    override fun onStop() {
        super.onStop()
        delegate.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        delegate.onDestroy()
    }
}