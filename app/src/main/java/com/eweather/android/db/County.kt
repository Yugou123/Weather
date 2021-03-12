package com.eweather.android.db

import org.litepal.crud.LitePalSupport

class County : LitePalSupport(){
    private var id:Int = -1
    private var countyName:String = ""
    private var weatherId:String = ""
    private var cityId:Int = -1

    fun setId(value: Int){
        id = value
    }

    fun getId() : Int = id

    fun setCountyName(value:String){
        countyName = value
    }
    fun getCountyName():String = countyName

    fun setWeatherId(value: String){
        weatherId = value
    }

    fun getWeatherId() : String = weatherId

    fun setCityId(value: Int){
        cityId = value
    }

    fun getCityId() : Int = cityId
}