package com.eweather.android.gson

import com.google.gson.annotations.SerializedName

data class Basic (
    @SerializedName("city")val cityName:String,
    @SerializedName("id")val weatherId:String,
     val update: Update)

data class Update(@SerializedName("loc")val updateTime:String)

data class AQI(val city:AQICity)
data class AQICity(val aqi:String,val pm25:String)

data class Now(@SerializedName("tmp")val temperature:String,
               @SerializedName("cond")val more:More)

data class More(@SerializedName("txt")val info:String)

data class Suggestion(@SerializedName("comf")val comfort:Comfort,
                      @SerializedName("cw")val carWash:CarWash,
                      val sport:Sport
                      )

data class Comfort(@SerializedName("txt")val info:String = "N/A")
data class CarWash(@SerializedName("txt")val info:String = "N/A")
data class Sport(@SerializedName("txt")val info: String = "N/A")

data class Forecast(
    val date:String,
    @SerializedName("tmp")val temperature: Temperature,
    @SerializedName("cond")val more: More
    ){
    data class More(@SerializedName("txt_d")val info:String)
}

data class Temperature(val max:String,val min:String)

data class Weather(
    val status:String,
    val basic: Basic,
    val aqi: AQI,
    val now: Now,
    val suggestion: Suggestion,
    @SerializedName("daily_forecast")val forecastList:List<Forecast>
    )

