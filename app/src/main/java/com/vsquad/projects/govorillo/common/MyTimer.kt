package com.vsquad.projects.govorillo.common

import android.os.Handler


/**
 * Created by Vova on 16.11.2017.
 */
class MyTimer(val interval: Int, val minT: Int, val maxT: Int, val actForTimer: (seconds: Int) -> Unit) {
    var curT: Int = if (interval > 0) minT else maxT

    val runnable: Runnable = object : Runnable {
        override fun run() {
            if((interval > 0 && curT <= maxT) || (interval < 0 && minT <= curT))
                actForTimer(curT)
            curT += interval
            updateHandler.postDelayed(this, Math.abs(interval.toLong())*1000)
        }

    }

    val updateHandler: Handler = Handler()

    fun start(){
        stop()
        curT = if (interval > 0) minT else maxT
        updateHandler.post(runnable);

    }

    fun stop(){
        updateHandler.removeCallbacksAndMessages(null)
    }

}


