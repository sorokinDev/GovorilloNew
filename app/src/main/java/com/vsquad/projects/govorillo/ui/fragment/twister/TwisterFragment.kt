package com.vsquad.projects.govorillo.ui.fragment.twister

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vsquad.projects.govorillo.R
import com.vsquad.projects.govorillo.presentation.view.twister.TwisterFragmentView
import com.vsquad.projects.govorillo.presentation.presenter.twister.TwisterFragmentPresenter

import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.vsquad.projects.govorillo.GovorilloApplication
import com.vsquad.projects.govorillo.presentation.presenter.main.MainActivityPresenter
import kotlinx.android.synthetic.main.fragment_twister.*
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class TwisterFragment : MvpAppCompatFragment(), TwisterFragmentView {

    companion object {
        const val TAG = "TwisterFragment"

        fun newInstance(): TwisterFragment {
            val fragment: TwisterFragment = TwisterFragment()
            val args: Bundle = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    //region INJECTIONS
    @Inject
    lateinit var router: Router

    @InjectPresenter
    lateinit var mTwisterFragmentPresenter: TwisterFragmentPresenter
    //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        GovorilloApplication.INSTANCE.getAppComponent().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_twister, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
