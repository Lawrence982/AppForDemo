package com.netcracker.appfordemo

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.sdk25.coroutines.onHover

/**
 * Created by vibo0917 on 1/22/2018.
 */
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LoginActivityUI().setContentView(this)
    }

    fun gotofuck(name:String,pass:String)
    {
        startActivity(intentFor<MainActivity>("login" to name,"pass" to pass).singleTop())
    }

    class LoginActivityUI : AnkoComponent<LoginActivity> {
        override fun createView(ui: AnkoContext<LoginActivity>) = ui.apply {
            verticalLayout {
                padding = dip(30)
                var name = editText {
                    hint = "Name"
                    textSize = 24f
                }
                var pass = editText {
                    hint = "Password"
                    textSize = 24f
                }
                button("Login") {
                    textSize = 26f
                    onClick {
//                        ctx.toast("Hello, ${name.text}!")
//                    val mainIntent = Intent(ctx, MainActivity::class.java)
//                    mainIntent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
//                    startActivity(mainIntent    )
                        ui.owner.gotofuck(name = name.text.toString(),pass = pass.text.toString())
                    }
                }
            }
        }.view
    }
}
