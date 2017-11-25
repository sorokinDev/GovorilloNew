package com.vsquad.projects.govorillo.ui.fragment.random_topic

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.vsquad.projects.govorillo.R
import com.vsquad.projects.govorillo.presentation.view.random_topic.RandomTopicView
import com.vsquad.projects.govorillo.presentation.presenter.random_topic.RandomTopicPresenter

import com.arellomobile.mvp.presenter.InjectPresenter
import com.facebook.FacebookSdk
import com.mixpanel.android.mpmetrics.MixpanelAPI
import com.vsquad.projects.govorillo.GovorilloApplication
import com.vsquad.projects.govorillo.common.MyTimer
import com.vsquad.projects.govorillo.common.PrefConst
import com.vsquad.projects.govorillo.common.SpeakingState
import com.vsquad.projects.govorillo.model.entity.TopicEntity
import com.vsquad.projects.govorillo.ui.fragment.base.BaseFragment
import ru.terrakok.cicerone.Router
import javax.inject.Inject

import kotlinx.android.synthetic.main.fragment_random_topic.*;


class RandomTopicFragment : BaseFragment(), RandomTopicView {
    lateinit var mixpanel : MixpanelAPI

    override fun setPreparingTime(time: Int) {
        tv_time_for_preparing.text = String.format(resources.getString(R.string.time_for_preparing), time)
    }

    override fun setSpeakingTime(speakingTime: Int) {
        tv_speech_time.text = String.format(resources.getString(R.string.speech_time), speakingTime / 60, speakingTime % 60)
    }

    override var fragmentTitle: String = "Рассуждения"

    override fun onPause() {
        super.onPause()

    }

    override fun onStart() {
        super.onStart()
        mRandomTopicPresenter.setRandomTopic()

    }

    override fun onResume() {
        super.onResume()
        Log.d("Randtop", "onResume")
    }



    override fun setSpeakingState(speakingState: SpeakingState) {
        if(speakingState == SpeakingState.STOPPED){
            ll_while_prep.visibility = View.VISIBLE
            ll_while_speaking.visibility = View.GONE
            btn_next_topic.visibility = View.VISIBLE

        }else if(speakingState == SpeakingState.STARTING) {
            btn_switch_speaking.setText("...Wait...")
            mixpanel.track("[RandomTopic] -> Start talking")
            mixpanel.track("[RandomTopic] Topic: "+tv_topic.text)
            ll_while_prep.visibility = View.GONE
            btn_next_topic.visibility = View.GONE
        }else if(speakingState == SpeakingState.STARTED){
            ll_while_speaking.visibility = View.VISIBLE
            btn_switch_speaking.setText("Speak")
            pulsator.start()
        }else if(speakingState == SpeakingState.FINISHING){
            btn_switch_speaking.setText("...Analyzing...")
            pulsator.stop()
        }

    }

    override fun setTopic(topic: TopicEntity) {
        tv_topic.text = topic.text
        tv_time_for_speech.text = resources.getString(R.string.time_for_speech, topic.speechTime/60, topic.speechTime % 60)

    }



    companion object {
        const val TAG = "RandomTopicFragment"

        fun newInstance(): RandomTopicFragment {
            val fragment: RandomTopicFragment = RandomTopicFragment()
            val args: Bundle = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    //region INJECTIONS

    @InjectPresenter
    lateinit var mRandomTopicPresenter: RandomTopicPresenter
    //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        GovorilloApplication.INSTANCE.getAppComponent().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mixpanel = MixpanelAPI.getInstance(context, resources.getString(R.string.mixpanel_token))
        mixpanel.track("[RandomTopic]")
        return inflater.inflate(R.layout.fragment_random_topic, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_next_topic.setOnClickListener {
            mixpanel.track("[RandomTopic] -> Next topic")
            mRandomTopicPresenter.setRandomTopic()
        }
        btn_switch_speaking.setOnClickListener{
            mRandomTopicPresenter.switchSpeakingState()
        }
    }
}
