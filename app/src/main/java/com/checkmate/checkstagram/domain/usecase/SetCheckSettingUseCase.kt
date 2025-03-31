package com.checkmate.checkstagram.domain.usecase

import com.checkmate.checkstagram.data.model.request.SetCheckRequestDto
import com.checkmate.checkstagram.domain.repository.CheckRepository
import javax.inject.Inject

class SetCheckSettingUseCase @Inject constructor(
    private val repository: CheckRepository
) {
    suspend operator fun invoke(username: String, dto: SetCheckRequestDto): Result<String> {
        return repository.setCheckSetting(username, dto)
    }
}