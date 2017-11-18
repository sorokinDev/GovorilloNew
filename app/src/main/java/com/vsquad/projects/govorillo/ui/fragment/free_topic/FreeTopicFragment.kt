package com.vsquad.projects.govorillo.ui.fragment.free_topic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vsquad.projects.govorillo.R
import com.vsquad.projects.govorillo.presentation.view.free_topic.FreeTopicView
import com.vsquad.projects.govorillo.presentation.presenter.free_topic.FreeTopicPresenter


import com.arellomobile.mvp.presenter.InjectPresenter
import com.vsquad.projects.govorillo.GovorilloApplication
import com.vsquad.projects.govorillo.common.MyTimer
import com.vsquad.projects.govorillo.common.SpeakingState
import com.vsquad.projects.govorillo.ui.fragment.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_free_topic.*


class FreeTopicFragment : BaseFragment(), FreeTopicView {
    lateinit var tmrSpeech: MyTimer
    override fun setSpeakingState(speakingState: SpeakingState) {
        if(speakingState == SpeakingState.STOPPED){
            ll_while_speaking.visibility = View.GONE
        }else if(speakingState == SpeakingState.STARTING) {
            btn_switch_speaking.setText("...Wait...")
        }else if(speakingState == SpeakingState.STARTED){
            ll_while_speaking.visibility = View.VISIBLE
            btn_switch_speaking.setText("Speak")
            tmrSpeech.start()
            pulsator.start()
        }else if(speakingState == SpeakingState.FINISHING){
            btn_switch_speaking.setText("...Analyzing...")
            tmrSpeech.stop()
            pulsator.stop()
        }
    }

    override var fragmentTitle: String = "Свободная тема"

    companion object {
        const val TAG = "FreeTopicFragment"

        fun newInstance(): FreeTopicFragment {
            val fragment: FreeTopicFragment = FreeTopicFragment()
            val args: Bundle = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    @InjectPresenter
    lateinit var mFreeTopicPresenter: FreeTopicPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        GovorilloApplication.INSTANCE.getAppComponent().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_free_topic, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val speechTimeStr = resources.getString(R.string.speech_time)
        btn_switch_speaking.setOnClickListener{
            mFreeTopicPresenter.switchSpeakingState()
        }
        tmrSpeech = MyTimer(1, 0, Int.MAX_VALUE){
            tv_speech_time.text = String.format(speechTimeStr, it/60, it)
        }
    }
}