package com.leif2k.composition.domain.repository

import com.leif2k.composition.domain.entity.GameSettings
import com.leif2k.composition.domain.entity.Level
import com.leif2k.composition.domain.entity.Question

interface GameRepository {

    fun generateQuestion(maxSumValue: Int, countOfOptions: Int): Question

    fun getGameSettings(level: Level): GameSettings
}