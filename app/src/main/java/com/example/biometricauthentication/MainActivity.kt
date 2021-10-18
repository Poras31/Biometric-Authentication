package com.example.biometricauthentication

import android.hardware.biometrics.BiometricManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import java.util.concurrent.Executor


class MainActivity : AppCompatActivity() {

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private lateinit var authStatusTv:TextView
    private lateinit var authBtn:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        authStatusTv=findViewById(R.id.authStatusTv)
        authBtn=findViewById(R.id.authBtn)

        executor=ContextCompat.getMainExecutor(this)
        biometricPrompt=BiometricPrompt(this@MainActivity,executor,object :BiometricPrompt.AuthenticationCallback(){
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
//                auth error or stop tasks that requires auth
                authStatusTv.text="Authentication Error:$errString"
                Toast.makeText(this@MainActivity, "Authentication Error:$errString", Toast.LENGTH_SHORT).show()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
//                auth succeed or do task that require auth
                authStatusTv.text="Authentication Succeed..!"
                Toast.makeText(this@MainActivity, "Authentication Succeed..!", Toast.LENGTH_SHORT).show()
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
//                if auth failed or stop task to require auth
                authStatusTv.text="Authentication Failed..!"
                Toast.makeText(this@MainActivity, "Authentication Failed..!", Toast.LENGTH_SHORT).show()
            }
        })
//        set properties tittle or description
        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Authentication")
            .setSubtitle("Login using Fingerprint Authentication")
            .setNegativeButtonText("Use App Password Instead")
            .build()


//        handle click to start authentication dialog
        authBtn.setOnClickListener {
//            show auth dialog
            biometricPrompt.authenticate(promptInfo)
        }

    }
}