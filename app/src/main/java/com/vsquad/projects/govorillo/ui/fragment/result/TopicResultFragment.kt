package com.vsquad.projects.govorillo.ui.fragment.result

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vsquad.projects.govorillo.R
import com.vsquad.projects.govorillo.presentation.view.result.TopicResultView
import com.vsquad.projects.govorillo.presentation.presenter.result.TopicResultPresenter

import com.arellomobile.mvp.presenter.InjectPresenter
import com.vsquad.projects.govorillo.GovorilloApplication
import com.vsquad.projects.govorillo.model.analyser.BaseTextAnalyser
import com.vsquad.projects.govorillo.model.analyser.TextAnalysisResult
import com.vsquad.projects.govorillo.ui.fragment.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_topic_result.*

import android.graphics.Color.parseColor
import android.os.Handler


class TopicResultFragment : BaseFragment(), TopicResultView {
    override fun setSpeechResult(res: TextAnalysisResult) {
        Log.d("RESULT", res.text)

        speedView.speedTo(20f, 0)
        Handler().postDelayed(Runnable { speedView.speedTo(180f, 1000) }, 50)
        Handler().postDelayed(Runnable { speedView.speedTo(100f, 500) }, 1050)
    }

    override var fragmentTitle: String = "Результат"
    lateinit var speechRes: TextAnalysisResult

    companion object {
        const val TAG = "TopicResultFragment"

        fun newInstance(res : TextAnalysisResult): TopicResultFragment {
            val fragment: TopicResultFragment = TopicResultFragment()

            val args: Bundle = Bundle()

            args.putParcelable("RESULT", res)

            fragment.arguments = args
            return fragment
        }
    }

    //region INJECTIONS
    @InjectPresenter
    lateinit var mRandomTopicResultPresenter: TopicResultPresenter
    //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        GovorilloApplication.INSTANCE.getAppComponent().inject(this)
        super.onCreate(savedInstanceState)
        if(arguments != null){
            speechRes = arguments.getParcelable("RESULT")
            mRandomTopicResultPresenter.speechRes = speechRes
        }

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_topic_result, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
