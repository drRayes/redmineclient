package com.rayes.redmineclient

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.rayes.redmineclient.databinding.ActivityLoginBinding
import com.rayes.redmineclient.http.Dispatcher
import com.rayes.redmineclient.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.w3c.dom.Text
import kotlin.coroutines.CoroutineContext

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        val button = findViewById<Button>(R.id.button_login)

        val dispatcher = Dispatcher(this)

        val user = User()
        binding.user = user

        dispatcher.ktorGetToken(user)

        button.setOnClickListener {
            dispatcher.ktorLogin(user)
        }

    }
}