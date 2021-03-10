package com.eweather.android.db

import org.litepal.crud.LitePalSupport

class City : LitePalSupport() {
    private var id:Int = -1
    private var cityName:String =""
    private var cityCode:Int = -1
    private var provinceId:Int = -1

    fun setId(value: Int){
        id = value
    }

    fun getId() : Int = id

    fun setCityName(value: String){
        cityName = value
    }

    fun  getCityName():String = cityName

    fun setCityCode(value: Int){
        cityCode = value
    }

    fun getCityCode():Int = cityCode

    fun setProvinceId(value: Int){
        provinceId = value
    }

    fun getProvinceId():Int = provinceId

}