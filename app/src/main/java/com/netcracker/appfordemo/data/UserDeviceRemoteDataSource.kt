package com.netcracker.appfordemo.data

import com.netcracker.appfordemo.api.SearchUserDeviceProvider
import com.netcracker.appfordemo.uimodel.UserDevice
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by mozil on 21.01.2018.
 */
class UserDeviceRemoteDataSource {
    var arrayList = ArrayList<UserDevice>()
    fun getUserDevices(parent_id: String) : Observable<ArrayList<UserDevice>> {

        val repository = SearchUserDeviceProvider.provideSearchUserDevice()
         repository.searchUserDevices(parent_id)
                 .observeOn(AndroidSchedulers.mainThread())
                 .subscribeOn(Schedulers.io())
                 .subscribe(this::handleGroups, this::handleError);

        return Observable.just(arrayList).delay(2, TimeUnit.SECONDS)
    }


    internal fun handleGroups(groups: List<UserDevice>) {
        arrayList.clear()
        arrayList.addAll(groups)

    }


    internal fun handleError(throwable: Throwable) {
        arrayList.clear()
    }
}