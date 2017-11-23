package com.vsquad.projects.govorillo.ui.fragment.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vsquad.projects.govorillo.R
import com.vsquad.projects.govorillo.presentation.view.auth.AuthView
import com.vsquad.projects.govorillo.presentation.presenter.auth.AuthPresenter

import com.arellomobile.mvp.presenter.InjectPresenter
import com.facebook.*
import com.facebook.FacebookSdk.getApplicationContext
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.vsquad.projects.govorillo.GovorilloApplication
import com.vsquad.projects.govorillo.ui.fragment.base.BaseFragment

import kotlinx.android.synthetic.main.fragment_auth.*
import android.content.Intent
import android.graphics.MaskFilter
import android.provider.ContactsContract
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.InputType
import android.text.util.Linkify
import com.facebook.login.LoginManager
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import java.util.*
import org.jetbrains.anko.*
import java.util.concurrent.TimeUnit
import com.google.firebase.auth.PhoneAuthProvider
import mehdi.sakout.fancybuttons.FancyButton
import org.intellij.lang.annotations.JdkConstants


class AuthFragment : BaseFragment(), AuthView {

    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    var callbackManager: CallbackManager = CallbackManager.Factory.create()
    lateinit var facebookSignInButton: LoginButton
    private lateinit var mResendToken : PhoneAuthProvider.ForceResendingToken
    lateinit var mVerificationId : String
    private lateinit var mCallbacks : PhoneAuthProvider.OnVerificationStateChangedCallbacks

    override fun onCreate(savedInstanceState: Bundle?) {
        GovorilloApplication.INSTANCE.getAppComponent().inject(this)
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        //FacebookSdk.sdkInitialize(getApplicationContext())
        //AppEventsLogger.activateApp(this@AuthFragment)
    }

    override var fragmentTitle: String = "Авторизация"

    override fun setWrongPassError() {
        textInput_pass.error = resources.getString(R.string.wrong_pass)
    }

    override fun setErrorWhileRegistration() {
        textInput_pass.error = resources.getString(R.string.error_while_reg)
    }

    companion object {
        const val TAG = "AuthFragment"

        fun newInstance(): AuthFragment {
            val fragment: AuthFragment = AuthFragment()
            val args: Bundle = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onStart() {
        super.onStart()
        // TODO: Настроить обновление UI при старте
        //mAuthPresenter.updateUI(mAuth.currentUser)
    }

    @InjectPresenter
    lateinit var mAuthPresenter: AuthPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(getApplicationContext());
        return inflater.inflate(R.layout.fragment_auth, container, false)

    }

        override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_sign_in.setOnClickListener {
            Log.d("PhoneVer", "Start")
            PhoneAuthProvider.getInstance(mAuth).verifyPhoneNumber(
                    et_email.text.toString(),        // Phone number to verify
                    60,                 // Timeout duration
                    TimeUnit.SECONDS,   // Unit of timeout
                    this@AuthFragment.activity,               // Activity (for callback binding)
                    object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                            Log.d(TAG, "onVerificationCompleted:" + credential);
                            mAuthPresenter.signInWithPhoneAuthCredential(credential);
                        }


                        override fun onVerificationFailed(e: FirebaseException) {
                            Log.w(TAG, "onVerificationFailed", e);
                            println("Something went wrong")
                        }


                        override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                            Log.d(TAG, "onCodeSent:" + verificationId);
                            mVerificationId = verificationId;
                            mResendToken = token;
                        }
                    }
            )

//            activity.alert {
//                customView {
//                    verticalLayout {
//                        title("Подтверждение номера")
//                        val task = editText {
//                            hint = "Код из смс сообщения "
//                            padding = dip(20)
//                        }
//                        positiveButton("Подтвердить") {
//                            task.text.toString()        // OnVerificationStateChangedCallbacks
//                        }
//                    }
//                }
//            }.show()

//            if (EmailValidator().validate(et_email.text.toString())) {
//                mAuthPresenter.trySignIn(et_email.text.toString(), et_pass.text.toString())
//            }
        }

        btn_sign_up.setOnClickListener {
            if (EmailValidator().validate(et_email.text.toString())) {
                mAuthPresenter.signUp(et_email.text.toString(), et_pass.text.toString())
            }
        }

        btn_sign_facebook.setOnClickListener {
            callbackManager = CallbackManager.Factory.create()
            LoginManager
                    .getInstance()
                    .logInWithReadPermissions(
                            this,
                            Arrays.asList("public_profile", "user_friends", "email")
                    )
            LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    // App code
                    Log.d("FACEBOOK_AUTH", "Success")
                    mAuthPresenter.signInWithFacebook(loginResult.accessToken);
                }

                override fun onCancel() {
                    // App code
                    Log.d("FACEBOOK_AUTH", "Cancel")
                }

                override fun onError(exception: FacebookException) {
                    // App code
                    Log.d("FACEBOOK_AUTH", "Error")
                }
            })
        }

            which_method_email.setOnClickListener {
                textInput_email.hint = resources.getString(R.string.hint_email)
                et_email.setRawInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
                et_pass.enabled = true
                which_method_email.setBackgroundColor(resources.getColor(R.color.enabledButtonAuth))
                which_method_tel.setBackgroundColor(resources.getColor(R.color.disabledButtonAuth))
            }

            which_method_tel.setOnClickListener {
                textInput_email.hint = resources.getString(R.string.hint_tel)
                et_email.setRawInputType(InputType.TYPE_CLASS_PHONE)
                et_pass.enabled = false
                which_method_email.setBackgroundColor(resources.getColor(R.color.disabledButtonAuth))
                which_method_tel.setBackgroundColor(resources.getColor(R.color.enabledButtonAuth))
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }
}