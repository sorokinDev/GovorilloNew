package com.vsquad.projects.govorillo.presentation.presenter.auth

import android.content.Context
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.vsquad.projects.govorillo.GovorilloApplication
import com.vsquad.projects.govorillo.Screens
import com.vsquad.projects.govorillo.common.PrefConst
import com.vsquad.projects.govorillo.common.SharedPrefUtils
import com.vsquad.projects.govorillo.presentation.presenter.base.BaseFragmentPresenter
import com.vsquad.projects.govorillo.presentation.view.auth.AuthView

@InjectViewState
class AuthPresenter : BaseFragmentPresenter<AuthView>() {
    override var isOpen: Boolean = true
    init {
        GovorilloApplication.INSTANCE.getAppComponent().inject(this)
    }
    fun trySignIn(email: String, pass: String){
        if(email.equals("email") && pass.equals("pass")){
            SharedPrefUtils.edit().putBoolean(PrefConst.USER_AUTHORIZED, true)
                    .putString(PrefConst.USER_EMAIL, email)
                    .putString(PrefConst.USER_PASS, pass)
                    .apply()
            router.newRootScreen(Screens.FREE_TOPIC_SCREEN)
        }else{
            viewState.setWrongPassError()
        }
    }

}
