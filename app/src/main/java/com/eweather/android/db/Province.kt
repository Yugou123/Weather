package com.eweather.android.db

import org.litepal.crud.LitePalSupport

class Province : LitePalSupport() {
    private var id:Int = -1
    private var provinceName:String =""
    private var provinceCode:Int = -1

    fun getId():Int{
        return id
    }

    fun setId(value:Int){
        id = value;
    }
    fun getprovinceName():String{
        return provinceName
    }

    fun setprovinceName(value:String){
        provinceName = value;
    }
    fun getprovinceCode():Int{
        return provinceCode
    }

    fun setprovinceCode(value:Int){
        provinceCode = value;
    }
}