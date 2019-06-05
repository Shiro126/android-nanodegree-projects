package com.example.android.short_quiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private int score;
    private boolean pictureClicked = false;

    public void addPoint() {

        score++;
    }

    /**
     * shows score using toast, prepares values for new run
     */

    public void showScore() {
        if(score == 4) {
            Toast toast = Toast.makeText(getApplicationContext(), "Great! You got it all correct! Your score is..." + String.valueOf(score), Toast.LENGTH_LONG);
            toast.show();
        }
        else {
            Toast toast = Toast.makeText(getApplicationContext(), "Congratulations, Your score is..." + String.valueOf(score), Toast.LENGTH_LONG);
            toast.show();
        }

        score=0;
        pictureClicked=false;
    }

    /**
     * counts points, checks answers
     * @param view
     */

    public void submitAnswers(View view) {

        CheckBox checkBox = findViewById(R.id.ans_1_1);
        CheckBox checkBox1 = findViewById(R.id.ans_1_3);
        CheckBox checkBox2 = findViewById(R.id.ans_1_4);
        CheckBox checkBox3 = findViewById(R.id.ans_1_2);
        if ( checkBox.isChecked() && checkBox1.isChecked() && checkBox2.isChecked() && !checkBox3.isChecked() )
            addPoint();

        EditText editText = findViewById(R.id.text_ans);
        if(editText.getText().toString().trim().equals("9"))
            addPoint();

        RadioButton radioButton = findViewById(R.id.ans_3);
        if(radioButton.isChecked())
            addPoint();


        //Ans 4

        showScore();
    }

    /**
     * both methods manage 4th question, by adding points and making it impossible to click picture twice
     * @param view
     */
    public void badPicture(View view) {
        pictureClicked=true;
    }

    public void goodPicture(View view){
        if ( !pictureClicked )
            addPoint();
        pictureClicked = true;
    }
}
