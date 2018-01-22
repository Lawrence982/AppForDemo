package com.netcracker.appfordemo.uimodel

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.netcracker.appfordemo.BR

/**
 * Created by mozil on 19.01.2018.
 */
class Repository(repositoryName : String, var repositoryOwner: String?, var numberOfStars: Int?
                 , var hasIssues: Boolean = false) : BaseObservable(){

    @get:Bindable
    var repositoryName : String = repositoryName
        set(value) {
            field = value
            notifyPropertyChanged(BR.repositoryName)
        }

}