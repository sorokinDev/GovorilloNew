package com.vsquad.projects.govorillo.ui.fragment.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vsquad.projects.govorillo.R
import com.vsquad.projects.govorillo.presentation.view.auth.AuthView
import com.vsquad.projects.govorillo.presentation.presenter.auth.AuthPresenter

import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.vsquad.projects.govorillo.GovorilloApplication
import com.vsquad.projects.govorillo.ui.fragment.base.BaseFragment

import kotlinx.android.synthetic.main.fragment_auth.*

class AuthFragment : BaseFragment(), AuthView {
    override fun onCreate(savedInstanceState: Bundle?) {
        GovorilloApplication.INSTANCE.getAppComponent().inject(this)
        super.onCreate(savedInstanceState)
    }

    override var fragmentTitle: String = "Авторизация"

    override fun setWrongPassError() {
        textInput_pass.error = resources.getString(R.string.wrong_pass)
    }

    companion object {
        const val TAG = "AuthFragment"

        fun newInstance(): AuthFragment {
            val fragment: AuthFragment = AuthFragment()
            val args: Bundle = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    @InjectPresenter
    lateinit var mAuthPresenter: AuthPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_auth, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_sign_in.setOnClickListener {
            mAuthPresenter.trySignIn(et_email.text.toString(), et_pass.text.toString())
        }
    }
}
