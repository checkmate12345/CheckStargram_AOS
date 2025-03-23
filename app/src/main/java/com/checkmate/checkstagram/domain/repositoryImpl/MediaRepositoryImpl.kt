import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import com.checkmate.checkstagram.data.respository.MediaRepository
import com.checkmate.checkstagram.domain.model.MediaInfo
import javax.inject.Inject

class MediaRepositoryImpl @Inject constructor() : MediaRepository {

    override suspend fun getMediaList(context: Context): List<MediaInfo> {
        val mediaList = mutableListOf<MediaInfo>()

        val projection = arrayOf(
            MediaStore.MediaColumns._ID,
            MediaStore.Files.FileColumns.MEDIA_TYPE
        )

        val collection = MediaStore.Files.getContentUri("external")
        val selection = "${MediaStore.Files.FileColumns.MEDIA_TYPE}=? OR ${MediaStore.Files.FileColumns.MEDIA_TYPE}=?"
        val selectionArgs = arrayOf(
            MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE.toString(),
            MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO.toString()
        )

        val sortOrder = "${MediaStore.MediaColumns.DATE_ADDED} DESC"

        context.contentResolver.query(
            collection, projection, selection, selectionArgs, sortOrder
        )?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns._ID)
            val typeColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.MEDIA_TYPE)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val mediaTypeInt = cursor.getInt(typeColumn)

                val (contentUri, typeString) = when (mediaTypeInt) {
                    MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE ->
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI to "img"

                    MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO ->
                        MediaStore.Video.Media.EXTERNAL_CONTENT_URI to "video"

                    else -> continue
                }

                val uri = ContentUris.withAppendedId(contentUri, id)
                mediaList.add(MediaInfo(mediaUrl = uri.toString(), mediaType = typeString))
            }
        }

        return mediaList
    }
}
