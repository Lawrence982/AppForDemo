package com.netcracker.appfordemo.data

import com.netcracker.appfordemo.uimodel.Repository
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

/**
 * Created by mozil on 19.01.2018.
 */

class GitRepoRemoteDataSource {

    fun getRepositories() : Observable<ArrayList<Repository>> {
        var arrayList = ArrayList<Repository>()


        return Observable.just(arrayList).delay(2,TimeUnit.SECONDS)
    }
}