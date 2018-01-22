package com.netcracker.appfordemo.data

import android.content.Context
import android.os.Handler
import com.netcracker.appfordemo.managers.NetManager
import com.netcracker.appfordemo.uimodel.Repository
import io.reactivex.Observable

/**
 * Created by mozil on 19.01.2018.
 */
class GitRepoRepository(private val netManager: NetManager) {

    private val localDataSource = GitRepoLocalDataSource()
    private val remoteDataSource = GitRepoRemoteDataSource()

    fun getRepositories (): Observable <ArrayList <Repository >> {

        netManager.isConnectedToInternet?.let {
            if (it) {
                return remoteDataSource.getRepositories().flatMap {
                    return@flatMap localDataSource.saveRepositories(it)
                            .toSingleDefault(it)
                            .toObservable()
                }
            }
        }

        return localDataSource.getRepositories ()
    }
}
