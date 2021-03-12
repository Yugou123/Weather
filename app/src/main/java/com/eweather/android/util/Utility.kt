package com.eweather.android.util

import android.text.TextUtils
import com.eweather.android.db.City
import com.eweather.android.db.County
import com.eweather.android.db.Province
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class Utility {
    companion object{
        fun handleProvinceResponse(response:String):Boolean{
            if (!TextUtils.isEmpty(response)){
                try {
                    val allProvinces:JSONArray = JSONArray(response)
                    for (i in 0 until allProvinces.length() ){
                        val provinceObject:JSONObject = allProvinces.getJSONObject(i)
                        val province:Province = Province()
                        province.setprovinceName(provinceObject.getString("name"))
                        province.setprovinceCode(provinceObject.getInt("id"))
                        province.setId(provinceObject.getInt("id"))
                        province.save()
                    }
                    return true
                }catch (e:JSONException){
                    e.printStackTrace()
                }
            }
            return false
        }

        fun handleCityResponse(response: String,provinceId: Int):Boolean{
            if(!TextUtils.isEmpty(response)){
                try{
                    val allCities:JSONArray = JSONArray(response)
                    for(i in 0 until allCities.length()){
                        val cityObject:JSONObject = allCities.getJSONObject(i)
                        val city:City = City()
                        city.setCityName(cityObject.getString("name"))
                        city.setCityCode(cityObject.getInt("id"))
                        city.setProvinceId(provinceId)
                        city.save()
                    }
                    return true
                }catch (e:JSONException){
                    e.printStackTrace()
                }

            }
            return false
        }

        fun handleCountyResponse(response: String, cityId: Int):Boolean{
            if(!TextUtils.isEmpty(response)){
                try {
                    val allCounties:JSONArray = JSONArray(response)
                    for (i in 0 until allCounties.length()){
                        val countyObject:JSONObject = allCounties.getJSONObject(i)
                        val county:County = County()
                        county.setCountyName(countyObject.getString("name"))
                        county.setCityId(cityId)
                        county.setWeatherId(countyObject.getString("weather_id"))
                        county.save()
                    }
                    return  true
                }catch (e:JSONException){
                    e.printStackTrace()
                }
            }

            return false
        }
    }
}