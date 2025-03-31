package com.checkmate.checkstagram.data.respository

import com.checkmate.checkstagram.data.model.request.SetCheckRequestDto
import com.checkmate.checkstagram.data.model.response.CheckResponseDto
import com.checkmate.checkstagram.data.source.CheckDataSource
import com.checkmate.checkstagram.domain.model.FeedInfo
import com.checkmate.checkstagram.domain.repository.CheckRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class CheckRepositoryImpl @Inject constructor(
    private val dataSource: CheckDataSource
) : CheckRepository {

    override suspend fun login(username: String, password: String): Result<String> {
        return dataSource.login(username, password).mapCatching { response ->
            val name = response.data?.username ?: throw Exception("username이 null입니다.")
            name
        }
    }

    override suspend fun getFeeds(): Result<List<FeedInfo>> {
        return dataSource.getFeeds()
    }

    override suspend fun postFeed(
        username: String,
        medias: List<MultipartBody.Part>,
        mediasMeta: String,
        description: String?
    ): Result<Unit> {
        return dataSource.postFeed(username, medias, mediasMeta, description)
            .mapCatching {
                if (!it.success) throw Exception(it.message)
            }
    }

    override suspend fun setCheckSetting(username: String, dto: SetCheckRequestDto): Result<String> {
        return dataSource.setCheck(username, dto).mapCatching { it.message }
    }

    override suspend fun getCheckSetting(username: String): Result<CheckResponseDto> {
        return dataSource.getCheck(username).fold(
            onSuccess = {
                it.data?.let { data -> Result.success(data) }
                    ?: Result.failure(Exception("데이터가 없습니다"))
            },
            onFailure = { Result.failure(it) }
        )
    }
}