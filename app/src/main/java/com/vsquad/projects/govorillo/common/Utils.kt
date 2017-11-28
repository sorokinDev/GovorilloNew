package com.vsquad.projects.govorillo.common

import android.content.Context
import android.content.SharedPreferences
import com.vsquad.projects.govorillo.GovorilloApplication

/**
 * Created by Vova on 18.11.2017.
 */
class SharedPrefUtils{
    companion object {
        val sPref = GovorilloApplication.INSTANCE.getSharedPreferences(PrefConst.APP_PREFERENCES, Context.MODE_PRIVATE)
        fun edit() = sPref.edit()
    }

}

fun <T> Array<T>.getRandom(): T = this[(this.count() * Math.random()).toInt()]