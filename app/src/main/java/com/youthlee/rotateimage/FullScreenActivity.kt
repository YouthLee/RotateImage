package com.youthlee.rotateimage

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_screen_full.*

/**
 * Created by lyp on 2020/4/13
 */
class FullScreenActivity : AppCompatActivity() {

    companion object {

        private const val KEY_LIST = "l"

        fun newInstance(context : Context, list:ArrayList<String>) {
            Intent(context, FullScreenActivity::class.java)
                .putExtra(KEY_LIST, list).let {
                    context.startActivity(it)
                }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_screen_full)

        //状态栏透明，布局延伸
        window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
        window?.run {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                statusBarColor = Color.TRANSPARENT
            }
        }

        initData()
    }

    private fun initData() {
        intent.getStringArrayListExtra(KEY_LIST)?.let {
            iv_rotate.controller.loadImageList(it, false)
        }

        iv_back.setOnClickListener {
            onBackPressed()
        }
    }

}