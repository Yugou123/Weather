package com.eweather.android

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import androidx.core.view.GravityCompat

class ProgressIndicator  private constructor(context: Context,theme:Int):Dialog(context,theme){
    companion object{
        private lateinit var indicator: ProgressIndicator
        fun buildIndicator(context: Context):ProgressIndicator{
            indicator = ProgressIndicator(context,R.style.commonDialogStyle)

            indicator.setContentView(R.layout.progress_cir)

            indicator.setCancelable(true)
            indicator.window?.attributes?.apply {
                gravity = Gravity.CENTER_VERTICAL
            }

            val lp = indicator.window?.attributes
            if (lp != null) {
                lp.dimAmount = 0.2f
            }

            indicator.window?.apply {
                attributes = lp
            }

            indicator.setCanceledOnTouchOutside(false)
            return indicator
        }
    }

    fun showLoading(){
        indicator.show()
    }

    fun dismissLoading() {
        indicator.dismiss()
    }
}