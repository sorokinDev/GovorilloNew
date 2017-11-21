package com.vsquad.projects.govorillo.presentation.presenter.result

import android.content.Context
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import com.vsquad.projects.govorillo.GovorilloApplication
import com.vsquad.projects.govorillo.common.PrefConst
import com.vsquad.projects.govorillo.presentation.presenter.base.BaseFragmentPresenter

/**
 * Created by Vova on 17.11.2017.
 */

abstract class BaseResultPresenter<T : MvpView> : BaseFragmentPresenter<T>() {
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        val sPref = GovorilloApplication.INSTANCE.getSharedPreferences(PrefConst.APP_PREFERENCES, Context.MODE_PRIVATE)
        val isUserAuth = sPref.getBoolean(PrefConst.USER_AUTHORIZED, false)
        var timesResShowed = sPref.getInt(PrefConst.TIMES_RESULT_SHOWED, 0)
        if(!isUserAuth){
            timesResShowed += 1
            sPref.edit().putInt(PrefConst.TIMES_RESULT_SHOWED, timesResShowed).apply()
        }
    }
}