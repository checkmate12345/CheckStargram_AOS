package com.checkmate.checkstagram.domain.usecase

import com.checkmate.checkstagram.domain.repository.CheckRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: CheckRepository
) {
    suspend operator fun invoke (username: String, password: String): Result<String> {
        return repository.login(username, password)
    }
}