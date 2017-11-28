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
import android.net.Uri
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
import com.mixpanel.android.mpmetrics.MixpanelAPI
import com.vsquad.projects.govorillo.ui.activity.main.MainActivity
import ru.terrakok.cicerone.Router
import javax.inject.Inject


class AuthFragment : BaseFragment(), AuthView {

    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    var callbackManager: CallbackManager = CallbackManager.Factory.create()
    lateinit var facebookSignInButton: LoginButton
    private lateinit var mResendToken : PhoneAuthProvider.ForceResendingToken
    lateinit var mVerificationId : String
    private lateinit var mCallbacks : PhoneAuthProvider.OnVerificationStateChangedCallbacks
    lateinit var mixpanel : MixpanelAPI

    @Inject lateinit var router: Router



    override fun onCreate(savedInstanceState: Bundle?) {
        GovorilloApplication.INSTANCE.getAppComponent().inject(this)
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
    }

    override var fragmentTitle: String = "Войти в аккаунт"

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
        /*
        if(mAuth.currentUser != null) {
            mAuthPresenter.updateUI(mAuth.currentUser)
        }
        */
    }

    @InjectPresenter
    lateinit var mAuthPresenter: AuthPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        FacebookSdk.sdkInitialize(getApplicationContext())
        AppEventsLogger.activateApp(getApplicationContext())
        mixpanel = MixpanelAPI.getInstance(context, resources.getString(R.string.mixpanel_token))
        mixpanel.track("[AuthPresenter]")
        return inflater.inflate(R.layout.fragment_auth, container, false)

    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_sign_in.setOnClickListener {
            mixpanel.identify(et_email.text.toString())
            mixpanel.people.set("email or phone",et_email.text.toString())
            mixpanel.track("[AuthPresenter] -> Trying to sign in with email/phone number")
            if (!et_pass.isEnabled && et_email.text.toString() != "" && et_email.text.length >= 10) {
                var phoneNumber : String = TelNumberValidator().validate(et_email.text.toString())
                if(phoneNumber == ""){
                    router.showSystemMessage("Номер телефона введен в неверном формате.")
                    return@setOnClickListener
                }
                Log.d("PhoneVer", "Start")
                PhoneAuthProvider.getInstance(mAuth).verifyPhoneNumber(
                        phoneNumber,        // Phone number to verify
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


                activity.alert {
                    customView {
                        verticalLayout {
                            title("Подтверждение номера")
                            val task = editText {
                                hint = "Код из смс сообщения "
                                padding = dip(20)
                            }
                            positiveButton("Подтвердить") {
                                task.text.toString()        // OnVerificationStateChangedCallbacks
                                var credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(mVerificationId, task.text.toString())
                                mAuthPresenter.signInWithPhoneAuthCredential(credential);
                            }
                        }
                    }
                }.show()
            } else if (et_pass.isEnabled){
                if (EmailValidator().validate(et_email.text.toString())) {
                    mAuthPresenter.trySignIn(et_email.text.toString(), et_pass.text.toString())
                }
            }
        }

        btn_sign_up.setOnClickListener {
            mixpanel.identify(et_email.text.toString())
            mixpanel.people.set("email or phone",et_email.text.toString())
            mixpanel.track("[AuthPresenter] -> Trying to sign up with email/phone number")
            if (et_pass.isEnabled == false) {
                var phoneNumber : String = TelNumberValidator().validate(et_email.text.toString())
                if(phoneNumber == ""){
                    router.showSystemMessage("Номер телефона введен в неверном формате.")
                    return@setOnClickListener
                }
                Log.d("PhoneVer", "Start")
                PhoneAuthProvider.getInstance(mAuth).verifyPhoneNumber(
                        phoneNumber,        // Phone number to verify
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


                activity.alert {
                    customView {
                        verticalLayout {
                            title("Подтверждение номера")
                            val task = editText {
                                hint = "Код из смс сообщения "
                                padding = dip(20)
                            }
                            positiveButton("Подтвердить") {
                                task.text.toString()        // OnVerificationStateChangedCallbacks
                                var credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(mVerificationId, task.text.toString())
                                mAuthPresenter.signInWithPhoneAuthCredential(credential);
                            }
                        }
                    }
                }.show()
            } else if (et_pass.isEnabled == true){
                if (EmailValidator().validate(et_email.text.toString())) {
                    mAuthPresenter.signUp(et_email.text.toString(), et_pass.text.toString())
                }
            }
        }

        btn_sign_facebook.setOnClickListener {
            mixpanel.track("[AuthPresenter] -> Trying to sign in with facebook")
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
                    mixpanel.identify(loginResult.accessToken.userId)
                    mixpanel.people.set("facebook user id",loginResult.accessToken.userId)
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

        user.onClick {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://govorillo.ru/docs")))
        }

        which_method_email.setOnClickListener {
            textInput_email.hint = resources.getString(R.string.hint_email)
            et_email.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            textInput_pass.visibility = View.VISIBLE
            et_pass.enabled = true
            which_method_email.setBackgroundColor(resources.getColor(R.color.enabledButtonAuth))
            which_method_tel.setBackgroundColor(resources.getColor(R.color.disabledButtonAuth))
        }

        which_method_tel.setOnClickListener {
            textInput_email.hint = resources.getString(R.string.hint_tel)
            et_email.inputType = InputType.TYPE_CLASS_PHONE
            textInput_pass.visibility = View.GONE
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