package ru.inteam.touristo.common.data.shared_media.image_compressor

import android.content.Context
import android.graphics.Bitmap
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.format
import id.zelory.compressor.constraint.quality
import id.zelory.compressor.constraint.resolution
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.inteam.touristo.common.data.shared_media.ImageCompressor
import ru.inteam.touristo.common.util.copyToFile
import java.io.File
import java.io.InputStream

class ImageCompressorImpl(
    private val context: Context
) : ImageCompressor {

    override suspend fun compress(file: File): File = withContext(Dispatchers.IO) {
        // TODO Calculate resolution and quality
        val compressed = Compressor.compress(context, file) {
            resolution(1280, 720)
            quality(85)
            format(Bitmap.CompressFormat.JPEG)
        }
        // TODO Cache controller to delete all temp files when their time ends (JobScheduler?)
        compressed.deleteOnExit()
        compressed
    }

    override suspend fun compress(inputStream: InputStream): File = withContext(Dispatchers.IO) {
        val temp = File(context.filesDir, "compressed${inputStream.hashCode()}.jpeg")
        inputStream.copyToFile(temp)
        val compressed = compress(temp)
        temp.delete()
        compressed.deleteOnExit()
        compressed
    }
}
