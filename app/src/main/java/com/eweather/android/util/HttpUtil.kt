package com.eweather.android.util

import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request

class HttpUtil{
    companion object{
        fun sendOkHttpRequest(address:String,callback:Callback){
            val client:OkHttpClient = OkHttpClient()
            val request:Request = Request.Builder().url(address).build()
            client.newCall(request).enqueue(callback)
        }
    }
}