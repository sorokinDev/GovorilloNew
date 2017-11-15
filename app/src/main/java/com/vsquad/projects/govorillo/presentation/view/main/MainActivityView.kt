package com.vsquad.projects.govorillo.presentation.view.main

import android.support.annotation.StringRes
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface MainActivityView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    public fun setToolbarTitle(str: String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    public fun setDefaultDrawerItem()
}
