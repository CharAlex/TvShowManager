package com.alexchar.tvshowmanager.presentation.util

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.immutable(): LiveData<T> = this

fun <T : Any?, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T?) -> Unit) =
    liveData.observe(this, { body(it) })

fun <Param> ((Param) -> Unit).skipNull(): (Param?) -> Unit = { param ->
    if (param != null) {
        invoke(param)
    }
}
