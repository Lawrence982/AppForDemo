package com.netcracker.appfordemo.api

import android.support.annotation.NonNull
import com.netcracker.appfordemo.uimodel.UserDevice
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Headers


/**
 * Created by mozil on 20.01.2018.
 */
interface UserDeviceService {



    @Headers("Authorization: Trusted application=\"eShop\", username=\"9149925907113305697\"")
    @GET("/api/v1/portal/get/devices/{parentId}")
    fun getUserDevices(@Path("parentId") @NonNull parentId : String): Observable<List<UserDevice>>


    companion object Factory {
        var sService: UserDeviceService? = null

        fun create(): UserDeviceService {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("http://10.106.2.128:6957/")
                    .build()

            return retrofit.create(UserDeviceService::class.java);
        }

        fun getUserDeviceService(): UserDeviceService? {
            var service: UserDeviceService? = sService
            if (service == null) {
                synchronized(Factory::class.java) {
                    service = sService
                    if (service == null) {
                        sService = create()
                        service = sService
                    }
                }
            }
            return service
        }
    }







}