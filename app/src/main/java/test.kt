import com.eweather.android.util.Utility
import okhttp3.*
import okio.IOException

fun main() {
    val client: OkHttpClient = OkHttpClient()

    val request: Request = Request.Builder().url("https://www.jianshu.com/p/9e6848aeeeee").build()
    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e:IOException){
            e.printStackTrace()
        }

        override fun onResponse(call: Call, response: Response){
            val responseText = response.body?.string()
            print(responseText)
        }})
}