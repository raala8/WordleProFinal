package com.example.wordleprofinal

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {
    var counter = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val wordToGuess = FourLetterWordList.getRandomFourLetterWord()
        val guessesField = findViewById<TextView>(R.id.guesses_view)    //
        val editText = findViewById<EditText>(R.id.four_letter)         // where the user guesses
        val showAnswer = findViewById<TextView>(R.id.dev_answer_show)   // in demo gif this is open but in trial closed
        val failView = findViewById<TextView>(R.id.info_view)           // shows info of status
        val debugView = findViewById<TextView>(R.id.guess_wrd)          // shows guess word


        val button = findViewById<Button>(R.id.button)
        var guessLimit = 3                                                  // set number of attempts
        var checkGuessDisplay = ""                                          // used to display check results
        var userInputDisplay = ""                                           // used to display user input

        // button logic
        button.setOnClickListener {
            // reset
            if (guessLimit == 0){
                finish()
                overridePendingTransition(0, 0);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
            // Toast.makeText(it.context, "Clicked Button!", Toast.LENGTH_SHORT).show()
            counter++
            if (counter >= 3) {
                Toast.makeText(
                    it.context,
                    "You have exceeded the number of tries!",
                    Toast.LENGTH_SHORT
                ).show()
            }

            // guesses remaining check
            if (guessLimit != 0) {
//                    showAnswer.setTextColor(Color.parseColor("#D3D3D3"));
//                    showAnswer.text = "Ans:   $wordToGuess"
                var isCorrect = false       // correct guess flag
                val editTextString = editText.text.toString().uppercase(Locale.getDefault());   // to string for edit text
                if (editTextString.length == 4) {
                    failView.text = ""
                    val result = checkGuess(editTextString, wordToGuess)
                    checkGuess(editTextString, wordToGuess)

                    // display conditional
                    // first guess
                    if (checkGuessDisplay == "") {
                        checkGuessDisplay = result
                        userInputDisplay = editTextString
                    }
                    // following guesses
                    else {
                        checkGuessDisplay = checkGuessDisplay + "\n" + result
                        userInputDisplay = userInputDisplay + "\n" + editTextString
                    }

                    guessesField.text = checkGuessDisplay
                    debugView.text = userInputDisplay

                    // on win conditional
                    if (result == "OOOO") {
                        failView.text = "Correct! \nAnswer: $wordToGuess"
                        button.text = "Reset"
                        isCorrect = true
                        guessLimit = 0
                    } else {
                        guessLimit--

                        // on lost conditional
                        if (guessLimit == 0 && !isCorrect) {
                            failView.text = "You're Out of Attempts!\nAnswer: $wordToGuess"
                            button.text = "Reset"
                        }
                    }
                }
                // invalid input conditional
                else
                    failView.text = "Oops, Gotta enter a Four Letter Word!"
            }
        }

    }
    // method for checking guess
    private fun checkGuess(guess: String, wordToGuess: String) : String {
        var result = ""
        for (i in 0..3) {
            if (guess[i] == wordToGuess[i]) {
                result += "O"
            }
            else if (guess[i] in wordToGuess) {
                result += "+"
            }
            else {
                result += "X"
            }
        }
        return result
    }

}