package com.leif2k.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.leif2k.composition.R
import com.leif2k.composition.databinding.FragmentGameFinishedBinding
import com.leif2k.composition.domain.entity.GameResult
import java.util.Locale

class GameFinishedFragment : Fragment() {

    private var _binding: FragmentGameFinishedBinding? = null
    private val binding: FragmentGameFinishedBinding
        get() = _binding ?: throw RuntimeException("FragmentGameFinishedBinding == null")


    private lateinit var gameResult: GameResult



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showGameResult()
        addCallBacks()
        setClickListeners()

    }


    private fun showGameResult() {
        with(binding) {

            if (gameResult.winner) {
                emojiResult.setImageResource(R.drawable.ic_smile)
            } else {
                emojiResult.setImageResource(R.drawable.ic_sad)
            }

            tvRequiredAnswers.text = String.format(
                Locale.ROOT,
                getString(R.string.required_score),
                gameResult.gameSettings.minCountOfRightAnswers.toString()
            )

            tvScoreAnswers.text = String.format(
                Locale.ROOT,
                getString(R.string.score_answers),
                gameResult.countOfRightAnswers.toString()
            )

            tvRequiredPercentage.text = String.format(
                Locale.ROOT,
                getString(R.string.required_percentage),
                gameResult.gameSettings.minPercentOfRightAnswers.toString()
            )


            tvScorePercentage.text = String.format(
                Locale.ROOT,
                getString(R.string.score_percentage),
                calculatePercentOfRightAnswers().toString()
            )
        }
    }
    private fun retryGame() {
        requireActivity().supportFragmentManager.popBackStack(
            GameFragment.NAME,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }

    private fun calculatePercentOfRightAnswers(): Int {
        if (gameResult.countOfQuestions == 0) {
            return 0
        }
        return ((gameResult.countOfRightAnswers / gameResult.countOfQuestions.toDouble()) * 100).toInt()
    }
    private fun parseArgs() {
        requireArguments().getParcelable<GameResult>(KEY_GAME_RESULT)?.let {
            gameResult = it
        }
    }
    private fun setClickListeners() {
        binding.buttonRetry.setOnClickListener {
            retryGame()
        }
    }
    private fun addCallBacks() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                retryGame()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object {

        private const val KEY_GAME_RESULT = "game_result"

        fun newInstance(gameResult: GameResult): GameFinishedFragment {
            return GameFinishedFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_GAME_RESULT, gameResult)
                }
            }
        }
    }
}

