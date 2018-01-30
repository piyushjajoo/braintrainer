package com.example.pjajoo.braintrainer;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button goButton;
    private List<Integer> answers = new ArrayList<>();
    private int correctAnswerLocation;
    private TextView scoreTextView;
    private TextView pointsTextView;
    private int totalAttempts;
    private int successAttempts;
    private CountDownTimer timer;
    private TextView timerTextView;
    private Button playAgain;
    private RelativeLayout gameRelativeLayout;

    public void playAgain(final View view) {
        this.successAttempts = 0;
        this.totalAttempts = 0;
        timerTextView.setText("30s");
        pointsTextView.setText("0/0");
        scoreTextView.setText("");
        playAgain.setVisibility(View.INVISIBLE);
        resetTheGame();

        new CountDownTimer(30100, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText(Integer.toString((int)millisUntilFinished/1000) + "s");
            }

            @Override
            public void onFinish() {
                timerTextView.setText("0s");
                scoreTextView.setBackgroundColor(Color.GRAY);
                scoreTextView.setText("Your final score is " + pointsTextView.getText().toString());
                playAgain.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    private void resetTheGame() {

        final TextView questionTextView = (TextView) findViewById(R.id.questionTextView);
        final Button choice1 = (Button) findViewById(R.id.choice1);
        final Button choice2 = (Button) findViewById(R.id.choice2);
        final Button choice3 = (Button) findViewById(R.id.choice3);
        final Button choice4 = (Button) findViewById(R.id.choice4);

        final Random random = new Random();

        final int a = random.nextInt(21);
        final int b = random.nextInt(21);

        questionTextView.setText(Integer.toString(a) + "+" + Integer.toString(b));

        correctAnswerLocation = random.nextInt(4);
        int incorrectAnswer;

        answers.clear();
        for (int i=0; i<4; i++) {
            if (i == correctAnswerLocation) {
                answers.add(a+b);
            } else {
                incorrectAnswer = random.nextInt(41);

                while (incorrectAnswer == a+b || answers.contains(incorrectAnswer)) {
                    incorrectAnswer = random.nextInt(41);
                }
                answers.add(incorrectAnswer);
            }
        }

        choice1.setText(Integer.toString(answers.get(0)));
        choice2.setText(Integer.toString(answers.get(1)));
        choice3.setText(Integer.toString(answers.get(2)));
        choice4.setText(Integer.toString(answers.get(3)));
    }

    public void chooseAnswer(final View view) {
        Log.i("Chosen answer", view.getTag().toString());
        if (view.getTag().toString().equals(Integer.toString(this.correctAnswerLocation))) {
            scoreTextView.setBackgroundColor(Color.GREEN);
            scoreTextView.setText("Correct");
            successAttempts++;
            totalAttempts++;
            pointsTextView.setText(Integer.toString(successAttempts)+"/"+Integer.toString(totalAttempts));
        } else {
            scoreTextView.setBackgroundColor(Color.RED);
            scoreTextView.setText("Incorrect");
            totalAttempts++;
            pointsTextView.setText(Integer.toString(successAttempts)+"/"+Integer.toString(totalAttempts));
        }
        resetTheGame();
    }

    public void startGame(final View view) {
        this.goButton.setVisibility(view.INVISIBLE);
        this.gameRelativeLayout.setVisibility(View.VISIBLE);
        playAgain(findViewById(R.id.playAgain));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.goButton = (Button) findViewById(R.id.go);
        this.scoreTextView = (TextView) findViewById(R.id.scoreTextView);
        this.pointsTextView = (TextView) findViewById(R.id.pointsTextView);
        this.timerTextView = (TextView) findViewById(R.id.timerTextView);
        this.playAgain = (Button) findViewById(R.id.playAgain);
        this.gameRelativeLayout = (RelativeLayout) findViewById(R.id.myRelLayout2);
    }
}
