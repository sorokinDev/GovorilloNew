package com.vsquad.projects.govorillo.presentation.presenter.auth

import android.support.design.widget.Snackbar
import android.util.Log
import android.view.View
import com.arellomobile.mvp.InjectViewState
import com.google.android.gms.tasks.Task
import com.vsquad.projects.govorillo.GovorilloApplication
import com.vsquad.projects.govorillo.Screens
import com.vsquad.projects.govorillo.common.PrefConst
import com.vsquad.projects.govorillo.common.SharedPrefUtils
import com.vsquad.projects.govorillo.presentation.presenter.base.BaseFragmentPresenter
import com.vsquad.projects.govorillo.presentation.view.auth.AuthView
import com.facebook.AccessToken
import com.google.firebase.auth.*
import com.vsquad.projects.govorillo.ui.fragment.auth.AuthFragment
import com.vsquad.projects.govorillo.ui.fragment.auth.AuthFragment.Companion.TAG
import org.jetbrains.anko.*


@InjectViewState
class AuthPresenter : BaseFragmentPresenter<AuthView>() {
    override var isOpen: Boolean = true
    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    
    init {
        GovorilloApplication.INSTANCE.getAppComponent().inject(this)
    }

    fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            router.newRootScreen(Screens.FREE_TOPIC_SCREEN)
        }
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
                Log.d("PREF_AUTH_PRES", SharedPrefUtils.sPref.getBoolean(PrefConst.USER_AUTHORIZED, false).toString())
                router.showSystemMessage("Вы успешно авторизовались!")
                updateUI(firebaseUser)
            } else {
                viewState.setWrongPassError()
                print("ERROR: " + task.getException())
                //Auth error
            }
        }
    }

    fun signUp(email: String, password: String){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task: Task<AuthResult> ->
            if (task.isSuccessful) {
                //Registration OK
                val firebaseUser = mAuth.currentUser!!
                router.showSystemMessage("Вы успешно зарегистрировались!")
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
                val firebaseUser = mAuth.currentUser
                SharedPrefUtils.edit().putBoolean(PrefConst.USER_AUTHORIZED, true)
                        .apply()
                router.showSystemMessage("Вы успешно авторизовались!")
                updateUI(firebaseUser)
            } else {
                viewState.setErrorWhileRegistration()
                //Registration error
            }
        }
    }

    fun signInWithPhoneAuthCredential(credentials: PhoneAuthCredential){
        Log.d(TAG, "signInWithPhoneAuthCredential");
        mAuth.signInWithCredential(credentials).addOnCompleteListener { task: Task<AuthResult> ->
            if (task.isSuccessful) {
                //Auth or Sign up OK
                val firebaseUser = mAuth.currentUser
                SharedPrefUtils.edit().putBoolean(PrefConst.USER_AUTHORIZED, true)
                        .apply()
                router.showSystemMessage("Вы успешно авторизовались!")
                updateUI(firebaseUser)
            } else {
                viewState.setErrorWhileRegistration()
                //Auth or Sign up error
            }
        }
    }
}