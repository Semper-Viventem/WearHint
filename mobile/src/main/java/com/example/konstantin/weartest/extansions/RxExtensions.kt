package com.example.konstantin.weartest.extansions

import com.google.android.gms.tasks.Task
import io.reactivex.Single

/**
 * @author Kulikov Konstantin
 * @since 21.01.2018.
 */

fun <T : Any?> Task<T>.successToSingle() = Single.create<T> { emitter ->
    addOnSuccessListener { emitter.onSuccess(it) }
    addOnFailureListener { emitter.onError(it) }
}

fun <T : Any?> Task<T>.completeToSingle() = Single.create<T> { emitter ->
    addOnCompleteListener {
        if (it.exception != null) emitter.onError(it.exception!!) else emitter.onSuccess(it.result)
    }
}