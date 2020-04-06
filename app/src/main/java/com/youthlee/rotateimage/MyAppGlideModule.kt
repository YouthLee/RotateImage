package com.youthlee.rotateimage

import android.content.Context
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.module.AppGlideModule


/**
 * Created by liyongpeng on 2020-04-06
 */
@GlideModule
class MyAppGlideModule : AppGlideModule() {
    override fun isManifestParsingEnabled(): Boolean {
        return false
    }

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        super.applyOptions(context, builder)
        val memoryCacheSizeBytes = 1024 * 1024 * 20 // 20 MB
        val diskCacheSizeBytes = 1024 * 1024 * 100 // 100 MB
        val bitmapPoolSizeBytes = 1024 * 1024 * 30 // 30mb
        builder.setBitmapPool(LruBitmapPool(bitmapPoolSizeBytes.toLong()))
        builder.setMemoryCache(LruResourceCache(memoryCacheSizeBytes.toLong()))
        builder.setDiskCache(InternalCacheDiskCacheFactory(context, diskCacheSizeBytes.toLong()))
    }
}