package mika.invul.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import java.util.Objects;


public class PhrasalVerbs extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_CODE_QUIZ = 1;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String KEY_HIGHSCORE = "keyHighscore";

    private TextView textViewHighScore;
    private int highscore;

    private Button button1;
    private Button button2;
    private Button button3;
    private Toolbar toolbar2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        toolbar2 = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar2);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        button1 = findViewById(R.id.button_practice_words);
        button2 = findViewById(R.id.button_irregular_verbs);
        button3 = findViewById(R.id.button_test);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        String difficultyEasy = String.valueOf(button1);
        String difficultyMedium = String.valueOf(button2);
        String difficultyHard = String.valueOf(button3);
        Intent intent = new Intent(PhrasalVerbs.this, MainLogic.class);

        switch(v.getId()) {
            case R.id.button_practice_words:
                intent.putExtra("difficulty", Question.DIFFICULTY_VERB_GET);
                startActivity(intent);
                break;
            case R.id.button_irregular_verbs:
                intent.putExtra("difficulty", Question.DIFFICULTY_VERB_MAKE);
                startActivity(intent);
                break;
            case R.id.button_test:
                intent.putExtra("difficulty", Question.DIFFICULTY_VERB_TAKE);
                startActivity(intent);
                break;
        }
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