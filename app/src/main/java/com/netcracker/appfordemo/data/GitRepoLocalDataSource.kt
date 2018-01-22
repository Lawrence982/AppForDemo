package com.netcracker.appfordemo.data

import com.netcracker.appfordemo.uimodel.Repository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import java.util.concurrent.TimeUnit

/**
 * Created by mozil on 19.01.2018.
 */

class GitRepoLocalDataSource {
    var arrayList = ArrayList<Repository>()
    fun getRepositories() : Observable<ArrayList<Repository>> {

        arrayList.add(Repository("First From Local", "Owner 1", 100, false))
        arrayList.add(Repository("Second From Local", "Owner 2", 30, true))
        arrayList.add(Repository("Third From Local", "Owner 3", 430, false))

        return Observable.just(arrayList).delay(2, TimeUnit.SECONDS)
    }

    fun saveRepositories(_arrayList: ArrayList <Repository>): Completable {
        arrayList = _arrayList
        return Single.just(arrayList).delay(1, TimeUnit.SECONDS).toCompletable ()
    }
}