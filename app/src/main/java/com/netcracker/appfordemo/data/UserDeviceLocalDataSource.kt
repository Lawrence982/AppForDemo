package com.netcracker.appfordemo.data

import com.netcracker.appfordemo.uimodel.UserDevice
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import java.util.concurrent.TimeUnit

/**
 * Created by mozil on 21.01.2018.
 */
class UserDeviceLocalDataSource {
    var arrayList = ArrayList<UserDevice>()
    fun getUserDevices() : Observable<ArrayList<UserDevice>> {

        return Observable.just(arrayList).delay(2, TimeUnit.SECONDS)
    }

    fun saveRepositories(_arrayList: ArrayList <UserDevice>): Completable {
        arrayList = _arrayList;
        return Single.just(arrayList).delay(1, TimeUnit.SECONDS).toCompletable ()
    }

}