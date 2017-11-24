package com.vsquad.projects.govorillo.presentation.view.twister

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.vsquad.projects.govorillo.model.entity.TwisterEntity

interface TwisterView : MvpView {
    companion object {
        val MODE_PREPARING = 0
        val MODE_TWISTERING = 1
    }

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setMode(mode: Int)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setNewTwister(twister: TwisterEntity)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setStatusText(txt: String)
}
