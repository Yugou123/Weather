package com.eweather.android

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import com.eweather.android.gson.Weather
import com.eweather.android.util.HttpUtil
import com.eweather.android.util.Utility
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.w3c.dom.Text
import java.io.IOException

class WeatherActivity : AppCompatActivity() {

    lateinit var weatherLayout:ScrollView
    lateinit var titleCity:TextView
    lateinit var titleUpDateTime:TextView
    lateinit var degreeText:TextView
    lateinit var weatherInfoText:TextView
    lateinit var forecastLayout:LinearLayout
    lateinit var aqiText: TextView
    lateinit var pm25Text:TextView
    lateinit var comfortText:TextView
    lateinit var carWashText:TextView
    lateinit var sportText:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        weatherLayout = findViewById(R.id.weather_layout)
        titleCity = findViewById(R.id.title_city)
        titleUpDateTime = findViewById(R.id.title_update_time)
        degreeText = findViewById(R.id.degree_text)
        weatherInfoText = findViewById(R.id.weather_info_text)
        forecastLayout = findViewById(R.id.forecast_layout)
        aqiText = findViewById(R.id.aqi_text)
        pm25Text = findViewById(R.id.pm2t_text)
        comfortText = findViewById(R.id.comfort_text)
        carWashText = findViewById(R.id.car_wash_text)
        sportText = findViewById(R.id.sport_text)

        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        val weatherString: String? = sharedPref.getString("weather",null)
        if(weatherString!=null){
            Utility.handleWeatherResponse(weatherString)?.let { showWeatherInfo(it) }

        }else{
            val weatherId:String = intent.getStringExtra("weather_id")
            weatherLayout.visibility = View.INVISIBLE
            requestWeather(weatherId);
        }
    }

    fun requestWeather(weatherId: String) {
        val weatherUrl:String = "http://guolin.tech/api/weather?cityid="+weatherId+"&key=bc0418b57b2d4918819d3974ac1285d9"
        HttpUtil.sendOkHttpRequest(weatherUrl,object :Callback{
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.let {
                    val responseText = it.string()
                    val weather:Weather? = Utility.handleWeatherResponse(responseText)
                    runOnUiThread(object:Runnable{
                        override fun run() {
                            if (weather!=null&&"ok".equals(weather.status))
                            {
                                val sharePref = getPreferences(Context.MODE_PRIVATE)
                                with(sharePref.edit()){
                                    putString("weather",responseText)
                                    apply()
                                    showWeatherInfo(weather)
                                }
                            }else{
                                Toast.makeText(this@WeatherActivity,"加载天气失败",Toast.LENGTH_SHORT).show()
                            }
                        }

                    })
                }

            }
        })
    }

    fun showWeatherInfo(weather: Weather){
        weather?.let {
            val cityName:String = weather.basic.cityName
            val updateTime:String = weather.basic.update.updateTime.split(" ")[1]
            val degree = weather.now.temperature+"℃"
            val weatherInfo = weather.now.more.info

            titleCity.text = cityName
            titleUpDateTime.text = updateTime
            degreeText.text = degree
            weatherInfoText.text = weatherInfo
            forecastLayout.removeAllViews()
            for (forecast in weather.forecastList){
                val view = LayoutInflater.from(this).inflate(R.layout.forecast_item,forecastLayout,false)
                with(view){
                    val dateText:TextView = findViewById(R.id.date_text)
                    val infoText:TextView = findViewById(R.id.info_text)
                    val maxText:TextView = findViewById(R.id.max_text)
                    val minText:TextView = findViewById(R.id.min_text)

                    dateText.text = forecast.date
                    infoText.text = forecast.more.info
                    maxText.text = forecast.temperature.max
                    minText.text = forecast.temperature.min
                    forecastLayout.addView(view)
                }
            }
            weather.aqi?.let {
                aqiText.text = weather.aqi.city.aqi
                pm25Text.text = weather.aqi.city.pm25
            }

            with(weather.suggestion){
                comfortText.text = "舒适度："+comfort.info
                carWashText.text = "洗车指数："+carWash.info
                sportText.text = "运动建议："+sport.info
            }
            weatherLayout.visibility = View.VISIBLE
        }
    }
}