package com.netcracker.appfordemo.api

import com.netcracker.appfordemo.uimodel.UserDevice
import io.reactivex.Observable

/**
 * Created by mozil on 20.01.2018.
 */
class SearchUserDevice(val apiService: UserDeviceService?) {
    fun searchUserDevices(parentId: String): Observable<List<UserDevice>> {
        return apiService!!.getUserDevices(parentId)
    }
}