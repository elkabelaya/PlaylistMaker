package com.example.playlistmaker.common.presentation.utils

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import kotlin.concurrent.atomics.AtomicBoolean
import kotlin.concurrent.atomics.ExperimentalAtomicApi

class SingleLiveData<T>(value: T) : MutableLiveData<T>(value) {
    @OptIn(ExperimentalAtomicApi::class)
    private val mPending = AtomicBoolean(false)

    @OptIn(ExperimentalAtomicApi::class)
    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner) { t ->
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        }
    }
    @OptIn(ExperimentalAtomicApi::class)
    @MainThread
    override fun setValue(t: T?) {
        mPending.store(true)
        super.setValue(t)
    }
}