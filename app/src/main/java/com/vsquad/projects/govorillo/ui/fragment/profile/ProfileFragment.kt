package com.vsquad.projects.govorillo.ui.fragment.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vsquad.projects.govorillo.R
import com.vsquad.projects.govorillo.presentation.view.profile.ProfileView
import com.vsquad.projects.govorillo.presentation.presenter.profile.ProfilePresenter

import com.arellomobile.mvp.presenter.InjectPresenter
import com.facebook.FacebookSdk
import com.mixpanel.android.mpmetrics.MixpanelAPI
import com.vsquad.projects.govorillo.GovorilloApplication
import com.vsquad.projects.govorillo.ui.fragment.base.BaseFragment

import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : BaseFragment(), ProfileView {
    override var fragmentTitle: String = "Профиль"
    lateinit var mixpanel : MixpanelAPI

    companion object {
        const val TAG = "ProfileFragment"

        fun newInstance(): ProfileFragment {
            val fragment: ProfileFragment = ProfileFragment()
            val args: Bundle = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    @InjectPresenter
    lateinit var mProfilePresenter: ProfilePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        GovorilloApplication.INSTANCE.getAppComponent().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mixpanel = MixpanelAPI.getInstance(context, resources.getString(R.string.mixpanel_token))
        mixpanel.track("[Profile]")
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_exit.setOnClickListener {
            mixpanel.track("[Profile] -> Exit")
            mProfilePresenter.exitUser()
        }
    }
}
