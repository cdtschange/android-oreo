package com.cdts.oreo.data.local

import android.content.Context
import android.os.Environment
import com.cdts.oreo.data.encrypt.md5
import com.cdts.oreo.ui.application.ORApplication
import com.jakewharton.disklrucache.DiskLruCache
import java.io.File
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

open class ORLruCache {

    enum class CacheType {
        Cache, Disk
    }

    companion object {

        var appVersion: Int = 100
        var maxSize: Long = Long.MAX_VALUE

        private val cacheForCache: ORLruCache by lazy {
            val context = ORApplication.application!!
            ORLruCache(context.packageName, context, CacheType.Cache)
        }
        private val cacheForDisk: ORLruCache by lazy {
            val context = ORApplication.application!!
            ORLruCache(context.packageName, context, CacheType.Disk)
        }

        fun getCache(type: CacheType): ORLruCache {
            return when(type) {
                CacheType.Cache -> cacheForCache
                CacheType.Disk -> cacheForDisk
            }
        }
    }

	private var cache: DiskLruCache

    constructor(cacheFile: File) {
        if(!cacheFile.exists()){
            cacheFile.mkdirs()
        }
        cache = DiskLruCache.open(cacheFile, appVersion, 1, maxSize)
    }
    constructor(name: String, context: Context, type: CacheType) {
        val cacheDir: String = when(type) {
            CacheType.Cache -> {
                if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState() || !Environment.isExternalStorageRemovable()) {
                    context.externalCacheDir?.path ?: context.cacheDir.path
                } else {
                    context.cacheDir.path
                }
            }
            CacheType.Disk -> {
                if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState() || !Environment.isExternalStorageRemovable()) {
                    context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.path ?: context.filesDir.path
                } else {
                    context.filesDir.path
                }
            }
        }
        val file = File(cacheDir + File.separator + name)
        if(!file.exists()){
            file.mkdirs()
        }
        cache = DiskLruCache.open(file, appVersion, 1, maxSize)
    }

    fun clear() {
        val dir = cache.directory
        cache.delete()
        cache = DiskLruCache.open(dir, appVersion, 1, maxSize)
    }

    fun getCache(): DiskLruCache {
        return cache
    }

    @Suppress("UNCHECKED_CAST")
    fun<T> get(key: String): T? {
        val snapshot: DiskLruCache.Snapshot?
        try {
            snapshot = cache.get(toInternalKey(key))
        } catch (e: Error) {
            return null
        }
        if (snapshot == null) {
            return null
        }
        snapshot.use {
            val inputStream = it.getInputStream(0)
            val objectInputStream = ObjectInputStream(inputStream)
            return objectInputStream.readObject() as T
        }
    }

    fun contains(key: String): Boolean {
        return try {
            cache.get(toInternalKey(key)) != null
        } catch (e: Error) {
            false
        }
    }

    /**
     * value type support: Int, Boolean, String, Float, Long, Serializable Object
     */
    fun<T> put(key: String, value: T) {
        val editor: DiskLruCache.Editor?
        try {
            editor = cache.edit(toInternalKey(key))
            if (editor == null) {
                return
            }
        } catch (e: Error) {
            return
        }
        val os = editor.newOutputStream(0)
        os.use {
            ObjectOutputStream(it).use { stream ->
                stream.writeObject(value)
                editor.commit()
            }
        }
    }

    private fun toInternalKey(key: String): String {
        return key.md5()
    }
}