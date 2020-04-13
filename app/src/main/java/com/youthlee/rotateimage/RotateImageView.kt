package com.youthlee.rotateimage

import android.animation.IntEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class RotateImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    val controller: RotateController = RotateController(this, context)

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return controller.onTouchEvent(event)
    }

}

interface OnRotateActionListener {
    fun onSourceReady() {}

    fun onClick() {}
}

class RotateController(private val rotateView: RotateImageView, context: Context) :
    LifecycleObserver {

    private val disposableHelper = CompositeDisposable()

    private val gestureDetector: GestureDetector

    private val motorImageList = mutableListOf<String>()

    private var isSourceReady = false

    private var timerDisposable: Disposable? = null

    private var currentIndex = 0

    //累积播放的帧数
    private var accumulate = 0

    private var anim: ValueAnimator? = null

    private var isAutoShow = true

    var actionistener: OnRotateActionListener? = null

    init {
        gestureDetector = GestureDetector(context, object : GestureDetector.OnGestureListener {
            override fun onShowPress(e: MotionEvent?) {
            }

            override fun onSingleTapUp(e: MotionEvent?): Boolean {
                actionistener?.onClick()
                return true
            }

            override fun onDown(e: MotionEvent?): Boolean {
                return true
            }

            override fun onFling(
                e1: MotionEvent?,
                e2: MotionEvent?,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                if (kotlin.math.abs(velocityX) < 150) return false

                if (velocityX > 0) {
                    accumulate += 5
                } else {
                    accumulate -= 5
                }
                return true
            }

            override fun onScroll(
                e1: MotionEvent?,
                e2: MotionEvent?,
                distanceX: Float,
                distanceY: Float
            ): Boolean {

                when {
                    kotlin.math.abs(distanceX) < 1f -> {
                        accumulate = 0
                    }

                    kotlin.math.abs(distanceX) < 3f -> {
                        if (distanceX > 0) {
                            accumulate = -1
                        } else if (distanceX < 0) {
                            accumulate = 1
                        }
                    }

                    distanceX > 0 -> {
                        if (accumulate < 0) accumulate = 0
                        accumulate--
                    }
                    else -> {
                        if (accumulate > 0) accumulate = 0
                        accumulate++
                    }
                }

                return true
            }

            override fun onLongPress(e: MotionEvent?) {
            }
        })

        gestureDetector.setIsLongpressEnabled(false)

        if (context is AppCompatActivity) {
            context.lifecycle.addObserver(this)
        }
    }

    private fun addIndex() {
        if (isSourceReady) {
            currentIndex++

            if (currentIndex >= motorImageList.size)
                currentIndex = 0

            Glide.with(rotateView).load(motorImageList[currentIndex]).dontAnimate()
                .placeholder(rotateView.drawable).into(rotateView)
        }
    }

    private fun reduceIndex() {
        if (isSourceReady) {
            currentIndex--

            if (currentIndex < 0)
                currentIndex = motorImageList.size - 1

            Glide.with(rotateView).load(motorImageList[currentIndex]).dontAnimate()
                .placeholder(rotateView.drawable).into(rotateView)
        }
    }

    private fun getFirstImage(url: String) =
        Completable.create {
            Glide.with(rotateView)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(rotateView)
            it.onComplete()
        }.subscribeOn(AndroidSchedulers.mainThread())

    private fun getSingleImage(url: String) =
        Completable.create {
            Glide.with(rotateView).asFile()
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .submit()
            it.onComplete()
        }.subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())

    private fun prepareImageSource() {
        val actionList = ArrayList<Completable>()
        motorImageList.forEachIndexed { index, data ->
            if (index == 0)
                actionList.add(getFirstImage(data))
            else
                actionList.add(getSingleImage(data))
        }

        Completable.merge(actionList)
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onComplete() {
                    loadComplete()
                }

                override fun onError(e: Throwable) {
                    Log.e("xxtest", "download error", e)
                    //to can not scroll
                }

                override fun onSubscribe(d: Disposable) {
                    disposableHelper.add(d)
                }
            })
    }

    private fun loadComplete() {
        Log.d("xxtest", "=========loadComplete")

        actionistener?.onSourceReady()

        if (!isAutoShow) {
            isSourceReady = true
            initTimer()
            return
        }

        anim = ValueAnimator.ofObject(IntEvaluator(), 1, motorImageList.size)
        anim?.duration = 1800
        anim?.addUpdateListener {
            val value = it.animatedValue as Int
            if (currentIndex != value) {
                currentIndex = if (value >= motorImageList.size) {
                    0
                } else {
                    value
                }
                Glide.with(rotateView).load(motorImageList[currentIndex]).dontAnimate()
                    .placeholder(rotateView.drawable).into(rotateView)

                if (currentIndex == 0) {
                    isSourceReady = true
                    initTimer()
                }
            }
        }
        anim?.start()
    }

    private fun initTimer() {
        timerDisposable = Observable.interval(40, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<Long?>() {
                override fun onNext(time: Long) {
                    Log.d("xxtest", "timer = $time")

                    if (accumulate > 0) {
                        accumulate--
                        addIndex()
                    } else if (accumulate < 0) {
                        accumulate++
                        reduceIndex()
                    }
                }

                override fun onError(e: Throwable) {}
                override fun onComplete() {}
            })
    }

    fun loadSingleImage(imageUrl: String) {
        rotateView.scaleType = ImageView.ScaleType.CENTER_CROP

        Glide.with(rotateView).load(imageUrl).dontAnimate().into(rotateView)
    }

    fun loadImageList(imageList: List<String>, isAutoShow: Boolean = true) {
        Log.d("xxtest", "======= loadImageList")

        this.isAutoShow = isAutoShow

        when (imageList.size) {
            0 -> {
            }
            1 -> loadSingleImage(imageList[0])
            else -> {
                rotateView.scaleType = ImageView.ScaleType.FIT_CENTER
                motorImageList.addAll(imageList)
                prepareImageSource()
            }
        }
    }

    fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            if (it.action == MotionEvent.ACTION_UP || it.action == MotionEvent.ACTION_CANCEL) {
                accumulate = 0
            }
        }

        rotateView.parent?.requestDisallowInterceptTouchEvent(true)
        return gestureDetector.onTouchEvent(event)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        disposableHelper.dispose()
        timerDisposable?.dispose()
        anim?.removeAllUpdateListeners()
        anim?.cancel()
    }
}