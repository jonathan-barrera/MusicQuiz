package com.example.android.musicquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int score = 0;
    boolean checkAllQuestionsAnswered = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * The method called when the submit answers button is clicked.
     */
    public void submitAnswers(View view) {

        // Check the first question
        RadioGroup question1 = (RadioGroup) findViewById(R.id.question1);
        RadioButton correctAnswer1 = (RadioButton) findViewById(R.id.rumors);

        checkRadioGroupQuestion(question1, correctAnswer1);

        // Check the second question
        String userAnswer2 = ((EditText) findViewById(R.id.question2)).getText().toString();
        String correctAnswer2 = "jimi hendrix";

        checkEditTextQuestion(userAnswer2, correctAnswer2);

        // Check the third question
        RadioGroup question3 = (RadioGroup) findViewById(R.id.question3);
        RadioButton correctAnswer3 = (RadioButton) findViewById(R.id.year_1967);

        checkRadioGroupQuestion(question3, correctAnswer3);

        // Check the fourth question
        RadioGroup question4 = (RadioGroup) findViewById(R.id.question4);
        RadioButton correctAnswer4 = (RadioButton) findViewById(R.id.texas);

        checkRadioGroupQuestion(question4, correctAnswer4);

        // Check the fifth question
        String userAnswer5 = ((EditText) findViewById(R.id.question5)).getText().toString();
        String correctAnswer5 = "stairway to heaven";

        checkEditTextQuestion(userAnswer5, correctAnswer5);

        // Check the sixth question
        RadioGroup question6 = (RadioGroup) findViewById(R.id.question6);
        RadioButton correctAnswer6 = (RadioButton) findViewById(R.id.syd_barrett);

        checkRadioGroupQuestion(question6, correctAnswer6);

        // Check the seventh question
        RadioGroup question7 = (RadioGroup) findViewById(R.id.question7);
        RadioButton correctAnswer7 = (RadioButton) findViewById(R.id.ronnie_james_dio);

        checkRadioGroupQuestion(question7, correctAnswer7);

        if (checkAllQuestionsAnswered) {
            // Display user's final score only if all questions were answered.
            String finalScoreText = "Congratulations, you've finished! Your final score was: " + score
                    + "/7";
            TextView finalScore = (TextView) findViewById(R.id.final_score);
            finalScore.setText(finalScoreText);
            finalScore.setVisibility(View.VISIBLE);

            // Hide "Submit Answers" button and show "Try Again?" button
            Button submitAnswers = (Button) findViewById(R.id.submit_answers);
            submitAnswers.setVisibility(View.GONE);

            Button tryAgain = (Button) findViewById(R.id.try_again);
            tryAgain.setVisibility(View.VISIBLE);
        } else {
            // Tell the user to finish the quiz if the answers are not all filled.
            Toast.makeText(getApplicationContext(), "Please answer all of the questions.",
                    Toast.LENGTH_LONG).show();

            // Reset score to zero.
            score = 0;

            // Reset boolean checkAllQuestionsAnswered
            checkAllQuestionsAnswered = true;
        }

    }

    /**
     * Check answers for radio group (multiple choice) type questions.
     *
     * @param question is the question
     * @param correctAnswer is the correct answer
     */
    private void checkRadioGroupQuestion(RadioGroup question, RadioButton correctAnswer) {
        int answerInt = question.getCheckedRadioButtonId();
        RadioButton userAnswer = (RadioButton) question.findViewById(answerInt);
        if (answerInt == -1) {
            checkAllQuestionsAnswered = false;
        } else if (userAnswer == correctAnswer) {
            score += 1;
        }
    }

    /**
     * Check answer for edit text (free response) style questions.
     *
     * @param userAnswer is the user's input.
     * @param correctAnswer is the correct answer.
     */
    private void checkEditTextQuestion(String userAnswer, String correctAnswer) {
        if (userAnswer.equals("")) {
            checkAllQuestionsAnswered = false;
        } else if (userAnswer.toLowerCase().equals(correctAnswer)) {
            score += 1;
        }
    }

    public void reset(View view) {
        // Set score back to zero.
        score = 0;

        // Hide "Try Again" button and "final score" message
        // Make "Submit Answers" button reappear
        TextView finalScore = (TextView) findViewById(R.id.final_score);
        finalScore.setVisibility(View.GONE);

        Button submitAnswers = (Button) findViewById(R.id.submit_answers);
        submitAnswers.setVisibility(View.VISIBLE);

        Button tryAgain = (Button) findViewById(R.id.try_again);
        tryAgain.setVisibility(View.GONE);

        // Reset RadioButton questions
        RadioGroup question1 = (RadioGroup) findViewById(R.id.question1);
        question1.clearCheck();

        RadioGroup question3 = (RadioGroup) findViewById(R.id.question3);
        question3.clearCheck();

        RadioGroup question4 = (RadioGroup) findViewById(R.id.question4);
        question4.clearCheck();

        RadioGroup question6 = (RadioGroup) findViewById(R.id.question6);
        question6.clearCheck();

        RadioGroup question7 = (RadioGroup) findViewById(R.id.question7);
        question7.clearCheck();

        // Reset EditText questions
        EditText question2 = (EditText) findViewById(R.id.question2);
        question2.setText(null);

        EditText question5 = (EditText) findViewById(R.id.question5);
        question5.setText(null);
    }


}
