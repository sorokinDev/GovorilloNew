package com.vsquad.projects.govorillo.ui.fragment.random_topic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.vsquad.projects.govorillo.R
import com.vsquad.projects.govorillo.presentation.view.random_topic.RandomTopicResultFragmentView
import com.vsquad.projects.govorillo.presentation.presenter.random_topic.RandomTopicResultFragmentPresenter

import com.arellomobile.mvp.presenter.InjectPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class RandomTopicResultFragment : MvpAppCompatFragment(), RandomTopicResultFragmentView {
    companion object {
        const val TAG = "RandomTopicResultFragment"

        fun newInstance(): RandomTopicResultFragment {
            val fragment: RandomTopicResultFragment = RandomTopicResultFragment()
            val args: Bundle = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    //region INJECTIONS
    @Inject
    lateinit var router: Router

    @InjectPresenter
    lateinit var mRandomTopicResultFragmentPresenter: RandomTopicResultFragmentPresenter
    //endregion

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_random_topic_result, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
