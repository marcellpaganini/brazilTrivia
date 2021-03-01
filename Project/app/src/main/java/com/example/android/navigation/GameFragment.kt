package com.example.android.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.android.navigation.R.*
import com.example.android.navigation.databinding.FragmentGameBinding

class GameFragment : Fragment() {
    data class Question(
            val text: Int,
            val answers: List<String>,
            var answered: String? = null,
            var correct: String? = null
            )

    private val questionBank : MutableList<Question> = mutableListOf(
            Question(text = string.question_1, answers = listOf("False", "True")),
            Question(text = string.question_2, answers = listOf("True", "False")),
            Question(text = string.question_3, answers = listOf("True", "False")),
            Question(text = string.question_4, answers = listOf("False", "True")),
            Question(text = string.question_5, answers = listOf("False", "True")),
            Question(text = string.question_6, answers = listOf("True", "False")),
            Question(text = string.question_7, answers = listOf("False", "True")),
            Question(text = string.question_8, answers = listOf("True", "False")),
            Question(text = string.question_9, answers = listOf("False", "True")),
            Question(text = string.question_10, answers = listOf("False", "True")),
            Question(text = string.question_11, answers = listOf("False", "True")),
            Question(text = string.question_12, answers = listOf("True", "False")),
            Question(text = string.question_13, answers = listOf("False", "True")),
            Question(text = string.question_14, answers = listOf("True", "False")),
            Question(text = string.question_15, answers = listOf("False", "True")),
            Question(text = string.question_16, answers = listOf("False", "True")),
            Question(text = string.question_17, answers = listOf("True", "False")),
            Question(text = string.question_18, answers = listOf("False", "True")),
            Question(text = string.question_19, answers = listOf("False", "True")),
            Question(text = string.question_20, answers = listOf("True", "False"))
    )


    lateinit var currentQuestion: Question
    lateinit var answers: MutableList<String>
    private var questionIndex = 0
    private val numQuestions = Math.min((questionBank.size + 1) / 2, 10)
    private var score: Int = 0;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentGameBinding>(
                inflater, layout.fragment_game, container, false)

        // Shuffles the questions and sets the question index to the first question.
        randomizeQuestions()

        // Bind this fragment class to the layout
        binding.game = this

        binding.textView4.text = getString(string.score, score)

        binding.firstAnswerRadioButton.setOnClickListener() @Suppress("UNUSED_ANONYMOUS_PARAMETER")
        { view: View ->
            val checkedId = binding.questionRadioGroup.checkedRadioButtonId
            // Do nothing if nothing is checked (id == -1)
            if (-1 != checkedId) {
                var answerIndex = 0
                when (checkedId) {
                    R.id.secondAnswerRadioButton -> answerIndex = 1
                }
                // The first answer in the original question is always the correct one, so if our
                // answer matches, we have the correct answer.
                if (answers[answerIndex] == currentQuestion.answers[0]) {
                    //questionIndex++
                    // Advance to the next question
                    if (questionIndex < numQuestions) {
                        currentQuestion = questionBank[questionIndex]
                        //setQuestion()
                        binding.invalidateAll()
                        binding.firstAnswerRadioButton.isEnabled = false
                        binding.secondAnswerRadioButton.isEnabled = false
                        binding.imageView.isVisible = true
                    } else {

                        view.findNavController()
                                .navigate(GameFragmentDirections.actionGameFragmentToGameOverFragment(score))
                    }
                } else {
                    binding.imageView2.isVisible = true
                    binding.firstAnswerRadioButton.isEnabled = false
                    binding.secondAnswerRadioButton.isEnabled = false
                }
            }

        }

        binding.secondAnswerRadioButton.setOnClickListener() @Suppress("UNUSED_ANONYMOUS_PARAMETER")
        { view: View ->
            val checkedId = binding.questionRadioGroup.checkedRadioButtonId
            // Do nothing if nothing is checked (id == -1)
            if (-1 != checkedId) {
                var answerIndex = 0
                when (checkedId) {
                    R.id.secondAnswerRadioButton -> answerIndex = 1
                }
                // The first answer in the original question is always the correct one, so if our
                // answer matches, we have the correct answer.
                if (answers[answerIndex] == currentQuestion.answers[0]) {
                    //questionIndex++
                    // Advance to the next question
                    if (questionIndex < numQuestions) {
                        currentQuestion = questionBank[questionIndex]
                        //setQuestion()
                        binding.invalidateAll()
                        binding.firstAnswerRadioButton.isEnabled = false
                        binding.secondAnswerRadioButton.isEnabled = false
                        binding.imageView.isVisible = true
                    } else {
                        view.findNavController()
                                .navigate(GameFragmentDirections.actionGameFragmentToGameOverFragment(score))
                    }
                } else {
                    binding.imageView2.isVisible = true
                    binding.firstAnswerRadioButton.isEnabled = false
                    binding.secondAnswerRadioButton.isEnabled = false
                }
            }

        }

        // Set the onClickListener for the submitButton
        binding.submitButton.setOnClickListener @Suppress("UNUSED_ANONYMOUS_PARAMETER")
        { view: View ->
            val checkedId = binding.questionRadioGroup.checkedRadioButtonId
            // Do nothing if nothing is checked (id == -1)
            if (-1 != checkedId) {
                var answerIndex = 0
                when (checkedId) {
                    R.id.secondAnswerRadioButton -> answerIndex = 1
                }
                binding.firstAnswerRadioButton.isEnabled = true
                binding.secondAnswerRadioButton.isEnabled = true
                binding.firstAnswerRadioButton.isChecked = false
                binding.secondAnswerRadioButton.isChecked = false
                binding.imageView.isVisible = false
                binding.imageView2.isVisible = false

                // The first answer in the original question is always the correct one, so if our
                // answer matches, we have the correct answer.
                if (answers[answerIndex] == currentQuestion.answers[0]) {
                    questionIndex++
                    score++
                    binding.textView4.text = getString(string.score, score)
                    // Advance to the next question
                    if (questionIndex < numQuestions) {
                        currentQuestion = questionBank[questionIndex]
                        binding.textView4.text = getString(string.score, score)
                        setQuestion()
                        binding.invalidateAll()
                    } else {
                        view.findNavController()
                                .navigate(GameFragmentDirections.actionGameFragmentToGameOverFragment(score))
                    }
                } else {
                    questionIndex++
                    binding.textView4.text = getString(string.score, score)
                    // Advance to the next question
                    if (questionIndex < numQuestions) {
                        currentQuestion = questionBank[questionIndex]
                        setQuestion()
                        binding.invalidateAll()
                    } else {
                        view.findNavController()
                                .navigate(GameFragmentDirections.actionGameFragmentToGameOverFragment(score))
                    }
                }
            }
        }

        binding.returnButton.setOnClickListener @Suppress("UNUSED_ANONYMOUS_PARAMETER")
        { view: View ->

        }

        return binding.root
    }

    // randomize the questions and set the first question
    private fun randomizeQuestions() {
        questionBank.shuffle()
        questionIndex = 0
        setQuestion()
    }

    // Sets the question and randomizes the answers.  This only changes the data, not the UI.
    // Calling invalidateAll on the FragmentGameBinding updates the data.
    private fun setQuestion() {
        currentQuestion = questionBank[questionIndex]
        // randomize the answers into a copy of the array
        answers = currentQuestion.answers.toMutableList()
        // and shuffle them
        answers.shuffle()
        (activity as AppCompatActivity).supportActionBar?.title = getString(string.title_android_trivia_question, questionIndex + 1, numQuestions)
    }
}
