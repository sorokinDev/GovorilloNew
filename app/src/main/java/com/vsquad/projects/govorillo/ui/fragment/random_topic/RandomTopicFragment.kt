package com.vsquad.projects.govorillo.ui.fragment.random_topic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatFragment
import com.vsquad.projects.govorillo.R
import com.vsquad.projects.govorillo.presentation.view.random_topic.RandomTopicFragmentView
import com.vsquad.projects.govorillo.presentation.presenter.random_topic.RandomTopicFragmentPresenter

import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.vsquad.projects.govorillo.GovorilloApplication
import com.vsquad.projects.govorillo.Screens
import com.vsquad.projects.govorillo.common.SpeakingState
import com.vsquad.projects.govorillo.model.entity.TopicEntity
import com.vsquad.projects.govorillo.presentation.presenter.twister.TwisterFragmentPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

import kotlinx.android.synthetic.main.fragment_random_topic.*;

class RandomTopicFragment : MvpAppCompatFragment(), RandomTopicFragmentView {
    override fun setSpeakingState(speakingState: SpeakingState) {
        when(speakingState){
            SpeakingState.STOPPED -> {
                btn_next_topic.visibility = View.VISIBLE
                btn_switch_speaking.setText("Start")
            }
            SpeakingState.STARTED -> {
                btn_next_topic.visibility = View.GONE
                btn_switch_speaking.setText("Stop")
            }
            SpeakingState.STARTING -> {
                btn_next_topic.visibility = View.GONE
                btn_switch_speaking.setText("Wait...")
            }
            SpeakingState.FINISHING -> {
                btn_next_topic.visibility = View.GONE
                btn_switch_speaking.setText("Analyzing...")
            }
            SpeakingState.FINISHED -> {
                btn_next_topic.visibility = View.VISIBLE
                btn_switch_speaking.setText("Start")
            }
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
    }
}
