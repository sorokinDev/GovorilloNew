package com.vsquad.projects.govorillo.ui.fragment.twister

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vsquad.projects.govorillo.R
import com.vsquad.projects.govorillo.presentation.view.twister.TwisterView
import com.vsquad.projects.govorillo.presentation.presenter.twister.TwisterPresenter

import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.mixpanel.android.mpmetrics.MixpanelAPI
import com.vsquad.projects.govorillo.GovorilloApplication
import com.vsquad.projects.govorillo.model.entity.TwisterEntity
import com.vsquad.projects.govorillo.ui.fragment.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_twister.*
import org.jetbrains.anko.onClick
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class TwisterFragment : BaseFragment(), TwisterView {
    lateinit var mixpanel : MixpanelAPI

    override fun pulse() {
        pulsator.startRippleAnimation()
        Handler().postDelayed({ pulsator.stopRippleAnimation()}, 200)
    }

    override fun setStatusText(txt: String){
        tv_status.text = txt
    }

    override fun setNewTwister(twister: TwisterEntity) {
        tv_twister.text = twister.text
    }

    override fun setMode(mode: Int) {
        if(mode == TwisterView.MODE_PREPARING){
            rl_on_start_only.visibility = View.VISIBLE
            rl_on_twistering_only.visibility = View.GONE
        }else if(mode == TwisterView.MODE_TWISTERING){
            rl_on_start_only.visibility = View.GONE
            rl_on_twistering_only.visibility = View.VISIBLE
        }

    }

    override var fragmentTitle: String = "Скороговорки"

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
    @InjectPresenter
    lateinit var mTwisterPresenter: TwisterPresenter
    //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        GovorilloApplication.INSTANCE.getAppComponent().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mixpanel = MixpanelAPI.getInstance(context, resources.getString(R.string.mixpanel_token))
        mixpanel.track("[Twisters]")
        return inflater.inflate(R.layout.fragment_twister, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_start_twistering.onClick {
            mTwisterPresenter.changeMode(TwisterView.MODE_TWISTERING)
            mixpanel.track("[Twisters] -> start")
        }

        btn_next_twister.onClick {
            mTwisterPresenter.nextTwister()
            mixpanel.track("[Twisters] -> next twister")
        }
        btn_finish.onClick {
            mixpanel.track("[Twisters] -> finish")
            mTwisterPresenter.finishTwistering()
        }

    }
}
