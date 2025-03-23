package com.checkmate.checkstagram.data.respository

import android.content.Context
import android.net.Uri
import com.checkmate.checkstagram.data.model.request.FeedCheckResponseDto
import com.checkmate.checkstagram.data.model.request.PostFeedMetaDto
import com.checkmate.checkstagram.data.source.StarDataSource
import com.checkmate.checkstagram.domain.model.MediaInfo
import com.checkmate.checkstagram.domain.repository.StarRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject
import com.squareup.moshi.Types


class StarRepositoryImpl @Inject constructor(
    private val dataSource: StarDataSource,
    @ApplicationContext private val context: Context
) : StarRepository {

    override suspend fun checkFeed(
        mediaInfos: List<MediaInfo>,
        description: String?,
        categoriesJson: String
    ): Result<FeedCheckResponseDto> {
        return try {
            val medias = mediaInfos.map {
                uriToMultipartPart(context, Uri.parse(it.mediaUrl))
            }
            val metasJson = mediaInfos.map { PostFeedMetaDto(it.mediaType) }.toJson()

            dataSource.checkFeed(medias, metasJson, categoriesJson, description)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

private fun uriToMultipartPart(
    context: Context,
    uri: Uri,
    partName: String = "medias"
): MultipartBody.Part {
    val contentResolver = context.contentResolver
    val inputStream = contentResolver.openInputStream(uri)
        ?: throw IllegalArgumentException("Uri를 읽을 수 없습니다: $uri")

    val file = File.createTempFile("upload_", null, context.cacheDir)
    file.outputStream().use { inputStream.copyTo(it) }

    val mimeType = contentResolver.getType(uri) ?: "application/octet-stream"
    val requestBody = file.asRequestBody(mimeType.toMediaTypeOrNull())

    return MultipartBody.Part.createFormData(partName, file.name, requestBody)
}

fun List<PostFeedMetaDto>.toJson(): String {
    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val type = Types.newParameterizedType(List::class.java, PostFeedMetaDto::class.java)
    val adapter = moshi.adapter<List<PostFeedMetaDto>>(type)

    return adapter.toJson(this)
}