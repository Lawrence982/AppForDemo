package com.netcracker.appfordemo

import android.app.Activity
import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableField
import android.os.Debug
import android.util.Log
import com.netcracker.appfordemo.data.GitRepoRepository
import com.netcracker.appfordemo.data.UserDeviceRepository
import com.netcracker.appfordemo.uimodel.UserDevice
import com.netcracker.appfordemo.extensions.plusAssign
import com.netcracker.appfordemo.managers.NetManager
import com.netcracker.appfordemo.uimodel.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

/**
 * Created by mozil on 19.01.2018.
 */

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var gitRepoRepository: GitRepoRepository = GitRepoRepository(NetManager(getApplication()))

    private var userDeviceRepository: UserDeviceRepository = UserDeviceRepository(NetManager(getApplication()))

    val text = ObservableField("old data")

    val isLoading = ObservableField(false)

    var repositories = MutableLiveData<ArrayList<Repository>>()

    var userDevices = MutableLiveData<ArrayList<UserDevice>>()

    private val compositeDisposable = CompositeDisposable()

    var parent_id: String? = null

    fun loadRepositories() {
        isLoading.set(true)
        compositeDisposable += gitRepoRepository
                .getRepositories()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<ArrayList<Repository>>() {

                    override fun onError(e: Throwable) {
                        //if some error happens in our data layer our app will not crash, we will
                        // get error here
                    }

                    override fun onNext(data: ArrayList<Repository>) {
                        repositories.value = data
                    }

                    override fun onComplete() {
                        isLoading.set(false)
                    }
                })
    }

    fun loadUserDevices(parent_id: String) {
        isLoading.set(true)
        compositeDisposable += userDeviceRepository
                .getGroups(parent_id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<ArrayList<UserDevice>>() {

                    override fun onComplete() {
                        isLoading.set(false)
                    }

                    override fun onNext(data: ArrayList<UserDevice>) {
//                        if (userDevices.value==null)
//                            userDevices.value=ArrayList<UserDevice>()
//                        for (device : UserDevice in data)
//                        userDevices.value?.add(device)
                        userDevices.value=data
                    }

                    override fun onError(e: Throwable) {
                        Log.d("Error", e.stackTrace.toString())
                    }
                })
    }

    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    init {
        Log.d("hue", "isLong")
    }
}