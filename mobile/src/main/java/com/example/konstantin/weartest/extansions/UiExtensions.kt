package com.example.konstantin.weartest.extansions

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Point
import android.os.Build
import android.support.annotation.ColorRes
import android.support.annotation.LayoutRes
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v4.graphics.drawable.TintAwareDrawable
import android.text.Html
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView


fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun Context.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(this).inflate(layoutRes, null, attachToRoot)
}

fun View.visible(visible: Boolean, useGone: Boolean = true) {
    this.visibility = if (visible) View.VISIBLE else if (useGone) View.GONE else View.INVISIBLE
}

fun Resources.color(colorRes: Int) =
    if (Build.VERSION.SDK_INT >= 23) {
        this.getColor(colorRes, null)
    } else {
        this.getColor(colorRes)
    }

fun ImageView.tintSrc(@ColorRes colorRes: Int) {
    val drawable = DrawableCompat.wrap(drawable)
    DrawableCompat.setTint(drawable, ContextCompat.getColor(context, colorRes))
    setImageDrawable(drawable)
    if (drawable is TintAwareDrawable) invalidate() // Because in this case setImageDrawable will not call invalidate()
}

fun Activity.getScreenHeight(): Int {
    val size = Point()
    windowManager.defaultDisplay.getSize(size)
    return size.y
}

fun View.showKeyboard(show: Boolean) {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if (show) {
        if (requestFocus()) imm.showSoftInput(this, 0)
    } else {
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}

fun spannedFromHtml(source: String): Spanned =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(source, Html.FROM_HTML_MODE_COMPACT)
    } else {
        Html.fromHtml(source)
    }

fun TextView.setTextAppearanceCompat(res: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        setTextAppearance(res)
    } else {
        setTextAppearance(context, res)
    }
}
