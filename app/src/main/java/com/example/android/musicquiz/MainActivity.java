package com.example.android.musicquiz;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int score = 0;
    boolean checkAllQuestionsAnswered = true;

    // Define global variables
    // Question 1
    RadioGroup question1;
    RadioButton correctAnswer1;

    // Question 2
    EditText userAnswer2;
    String correctAnswer2;

    // Question 3
    RadioGroup question3;
    RadioButton correctAnswer3;

    // Question 4
    RadioGroup question4;
    RadioButton correctAnswer4;

    // Question 5
    EditText userAnswer5;
    String correctAnswer5;

    // Question 6
    RadioGroup question6;
    RadioButton correctAnswer6;

    // Question 7
    RadioGroup question7;
    RadioButton correctAnswer7;

    // Question 8
    EditText userAnswer8;
    String correctAnswer8;

    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;

    /**
     * global variable OnCompletionListener
     */
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };

    /**
     * global variable OnAudioFocusChangeListener
     */
    private AudioManager.OnAudioFocusChangeListener afChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                releaseMediaPlayer();
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                mMediaPlayer.start();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        // Question 1
        question1 = (RadioGroup) findViewById(R.id.question1);
        correctAnswer1 = (RadioButton) findViewById(R.id.rumors);

        // Question 2
        userAnswer2 = ((EditText) findViewById(R.id.question2));
        correctAnswer2 = "jimi hendrix";

        // Question 3
        question3 = (RadioGroup) findViewById(R.id.question3);
        correctAnswer3 = (RadioButton) findViewById(R.id.year_1967);

        // Question 4
        question4 = (RadioGroup) findViewById(R.id.question4);
        correctAnswer4 = (RadioButton) findViewById(R.id.texas);

        // Question 5
        userAnswer5 = ((EditText) findViewById(R.id.question5));
        correctAnswer5 = "stairway to heaven";

        // Question 6
        question6 = (RadioGroup) findViewById(R.id.question6);
        correctAnswer6 = (RadioButton) findViewById(R.id.syd_barrett);

        // Question 7
        question7 = (RadioGroup) findViewById(R.id.question7);
        correctAnswer7 = (RadioButton) findViewById(R.id.ronnie_james_dio);

        // Question 8
        userAnswer8 = ((EditText) findViewById(R.id.question8));
        correctAnswer8 = "echoes";

        // Find the Question 8 view
        View question8View = (View) findViewById(R.id.question_8_song_player);

        // Create and setup the {@ AudioManager} to request audio focus
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        // Attach an OnClickListener
        question8View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // request audio focus to play the audio file
                int result = mAudioManager.requestAudioFocus(afChangeListener, AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // We have audio focus now.

                    // Create and setup the {@link MediaPlayer} for the audio resource associated
                    // with the current word
                    mMediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.echoes_clip);

                    // Start the audio file
                    mMediaPlayer.start();

                    // Setup a listener on the media player, so that we can stop and release the
                    // media player once the sound has finished playing.
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });
    }

    /**
     * The method called when the submit answers button is clicked.
     */
    public void submitAnswers(View view) {

        // Check the first question
        checkRadioGroupQuestion(question1, correctAnswer1);

        // Check the second question
        checkEditTextQuestion(userAnswer2, correctAnswer2);

        // Check the third question
        checkRadioGroupQuestion(question3, correctAnswer3);

        // Check the fourth question
        checkRadioGroupQuestion(question4, correctAnswer4);

        // Check the fifth question
        checkEditTextQuestion(userAnswer5, correctAnswer5);

        // Check the sixth question
        checkRadioGroupQuestion(question6, correctAnswer6);

        // Check the seventh question
        checkRadioGroupQuestion(question7, correctAnswer7);

        // Check the eighth question
        checkEditTextQuestion(userAnswer8, correctAnswer8);

        if (checkAllQuestionsAnswered) {
            // Display user's final score only if all questions were answered.
            String finalScoreText = "Congratulations, you've finished! Your final score was: " + score
                    + "/8";
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
    private void checkEditTextQuestion(EditText userAnswer, String correctAnswer) {
        String userAnswerText = userAnswer.getText().toString();
        Log.v("MainActivity.java", userAnswerText + " " + correctAnswer);
        if (userAnswerText.isEmpty()) {
            checkAllQuestionsAnswered = false;
        } else if (userAnswerText.toLowerCase().equals(correctAnswer)) {
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

        EditText question8 = (EditText) findViewById(R.id.question8);
        question8.setText(null);
    }

    /**
     * Release the mediaplayer if the user leaves the app.
     */
    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;

            // Abandon focus once the media player has been released.
            mAudioManager.abandonAudioFocus(afChangeListener);
        }
    }

}
