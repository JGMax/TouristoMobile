package ru.inteam.touristo.common.util

import java.io.*

fun InputStream.copyToFile(file: File) {
    var out: OutputStream? = null
    try {
        out = FileOutputStream(file)
        val buf = ByteArray(1024)
        var len: Int
        while (read(buf).also { len = it } > 0) {
            out.write(buf, 0, len)
        }
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
    } finally {
        try {
            out?.close()
            close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
