package com.vsquad.projects.govorillo.presentation.presenter.base

import android.content.Context
import android.util.Log
import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import com.vsquad.projects.govorillo.GovorilloApplication
import com.vsquad.projects.govorillo.R
import com.vsquad.projects.govorillo.Screens
import com.vsquad.projects.govorillo.common.PrefConst
import com.vsquad.projects.govorillo.common.SharedPrefUtils
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * Created by Vova on 17.11.2017.
 */
abstract class BaseFragmentPresenter<T: MvpView> : MvpPresenter<T>() {
    @Inject lateinit var router: Router

    override fun attachView(view: T) {

        Log.d("times_result_showed", ""+ SharedPrefUtils.sPref.getInt(PrefConst.TIMES_RESULT_SHOWED, 0))
        Log.d("USER_AUTH_PREF", SharedPrefUtils.sPref.getBoolean(PrefConst.USER_AUTHORIZED, false).toString())
        if(!isOpen && !SharedPrefUtils.sPref.getBoolean(PrefConst.USER_AUTHORIZED, false)
                && SharedPrefUtils.sPref.getInt(PrefConst.TIMES_RESULT_SHOWED, 0) >= GovorilloApplication.INSTANCE.resources.getInteger(R.integer.result_times_before_auth)){
            Log.d("navigator", "navigating to auth")
            val hdl = android.os.Handler()
            hdl.post(){ router.newRootScreen(Screens.AUTH_SCREEN) }
        }else{
            super.attachView(view)
        }

    }

    abstract var isOpen: Boolean

}