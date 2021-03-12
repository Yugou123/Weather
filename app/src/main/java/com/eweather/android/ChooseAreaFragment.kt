package com.eweather.android


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eweather.android.db.City
import com.eweather.android.db.County
import com.eweather.android.db.Province
import com.eweather.android.util.HttpUtil
import com.eweather.android.util.Utility
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import okio.IOException
import org.litepal.LitePal

class ChooseAreaFragment: Fragment() {
    companion object{
        val LEVEL_PROVINCE:Int = 0
        val LEVEL_CITY:Int = 1
        val LEVEL_COUTY:Int = 2
    }

    private lateinit var progressDialog:ProgressIndicator

    private lateinit  var titleText: TextView
    private lateinit  var backButton:Button
    private lateinit  var listView:RecyclerView

    private lateinit var adapter: MyAdapter
    private val dataList:ArrayList<String> = ArrayList<String>()

    private lateinit var provinceList:List<Province>
    private lateinit var cityList:List<City>
    private lateinit var countyList:List<County>

    private lateinit var selectedProvince:Province
    private lateinit var selectedCity:City
    private  var currentLevel:Int = 0

    init {
    }

    fun onItemClick(s: String){
       titleText.text = s
       if (currentLevel== LEVEL_PROVINCE){
           selectedProvince = provinceList[dataList.indexOf(s)]
           queryCity()
       }else if(currentLevel == LEVEL_CITY){
           selectedCity = cityList[dataList.indexOf(s)]
           queryCounty()
       }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View = inflater.inflate(R.layout.choose_area,container,false)
        titleText = view.findViewById(R.id.title_text)
        backButton = view.findViewById(R.id.back_button)
        listView = view.findViewById(R.id.list_view)
        adapter = MyAdapter(context,dataList){
                s -> onItemClick(s)
        }
        listView.layoutManager = LinearLayoutManager(context)
        listView.adapter = adapter
        backButton.setOnClickListener {
            if(currentLevel == LEVEL_COUTY){
                queryCity()
            }else if (currentLevel == LEVEL_CITY){
                queryProvince()
            }
        }
        progressDialog = context?.let { ProgressIndicator.buildIndicator(it) }!!

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        queryProvince()
    }

    fun queryProvince(){
        titleText.text = "China"
        backButton.visibility = View.GONE
        provinceList = LitePal.findAll(Province::class.java)

        if(provinceList.size>0){
            dataList.clear()
            for (province in provinceList){
                dataList.add(province.getprovinceName())
            }
            adapter.notifyDataSetChanged()
            currentLevel = LEVEL_PROVINCE
        }else{
            val address = "http://guolin.tech/api/china"
            queryFromServer(address,"province")
        }

    }

    fun queryCity(){
        titleText.text = selectedProvince.getprovinceName()
        backButton.visibility = View.VISIBLE
        cityList = LitePal.where("provinceid = ?", "${selectedProvince.getId()}").find(City::class.java)
        if (cityList.size>0){
            dataList.clear()
            for (city in cityList){
                dataList.add(city.getCityName())
            }
            adapter.notifyDataSetChanged()
            currentLevel = LEVEL_CITY
        }else{
            val provinceCode = selectedProvince.getprovinceCode()
            val address = "http://guolin.tech/api/china/"+provinceCode
            queryFromServer(address,"city")
        }
    }

    fun queryCounty(){
        titleText.text = selectedCity.getCityName()
        backButton.visibility = View.VISIBLE
        countyList = LitePal.where("cityid = ?","${selectedCity.getId()}").find(County::class.java)
        if (countyList.size>0){
            dataList.clear()
            for (county in countyList){
                dataList.add(county.getCountyName())
            }
            adapter.notifyDataSetChanged()
            currentLevel = LEVEL_COUTY
        }else{
            val provinceCode = selectedProvince.getprovinceCode()
            val cityCode = selectedCity.getCityCode()
            val address = "http://guolin.tech/api/china/"+provinceCode+"/"+cityCode
            queryFromServer(address,"county")
        }
    }


    fun queryFromServer(address:String,type:String){
        progressDialog.showLoading()
        HttpUtil.sendOkHttpRequest(address,object :Callback{
            override fun onFailure(call:Call,e:IOException){
                progressDialog.dismissLoading()
                e.printStackTrace()
            }

            override fun onResponse(call: Call,response:Response){
                val responseText:String = response.body?.string() ?: ""
                var result = false
                if("province".equals(type)){
                    result = Utility.handleProvinceResponse(responseText)
                }else if("city".equals(type)){
                    result = Utility.handleCityResponse(responseText,selectedProvince.getId())
                }else if("county".equals(type)){
                    result = Utility.handleCountyResponse(responseText,selectedCity.getId())
                }

                if (result){
                    activity?.runOnUiThread(object :Runnable{
                        override fun run() {
                            progressDialog.dismissLoading()
                            if("province".equals(type)){
                                queryProvince()
                            }else if("city".equals(type)){
                                queryCity()
                            }else if("county".equals(type)){
                                queryCounty()
                            }
                        }

                    })
                }
            }
        })
    }
}