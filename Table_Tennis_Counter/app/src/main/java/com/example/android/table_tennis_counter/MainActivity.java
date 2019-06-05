package com.example.android.table_tennis_counter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int scorePlayerA = 0, scorePlayerB = 0, setScoreA = 0, setScoreB = 0;
    boolean isPlayerAServing = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * onClick methods called when someone scores a point
     * @param view
     */

    public void addPointForPlayerA(View view) {
        scorePlayerA++;
        updatePointsA();
        checkWhoIsServing();
    }

    public void addPointForPlayerB(View view) {
        scorePlayerB++;
        updatePointsB();
        checkWhoIsServing();
    }

    /**
     * Showing current score on screen
     */

    public void updatePointsA() {
        TextView textView = findViewById(R.id.player_a_score);
        textView.setText(String.valueOf(scorePlayerA));
    }

    public void updatePointsB() {
        TextView textView = findViewById(R.id.player_b_score);
        textView.setText(String.valueOf(scorePlayerB));
    }

    /**
     * onClick methods called when we want to add set won to either side
     * @param view
     */

    public void addSetForPlayerA(View view) {
        setScoreA++;
        updateSetsA();
    }

    public void addSetForPlayerB(View view) {
        setScoreB++;
        updateSetsB();
    }

    /**
     * calculates who is serving based on how many points were scored
     */

    public void checkWhoIsServing() {
        int sumOfScores = scorePlayerA + scorePlayerB;
        if (sumOfScores % 2 == 0) {
            isPlayerAServing = !isPlayerAServing;
            updateBall();
        }
    }

    /**
     * Updates ball that indicates whose set is it
     */

    public void updateBall() {
        ImageView imageView = findViewById(R.id.ball_A);
        if (isPlayerAServing) {
            imageView.setVisibility(View.VISIBLE);
            imageView = findViewById(R.id.ball_B);
            imageView.setVisibility(View.INVISIBLE);
        } else {
            imageView.setVisibility(View.INVISIBLE);
            imageView = findViewById(R.id.ball_B);
            imageView.setVisibility(View.VISIBLE);
        }

    }

    public void resetScore(View view) {
        scorePlayerA = scorePlayerB = 0;
        updatePointsB();
        updatePointsA();
        isPlayerAServing = (setScoreA + setScoreB) % 2 == 0;
        updateBall();
    }

    public void resetAll(View view) {
        scorePlayerA = scorePlayerB = 0;
        setScoreA = setScoreB = 0;
        updatePointsA();
        updatePointsB();
        updateSetsA();
        updateSetsB();
        isPlayerAServing = true;
    }

    private void updateSetsA() {
        ((TextView) findViewById(R.id.player_a_set_score)).setText(String.valueOf(setScoreA));
    }

    private void updateSetsB() {
        ((TextView) findViewById(R.id.player_b_set_score)).setText(String.valueOf(setScoreB));
    }
}
