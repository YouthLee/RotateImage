package com.youthlee.rotateimage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        initImage()
    }

    private fun initImage() {

        //这里图片虽随便找了找，没有按顺序排
        val imageList = ArrayList<String>().apply {
            add("http://image.bitauto.com/autoalbum/files/20181001/272/2018100120265626569415-750x500-w1.png")
            add("http://image.bitauto.com/autoalbum/files/20181001/288/2018100120265526555016-750x500-w1.png")
            add("http://image.bitauto.com/autoalbum/files/20181001/412/201810012027012711795-750x500-w1.png")
            add("http://image.bitauto.com/autoalbum/files/20181001/225/2018100120265926591015-750x500-w1.png")
            add("http://image.bitauto.com/autoalbum/files/20181001/022/2018100120270027017018-750x500-w1.png")
            add("http://image.bitauto.com/autoalbum/files/20181001/100/2018100120270227225124-750x500-w1.png")
            add("http://image.bitauto.com/autoalbum/files/20181001/303/2018100120270327319125-750x500-w1.png")
            add("http://image.bitauto.com/autoalbum/files/20181001/397/2018100120270527513222-750x500-w1.png")
            add("http://image.bitauto.com/autoalbum/files/20181001/803/201810012027072772447-750x500-w1.png")
            add("http://image.bitauto.com/autoalbum/files/20181001/724/201810012027092797124-750x500-w1.png")
//            add("http://image.bitauto.com/autoalbum/files/20181001/272/2018100120265626569415-750x500-w1.png")
//            add("http://image.bitauto.com/autoalbum/files/20181001/272/2018100120265626569415-750x500-w1.png")
//            add("http://image.bitauto.com/autoalbum/files/20181001/272/2018100120265626569415-750x500-w1.png")
//            add("http://image.bitauto.com/autoalbum/files/20181001/272/2018100120265626569415-750x500-w1.png")
//            add("http://image.bitauto.com/autoalbum/files/20181001/272/2018100120265626569415-750x500-w1.png")
//            add("http://image.bitauto.com/autoalbum/files/20181001/272/2018100120265626569415-750x500-w1.png")
//            add("http://image.bitauto.com/autoalbum/files/20181001/272/2018100120265626569415-750x500-w1.png")
//            add("http://image.bitauto.com/autoalbum/files/20181001/272/2018100120265626569415-750x500-w1.png")
//            add("http://image.bitauto.com/autoalbum/files/20181001/272/2018100120265626569415-750x500-w1.png")
//            add("http://image.bitauto.com/autoalbum/files/20181001/272/2018100120265626569415-750x500-w1.png")
//            add("http://image.bitauto.com/autoalbum/files/20181001/272/2018100120265626569415-750x500-w1.png")
        }


        rotate_image_view.controller.loadImageList(imageList, true)
    }
}
