package com.netcracker.appfordemo.data

import com.netcracker.appfordemo.uimodel.UserDevice
import com.netcracker.appfordemo.managers.NetManager
import io.reactivex.Observable

/**
 * Created by mozil on 21.01.2018.
 */
class UserDeviceRepository(private val netManager: NetManager) {

    private val localDataSource = UserDeviceLocalDataSource()
    private val remoteDataSource = UserDeviceRemoteDataSource()

    fun getGroups(parent_id: String): Observable<ArrayList<UserDevice>> {

        netManager.isConnectedToInternet?.let {
            if (it) {
                return remoteDataSource.getUserDevices(parent_id).flatMap {
                    return@flatMap localDataSource.saveRepositories(it)
                            .toSingleDefault(it)
                            .toObservable()
                }
            }
        }

        return localDataSource.getUserDevices()
    }
}
