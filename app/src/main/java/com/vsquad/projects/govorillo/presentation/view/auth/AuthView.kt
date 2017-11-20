package com.vsquad.projects.govorillo.presentation.view.auth

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface AuthView : MvpView {
    @StateStrategyType(SingleStateStrategy::class)
    fun setWrongPassError()
    fun setErrorWhileRegistration()
}
