package com.checkmate.checkstagram.domain.usecase

import com.checkmate.checkstagram.data.model.response.CheckResponseDto
import com.checkmate.checkstagram.domain.repository.CheckRepository
import javax.inject.Inject

class GetCheckSettingUseCase @Inject constructor(
    private val repository: CheckRepository
) {
    suspend operator fun invoke(username: String): Result<CheckResponseDto> {
        return repository.getCheckSetting(username)
    }
}