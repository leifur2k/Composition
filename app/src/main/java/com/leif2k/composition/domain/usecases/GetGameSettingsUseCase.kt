package com.leif2k.composition.domain.usecases

import com.leif2k.composition.domain.entity.GameSettings
import com.leif2k.composition.domain.entity.Level
import com.leif2k.composition.domain.repository.GameRepository

class GetGameSettingsUseCase(
    private val repository: GameRepository
) {

    operator fun invoke(level: Level): GameSettings {
        return repository.getGameSettings(level)
    }
}