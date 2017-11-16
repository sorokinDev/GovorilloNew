package com.vsquad.projects.govorillo.ui.fragment.random_topic

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.vsquad.projects.govorillo.R
import com.vsquad.projects.govorillo.presentation.view.random_topic.RandomTopicFragmentView
import com.vsquad.projects.govorillo.presentation.presenter.random_topic.RandomTopicFragmentPresenter

import com.arellomobile.mvp.presenter.InjectPresenter
import com.vsquad.projects.govorillo.GovorilloApplication
import com.vsquad.projects.govorillo.Screens
import com.vsquad.projects.govorillo.common.MyTimer
import com.vsquad.projects.govorillo.common.PrefConst
import com.vsquad.projects.govorillo.common.SpeakingState
import com.vsquad.projects.govorillo.model.entity.TopicEntity
import ru.terrakok.cicerone.Router
import javax.inject.Inject

import kotlinx.android.synthetic.main.fragment_random_topic.*;

class RandomTopicFragment : MvpAppCompatFragment(), RandomTopicFragmentView {
    override fun onPause() {
        super.onPause()
        tmrPreparing.stop()
        tmrSpeech.stop()
    }

    override fun onStart() {
        super.onStart()
        val sPref = GovorilloApplication.INSTANCE.getSharedPreferences(PrefConst.APP_PREFERENCES, Context.MODE_PRIVATE)
        if(!sPref.getBoolean(PrefConst.USER_AUTHORIZED, false) && sPref.getInt(PrefConst.TIMES_RESULT_SHOWED, 0) > 3){
            router.newRootScreen(Screens.AUTHENTICATION_SCREEN)
            return
        }
        mRandomTopicFragmentPresenter.setRandomTopic()
        tmrPreparing.start()

        Log.d("Randtop", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("Randtop", "onResume")
    }

    lateinit var tmrPreparing: MyTimer
    lateinit var tmrSpeech: MyTimer

    override fun setSpeakingState(speakingState: SpeakingState) {
        if(speakingState == SpeakingState.STOPPED){
            ll_while_prep.visibility = View.VISIBLE
            ll_while_speaking.visibility = View.GONE
            btn_next_topic.visibility = View.VISIBLE
            tmrPreparing.start()
        }else if(speakingState == SpeakingState.STARTING) {
            btn_switch_speaking.setText("...Wait...")
            tmrPreparing.stop()
            ll_while_prep.visibility = View.GONE
            btn_next_topic.visibility = View.GONE
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

    override fun setTopic(topic: TopicEntity) {
        tv_topic.text = topic.text
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
    @Inject
    lateinit var router: Router

    @InjectPresenter
    lateinit var mRandomTopicFragmentPresenter: RandomTopicFragmentPresenter
    //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        GovorilloApplication.INSTANCE.getAppComponent().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_random_topic, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_next_topic.setOnClickListener {
            mRandomTopicFragmentPresenter.setRandomTopic()
        }
        btn_switch_speaking.setOnClickListener{
            mRandomTopicFragmentPresenter.switchSpeakingState()
        }
        tv_time_for_speech.text = resources.getString(R.string.time_for_speech, 3)

        val prepTimeStr = resources.getString(R.string.time_for_preparing)
        val speechTimeStr = resources.getString(R.string.speech_time)
        tmrPreparing = MyTimer(-1, 0, 90){
            tv_time_for_preparing.text = String.format(prepTimeStr, it)
        }
        tmrSpeech = MyTimer(1, 0, Int.MAX_VALUE){
            tv_speech_time.text = String.format(speechTimeStr, it/60, it)
        }
    }
}
