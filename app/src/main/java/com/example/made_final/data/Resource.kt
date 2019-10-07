package com.example.made_final.data

import com.example.made_final.data.Status.*
import kotlinx.android.parcel.RawValue
import java.io.Serializable

class Resource<T>(var status: Status?, val data: @RawValue T?, val message: String?) :
        Serializable {
    companion object {

        fun <T> success(data: T): Resource<T> {
            return Resource(SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(LOADING, data, null)
        }
    }
}