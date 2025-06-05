package com.leif2k.composition.data

import com.leif2k.composition.domain.entity.GameSettings
import com.leif2k.composition.domain.entity.Level
import com.leif2k.composition.domain.entity.Question
import com.leif2k.composition.domain.repository.GameRepository
import kotlin.random.Random

object GameRepositoryImpl : GameRepository {

    private const val MIN_SUM_VALUE = 2
    private const val MIN_ANSWER_VALUE = 1

    override fun generateQuestion(maxSumValue: Int, countOfOptions: Int): Question {
        val sum = Random.nextInt(MIN_SUM_VALUE, maxSumValue + 1)
        val visibleNumber = Random.nextInt(MIN_ANSWER_VALUE, sum)

        val options = HashSet<Int>()
        val rightAnswer = sum - visibleNumber
        options.add(rightAnswer)

        while (options.size < countOfOptions) {
            options.add(Random.nextInt(MIN_ANSWER_VALUE, sum))
        }

        val shuffledList = options.toList().shuffled()

        return Question(sum, visibleNumber, shuffledList)
    }

    override fun getGameSettings(level: Level): GameSettings {
        return when(level) {
            Level.TEST -> GameSettings(10, 3, 50, 8)
            Level.EASY -> GameSettings(10, 10, 70, 60)
            Level.NORMAL -> GameSettings(30, 30, 90, 40)
            Level.HARD -> GameSettings(10, 3, 50, 40)
        }
    }
}