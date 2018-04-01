package com.example.konstantin.weartest.ui.common

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router
import com.example.konstantin.weartest.extensions.setRoot
import io.reactivex.disposables.CompositeDisposable
import me.dmdev.rxpm.AndroidPmView
import me.dmdev.rxpm.PresentationModel
import me.dmdev.rxpm.delegate.PmActivityDelegate


abstract class BasePmActivity<PM : PresentationModel> : AppCompatActivity(), AndroidPmView<PM> {

    private val delegate by lazy { PmActivityDelegate(this) }

    final override val compositeUnbind = CompositeDisposable()

    final override val presentationModel get() = delegate.presentationModel

    protected abstract val activityLayout: Int
    protected abstract val containerId: Int

    protected lateinit var router: Router

    protected abstract val rootScreen: Controller?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityLayout)
        delegate.onCreate(savedInstanceState)
        router = Conductor.attachRouter(this, findViewById(containerId), savedInstanceState)

        rootScreen?.let { root ->
            if (!router.hasRootController()) {
                router.setRoot(root)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        router.onActivityResult(requestCode, resultCode, data)
    }

    override fun onBackPressed() {
        if (!router.handleBack()) {
            super.onBackPressed()
        }
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