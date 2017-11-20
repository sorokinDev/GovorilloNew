package com.vsquad.projects.govorillo.presentation.presenter.auth

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.google.android.gms.tasks.Task
import com.vsquad.projects.govorillo.GovorilloApplication
import com.vsquad.projects.govorillo.Screens
import com.vsquad.projects.govorillo.common.PrefConst
import com.vsquad.projects.govorillo.common.SharedPrefUtils
import com.vsquad.projects.govorillo.presentation.presenter.base.BaseFragmentPresenter
import com.vsquad.projects.govorillo.presentation.view.auth.AuthView
import android.support.annotation.NonNull
import com.google.android.gms.tasks.OnCompleteListener
import android.R.attr.password
import android.support.v4.content.ContextCompat.startActivity
import com.vsquad.projects.govorillo.ui.activity.main.MainActivity
import android.content.Intent
import android.R.attr.password
import com.facebook.AccessToken
import com.google.firebase.auth.*
import com.vsquad.projects.govorillo.ui.fragment.auth.AuthFragment.Companion.TAG


@InjectViewState
class AuthPresenter : BaseFragmentPresenter<AuthView>() {
    override var isOpen: Boolean = true
    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    
    init {
        GovorilloApplication.INSTANCE.getAppComponent().inject(this)
    }

    fun trySignIn(email: String, password: String){
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task: Task<AuthResult> ->
            if (task.isSuccessful) {
                //Auth OK
                val firebaseUser = mAuth.currentUser!!
                SharedPrefUtils.edit().putBoolean(PrefConst.USER_AUTHORIZED, true)
                        .putString(PrefConst.USER_EMAIL, email)
                        .putString(PrefConst.USER_PASS, password)
                        .apply()
                updateUI(firebaseUser)
            } else {
                viewState.setWrongPassError()
                print("ERROR: " + task.getException())
                //Auth error
            }
        }
    }

    fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            router.newRootScreen(Screens.FREE_TOPIC_SCREEN)
        }
    }

    fun signUp(email: String, password: String){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task: Task<AuthResult> ->
            if (task.isSuccessful) {
                //Registration OK
                val firebaseUser = mAuth.currentUser!!
                updateUI(firebaseUser)
            } else {
                viewState.setErrorWhileRegistration()
                //Registration error
            }
        }
    }

    fun signInWithFacebook(token : AccessToken){
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        var credentials : AuthCredential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credentials).addOnCompleteListener { task: Task<AuthResult> ->
            if (task.isSuccessful) {
                //Registration OK
                val firebaseUser = mAuth.currentUser!!
                updateUI(firebaseUser)
            } else {
                viewState.setErrorWhileRegistration()
                //Registration error
            }
        }
    }
}