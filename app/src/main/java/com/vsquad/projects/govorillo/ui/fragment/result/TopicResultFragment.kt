package com.vsquad.projects.govorillo.ui.fragment.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.vsquad.projects.govorillo.R
import com.vsquad.projects.govorillo.presentation.view.result.TopicResultView
import com.vsquad.projects.govorillo.presentation.presenter.result.TopicResultPresenter

import com.arellomobile.mvp.presenter.InjectPresenter
import com.mixpanel.android.mpmetrics.MixpanelAPI
import com.vsquad.projects.govorillo.GovorilloApplication
import com.vsquad.projects.govorillo.model.analyser.BaseTextAnalyser
import com.vsquad.projects.govorillo.ui.fragment.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_topic_result.*
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class TopicResultFragment : BaseFragment(), TopicResultView {

    override var fragmentTitle: String = "Результат"
    lateinit var stringRes: String
    lateinit var mixpanel : MixpanelAPI

    companion object {
        const val TAG = "TopicResultFragment"

        fun newInstance(res : BaseTextAnalyser.Result): TopicResultFragment {
            val fragment: TopicResultFragment = TopicResultFragment()

            val args: Bundle = Bundle()
            args.putString("RESULT", res.toString())
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
            stringRes = arguments.getString("RESULT")
        }

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mixpanel = MixpanelAPI.getInstance(context, resources.getString(R.string.mixpanel_token))
        return inflater.inflate(R.layout.fragment_topic_result, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_tmp_res.text = stringRes
    }
}
