package mika.invul.myapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import java.util.Objects;


public class Activity2 extends AppCompatActivity {

    private static final int REQUEST_CODE_QUIZ = 1;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String KEY_HIGHSCORE = "keyHighscore";

    private TextView textViewHighScore;
    private int highscore;

    private Button buttonPracticeWords;
    private Button buttonIrregularVerbs;
    private Button buttonTest;
    private Toolbar toolbar2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        toolbar2 = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar2);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        buttonPracticeWords = (Button) findViewById(R.id.button_practice_words);
        buttonPracticeWords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity3();
            }
        });
        buttonIrregularVerbs = (Button) findViewById(R.id.button_irregular_verbs);
        buttonIrregularVerbs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity4();
            }
        });

        buttonTest = (Button) findViewById(R.id.button_test);
        buttonTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity5();
            }
        });



    }



    public void openActivity3 (){
        Intent intent = new Intent(this, Activity3.class);
        startActivityForResult(intent, REQUEST_CODE_QUIZ);
    }

    public void openActivity4 (){
        Intent kek = new Intent (this, Activity4.class);
        startActivityForResult(kek, REQUEST_CODE_QUIZ);
    }

    public void openActivity5 () {
        Intent bruh = new Intent(this, Activity5.class);
        startActivityForResult(bruh, REQUEST_CODE_QUIZ);
    }

/*    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_QUIZ) {
            if (resultCode == RESULT_OK) {
                int score = data.getIntExtra(Activity3.EXTRA_SCORE, 0);
                if (score > highscore) {
                    updateHighscore(score);
                }
            }
        }
    }*/

    private void loadHighscore() {
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        highscore = prefs.getInt(KEY_HIGHSCORE, 0);
        textViewHighScore.setText("Highscore: " + highscore);
    }

    private void updateHighscore (int highscoreNew) {
        highscore = highscoreNew;
        textViewHighScore.setText("Highscore: " + highscore);
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_HIGHSCORE, highscore);
        editor.apply();
    }
}