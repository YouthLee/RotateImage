package com.youthlee.rotateimage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray

class MainActivity : AppCompatActivity() {

    private val imageList = ArrayList<String>()

    private var firstResume = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        if (!firstResume) {
            initImage()

            tv_full_screen.setOnClickListener {
                FullScreenActivity.newInstance(this, imageList)
            }

            firstResume = true
        }
    }

    private fun initImage() {

        //一段网络数据，嘿嘿
        val dataStr =
            "[{\"Num\":0,\"Url\":\"http://image.bitauto.com/autoalbum/files/20191120/592/20191120191603163238289-750x500-w1.png\",\"info\":null}," +
                    "{\"Num\":1,\"Url\":\"http://image.bitauto.com/autoalbum/files/20191120/488/2019112019160116166154-750x500-w1.png\",\"info\":null}," +
                    "{\"Num\":2,\"Url\":\"http://image.bitauto.com/autoalbum/files/20191120/283/2019112019160216214987-750x500-w1.png\",\"info\":null}," +
                    "{\"Num\":3,\"Url\":\"http://image.bitauto.com/autoalbum/files/20191120/106/20191120191600160255201-750x500-w1.png\",\"info\":null}," +
                    "{\"Num\":4,\"Url\":\"http://image.bitauto.com/autoalbum/files/20191120/725/20191120191556155611471-750x500-w1.png\",\"info\":null}," +
                    "{\"Num\":5,\"Url\":\"http://image.bitauto.com/autoalbum/files/20191120/173/20191120191552155279199-750x500-w1.png\",\"info\":null}," +
                    "{\"Num\":6,\"Url\":\"http://image.bitauto.com/autoalbum/files/20191120/511/201911201915541554193197-750x500-w1.png\",\"info\":null}," +
                    "{\"Num\":7,\"Url\":\"http://image.bitauto.com/autoalbum/files/20191120/035/2019112019155115512288-750x500-w1.png\",\"info\":null}," +
                    "{\"Num\":8,\"Url\":\"http://image.bitauto.com/autoalbum/files/20191120/085/201911201915441544153154-750x500-w1.png\",\"info\":null}," +
                    "{\"Num\":9,\"Url\":\"http://image.bitauto.com/autoalbum/files/20191120/069/20191120191544154485203-750x500-w1.png\",\"info\":null}," +
                    "{\"Num\":10,\"Url\":\"http://image.bitauto.com/autoalbum/files/20191120/908/201911201915451545193289-750x500-w1.png\",\"info\":null}," +
                    "{\"Num\":11,\"Url\":\"http://image.bitauto.com/autoalbum/files/20191120/156/20191120191538153889472-750x500-w1.png\",\"info\":null}," +
                    "{\"Num\":12,\"Url\":\"http://image.bitauto.com/autoalbum/files/20191120/865/201911201915351535170154-750x500-w1.png\",\"info\":null}," +
                    "{\"Num\":13,\"Url\":\"http://image.bitauto.com/autoalbum/files/20191120/829/20191120191537153751256-750x500-w1.png\",\"info\":null}," +
                    "{\"Num\":14,\"Url\":\"http://image.bitauto.com/autoalbum/files/20191120/923/201911201915301530109469-750x500-w1.png\",\"info\":null}," +
                    "{\"Num\":15,\"Url\":\"http://image.bitauto.com/autoalbum/files/20191120/707/20191120191529152910197-750x500-w1.png\",\"info\":null}," +
                    "{\"Num\":16,\"Url\":\"http://image.bitauto.com/autoalbum/files/20191120/541/201911201915271527170398-750x500-w1.png\",\"info\":null}," +
                    "{\"Num\":17,\"Url\":\"http://image.bitauto.com/autoalbum/files/20191120/387/20191120191446144611116-750x500-w1.png\",\"info\":null}," +
                    "{\"Num\":18,\"Url\":\"http://image.bitauto.com/autoalbum/files/20191120/746/20191120191526152658113-750x500-w1.png\",\"info\":null}," +
                    "{\"Num\":19,\"Url\":\"http://image.bitauto.com/autoalbum/files/20191120/473/201911201915231523181313-750x500-w1.png\",\"info\":null}," +
                    "{\"Num\":20,\"Url\":\"http://image.bitauto.com/autoalbum/files/20191120/464/20191120191520152096389-750x500-w1.png\",\"info\":null}," +
                    "{\"Num\":21,\"Url\":\"http://image.bitauto.com/autoalbum/files/20191120/444/201911201915221522250388-750x500-w1.png\",\"info\":null}," +
                    "{\"Num\":22,\"Url\":\"http://image.bitauto.com/autoalbum/files/20191120/894/20191120191518151824065-750x500-w1.png\",\"info\":null}," +
                    "{\"Num\":23,\"Url\":\"http://image.bitauto.com/autoalbum/files/20191120/873/201911201915141514216391-750x500-w1.png\",\"info\":null}," +
                    "{\"Num\":24,\"Url\":\"http://image.bitauto.com/autoalbum/files/20191120/878/20191120191512151278113-750x500-w1.png\",\"info\":null}," +
                    "{\"Num\":25,\"Url\":\"http://image.bitauto.com/autoalbum/files/20191120/757/20191120191509159256468-750x500-w1.png\",\"info\":null}," +
                    "{\"Num\":26,\"Url\":\"http://image.bitauto.com/autoalbum/files/20191120/887/2019112019150715737389-750x500-w1.png\",\"info\":null}," +
                    "{\"Num\":27,\"Url\":\"http://image.bitauto.com/autoalbum/files/20191120/225/2019112019150315378153-750x500-w1.png\",\"info\":null}," +
                    "{\"Num\":28,\"Url\":\"http://image.bitauto.com/autoalbum/files/20191120/998/20191120191507157145398-750x500-w1.png\",\"info\":null}," +
                    "{\"Num\":29,\"Url\":\"http://image.bitauto.com/autoalbum/files/20191120/914/2019112019150215259312-750x500-w1.png\",\"info\":null}," +
                    "{\"Num\":30,\"Url\":\"http://image.bitauto.com/autoalbum/files/20191120/113/201911201914571457166469-750x500-w1.png\",\"info\":null}," +
                    "{\"Num\":31,\"Url\":\"http://image.bitauto.com/autoalbum/files/20191120/974/201911201914551455152154-750x500-w1.png\",\"info\":null}," +
                    "{\"Num\":32,\"Url\":\"http://image.bitauto.com/autoalbum/files/20191120/008/201911201914551455239116-750x500-w1.png\",\"info\":null}," +
                    "{\"Num\":33,\"Url\":\"http://image.bitauto.com/autoalbum/files/20191120/519/201911201914501450178388-750x500-w1.png\",\"info\":null}," +
                    "{\"Num\":34,\"Url\":\"http://image.bitauto.com/autoalbum/files/20191120/679/20191120191448144894298-750x500-w1.png\",\"info\":null}," +
                    "{\"Num\":35,\"Url\":\"http://image.bitauto.com/autoalbum/files/20191120/149/201911201914481448172398-750x500-w1.png\",\"info\":null}]"

        val dataArray = JSONArray(dataStr)

        //解析一下
        for (index in 0 until dataArray.length()) {
            imageList.add(dataArray.getJSONObject(index).optString("Url"))
        }


        rotate_image_view.controller.loadImageList(imageList, true)
    }
}
