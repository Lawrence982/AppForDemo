package com.netcracker.appfordemo.extensions

/**
 * Created by mozil on 19.01.2018.
 */
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

operator fun CompositeDisposable.plusAssign(disposable: Disposable) {
    add(disposable)
}