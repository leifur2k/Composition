package com.leif2k.composition.presentation.binding

import android.content.Context
import android.content.res.ColorStateList
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.leif2k.composition.R
import com.leif2k.composition.domain.entity.GameResult

// GameFragment
@BindingAdapter("numbersAsText")
fun numbersAsText(textView: TextView, number: Int) {
    textView.text = number.toString()
}


@BindingAdapter("tvAnswersProgressColor")
fun tvAnswersProgressColor(textView: TextView, enough: Boolean) {
    val resIdColor = getColorByState(textView.context, enough)
    textView.setTextColor(resIdColor)
}

@BindingAdapter("progressBarColor")
fun progressBarColor(progressBar: ProgressBar, enough: Boolean) {
    val color = getColorByState(progressBar.context, enough)
    progressBar.progressTintList = ColorStateList.valueOf(color)
}

private fun getColorByState(context: Context, goodState: Boolean): Int {
    val colorResId = if (goodState) {
        android.R.color.holo_green_light
    } else {
        android.R.color.holo_red_light
    }
    return ContextCompat.getColor(context, colorResId)
}


interface OnOptionClickListener {
    fun onOptionClick(option: Int)
}

@BindingAdapter("onOptionClickListener")
fun onOptionClickListener(textView: TextView, clickListener: OnOptionClickListener) {
    textView.setOnClickListener {
        clickListener.onOptionClick(textView.text.toString().toInt())
    }
}


// GameFinishedFragment
@BindingAdapter("requiredAnswers")
fun bindRequiredAnswers(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.required_score),
        count
    )
}

@BindingAdapter("scoreAnswers")
fun bindScoreAnswers(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.score_answers),
        count
    )
}

@BindingAdapter("requiredPercentage")
fun bindRequiredPercentage(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.required_percentage),
        count
    )
}


@BindingAdapter("scorePercentage")
fun bindScorePercentage(textView: TextView, gameResult: GameResult) {
    textView.text = String.format(
        textView.context.getString(R.string.score_percentage),
        calculatePercentOfRightAnswers(gameResult)
    )
}

private fun calculatePercentOfRightAnswers(gameResult: GameResult): Int {
    if (gameResult.countOfQuestions == 0) {
        return 0
    }
    return ((gameResult.countOfRightAnswers / gameResult.countOfQuestions.toDouble()) * 100).toInt()
}


@BindingAdapter("emojiResult")
fun bindEmojiResult(imageView: ImageView, winner: Boolean) {
    var resId = R.drawable.ic_sad
    if (winner) {
        resId = R.drawable.ic_smile
    }
    imageView.setImageResource(resId)
}

