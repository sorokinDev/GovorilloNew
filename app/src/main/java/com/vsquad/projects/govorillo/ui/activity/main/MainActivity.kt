package com.vsquad.projects.govorillo.ui.activity.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.Toast

import com.arellomobile.mvp.presenter.InjectPresenter
import com.vsquad.projects.govorillo.R
import com.vsquad.projects.govorillo.presentation.view.main.MainActivityView
import com.vsquad.projects.govorillo.presentation.presenter.main.MainActivityPresenter

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.vsquad.projects.govorillo.GovorilloApplication
import com.vsquad.projects.govorillo.Screens
import com.vsquad.projects.govorillo.common.PrefConst
import com.vsquad.projects.govorillo.common.SharedPrefUtils
import com.vsquad.projects.govorillo.model.analyser.BaseTextAnalyser
import com.vsquad.projects.govorillo.ui.fragment.auth.AuthFragment
import com.vsquad.projects.govorillo.ui.fragment.free_topic.FreeTopicFragment
import com.vsquad.projects.govorillo.ui.fragment.profile.ProfileFragment
import com.vsquad.projects.govorillo.ui.fragment.random_topic.RandomTopicFragment
import com.vsquad.projects.govorillo.ui.fragment.result.TopicResultFragment
import com.vsquad.projects.govorillo.ui.fragment.twister.TwisterFragment

import kotlinx.android.synthetic.main.activity_main.*;
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.SupportAppNavigator
import ru.terrakok.cicerone.commands.*
import javax.inject.Inject


class MainActivity : MvpAppCompatActivity(), MainActivityView {

    companion object {
        const val TAG = "MainActivity"
        fun getIntent(context: Context): Intent = Intent(context, MainActivity::class.java)
    }

    //region INJECTIONS
    @Inject
    lateinit var router : Router

    @Inject
    lateinit var navigatorHolder : NavigatorHolder

    @InjectPresenter
    lateinit var mMainActivityPresenter: MainActivityPresenter
    //endregion

    //region VARIABLES
    private lateinit var drawerToggle: ActionBarDrawerToggle
    //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        GovorilloApplication.INSTANCE.getAppComponent().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        drawerToggle = ActionBarDrawerToggle(this, drawer_layout, toolbar,
                R.string.open_drawer, R.string.close_drawer)
        drawer_layout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        nav_view.setNavigationItemSelectedListener(){
            navigationItemSelected(it)
        }

        nav_view.getHeaderView(0).setOnClickListener {
            if(SharedPrefUtils.sPref.getBoolean(PrefConst.USER_AUTHORIZED, false)){
                router.newRootScreen(Screens.PROFILE_SCREEN)
            }else{
                router.newRootScreen(Screens.AUTH_SCREEN)
            }
            drawer_layout.closeDrawer(Gravity.START)
        }
    }

    //region LIFECICLE NEEDS
    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }
    //endregion

    //region NAVIGATION
    private fun navigationItemSelected(item: MenuItem): Boolean {
        mMainActivityPresenter.onDrawerMenuClick(item.itemId)
        nav_view.setCheckedItem(item.itemId)
        drawer_layout.closeDrawer(Gravity.START);
        return true
    }

    private val navigator = object : SupportAppNavigator(this, R.id.frame_content){
        override fun createActivityIntent(screenKey: String?, data: Any?): Intent? {
            return null
        }

        override fun setupFragmentTransactionAnimation(command: Command?, currentFragment: Fragment?, nextFragment: Fragment?, fragmentTransaction: FragmentTransaction?) {
            super.setupFragmentTransactionAnimation(command, currentFragment, nextFragment, fragmentTransaction)
            if(command is Replace){
                fragmentTransaction!!.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            }else if(command is Forward){
                fragmentTransaction!!.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            }

        }

        override fun createFragment(screenKey: String?, data: Any?): Fragment {
            val fragment: Fragment = when(screenKey){
                Screens.FREE_TOPIC_SCREEN -> FreeTopicFragment.newInstance()
                Screens.TWISTER_SCREEN -> TwisterFragment.newInstance()
                Screens.RANDOM_TOPIC_SCREEN -> RandomTopicFragment.newInstance()
                Screens.TOPIC_RESULT_SCREEN -> TopicResultFragment.newInstance(data!! as BaseTextAnalyser.Result)
                Screens.AUTH_SCREEN -> AuthFragment.newInstance()
                Screens.PROFILE_SCREEN -> ProfileFragment.newInstance()
                else -> TwisterFragment.newInstance()
            }
            return fragment
        }

        override fun exit() {
            finish()
        }

        override fun showSystemMessage(message: String) {
            //Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()

            var s : CharSequence = message
            Snackbar.make(frame_content, s, Snackbar.LENGTH_LONG).show()
        }

    }
    //endregion

    //region VIEW STATE IMPLEMENTATION
    override fun setToolbarTitle(str: String) {
        setTitle(str)
    }
    override fun setDefaultDrawerItem() {
        navigationItemSelected(nav_view.menu.findItem(R.id.nav_main))
    }
    //endregion

    fun getMixpanelToken(): String {
        return "5c3fe12980d9a7c1736f393e360b84b6"
    }

}
