package com.example.konstantin.weartest.extensions

import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction

/**
 * Extensions to made navigation easy.
 * @author Dmitriy Gorbunov
 */

fun Router.goTo(screen: Controller) {
    pushController(
            RouterTransaction.with(screen)
                .tag(screen::class.java.name)
    )
}

fun Router.setRoot(screen: Controller) {
    setRoot(
            RouterTransaction.with(screen)
                .tag(screen::class.java.name)
    )
}

fun Router.back(): Boolean {
    return if (backstackSize > 1) {
        popCurrentController()
        true
    } else {
        false
    }
}

inline fun <reified T : Controller> Router.backTo() {
    if (backstackSize > 1) {
        popToTag(T::class.java.name)
    }
}