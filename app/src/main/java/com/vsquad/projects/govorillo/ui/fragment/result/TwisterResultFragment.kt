package com.vsquad.projects.govorillo.ui.fragment.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vsquad.projects.govorillo.R
import com.vsquad.projects.govorillo.presentation.view.result.TwisterResultView
import com.vsquad.projects.govorillo.presentation.presenter.result.TwisterResultPresenter

import com.arellomobile.mvp.presenter.InjectPresenter
import com.vsquad.projects.govorillo.GovorilloApplication
import com.vsquad.projects.govorillo.model.analyser.TwisterAnalysisResult
import com.vsquad.projects.govorillo.ui.fragment.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_topic_result.*

class TwisterResultFragment : BaseFragment(), TwisterResultView {
    override fun setSpeechResult(res: TwisterAnalysisResult) {
        speedView.speedTo(20f, 0)
        speedView.speedTo(res.speed.toFloat(), 1000)
    }

    override var fragmentTitle: String = "Результат"

    companion object {
        const val TAG = "TwisterResultFragment"

        fun newInstance(res: TwisterAnalysisResult): TwisterResultFragment {
            val fragment: TwisterResultFragment = TwisterResultFragment()
            val args: Bundle = Bundle()
            args.putParcelable("RESULT", res)
            fragment.arguments = args
            return fragment
        }
    }

    lateinit var speechRes: TwisterAnalysisResult

    override fun onCreate(savedInstanceState: Bundle?) {
        GovorilloApplication.INSTANCE.getAppComponent().inject(this)
        super.onCreate(savedInstanceState)
        if(arguments != null){
            speechRes = arguments.getParcelable("RESULT")
            mTwisterResultPresenter.speechRes = speechRes
        }
    }

    @InjectPresenter
    lateinit var mTwisterResultPresenter: TwisterResultPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_twister_result, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
