package com.vsquad.projects.govorillo.ui.fragment.base

import com.arellomobile.mvp.MvpAppCompatFragment
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Vova on 18.11.2017.
 */
abstract class BaseFragment: MvpAppCompatFragment() {
    override fun onStart() {
        super.onStart()
        activity.title = fragmentTitle
    }

    abstract var fragmentTitle: String

}