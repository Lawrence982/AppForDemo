package com.netcracker.appfordemo.api

/**
 * Created by mozil on 20.01.2018.
 */
object SearchUserDeviceProvider {
    fun provideSearchUserDevice(): SearchUserDevice {
        return SearchUserDevice(UserDeviceService.getUserDeviceService())
    }
}