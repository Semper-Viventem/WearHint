package ru.semper_viventem.common

import com.google.android.gms.tasks.Task
import io.reactivex.Completable
import io.reactivex.Single

/**
 * @author Kulikov Konstantin
 * @since 21.01.2018.
 */

fun <T : Any?> Task<T>.toCompletable() = Completable.create { emitter ->
    addOnCompleteListener { if (it.exception != null) emitter.onError(it.exception!!) else emitter.onComplete() }
}

fun <T : Any?> Task<T>.toSingle(fromComplete: Boolean = true) = Single.create<T> { emitter ->
    if (fromComplete) {
        addOnCompleteListener {
            if (it.exception != null) emitter.onError(it.exception!!) else emitter.onSuccess(it.result)
        }
    } else {
        addOnSuccessListener { emitter.onSuccess(it) }
        addOnFailureListener { emitter.onError(it) }
    }
}