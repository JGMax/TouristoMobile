package ru.inteam.touristo.common.data.shared_media

import java.io.File
import java.io.InputStream

interface ImageCompressor {
    suspend fun compress(file: File): File
    suspend fun compress(inputStream: InputStream): File
}
