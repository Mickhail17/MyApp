package mika.invul.myapp;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.security.cert.Extension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class Activity5 extends AppCompatActivity {
    public static final String EXTRA_SCORE = "extraScore";

    private static final String KEY_SCORE = "keyScore";
    private static final String KEY_QUESTION_COUNT = "keyQuestionCount";
    private static final String KEY_ANSWERED = "keyAnswered";
    private static final String KEY_QUESTION_LIST = "keyQuestionList";



    private TextView textViewQuestion;
    private TextView textViewScore;
    private TextView textViewQuestionCount;

    private ViewGroup layout2;

    private ColorStateList textColorDefault;

    private Button option1;
    private Button option2;
    private Button option3;
    private Button confirm;

    private ArrayList<Question> questionList;
    private int questionCounter;
    private int questionCountTotal;
    private Question currentQuestion;

    private int score = 0;
    private boolean answered;

    private long backPressedTime;
    private Toolbar toolbar5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_5);
        toolbar5 = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar5);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        textViewQuestion = findViewById(R.id.text_view_question);
        textViewScore = findViewById(R.id.text_view_high_score5);
        textViewQuestionCount = findViewById(R.id.text_view_question_count);

        option1 = findViewById(R.id.button_1);
        option2 = findViewById(R.id.button_2);
        option3 = findViewById(R.id.button_3);
        confirm = findViewById(R.id.button_confirm);

        layout2 = (ViewGroup) findViewById(R.id.layout_2);

        textColorDefault = option1.getTextColors();

        if (savedInstanceState == null) {
            QuizDbHelper dbHelper = new QuizDbHelper(this);
            questionList = dbHelper.getQuestions("Hard");

            questionCountTotal = questionList.size();
            Collections.shuffle(questionList);

            showNextQuestion();
        } else {
            questionList = savedInstanceState.getParcelableArrayList(KEY_QUESTION_LIST);
            questionCountTotal = questionList.size();
            questionCounter = savedInstanceState.getInt(KEY_QUESTION_COUNT);
            currentQuestion = questionList.get(questionCounter - 1);
            score = savedInstanceState.getInt(KEY_SCORE);
            answered = savedInstanceState.getBoolean(KEY_ANSWERED);
        }
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!answered) {
                    Toast.makeText(Activity5.this, "Please, select an answer", Toast.LENGTH_SHORT).show();
                } else {
                    showNextQuestion();
                }
            }
        });




        /*View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.equals(option1)) {
                    showSolution();
                } else {
                    showSolution();
                }
            }
        };

        option1.setOnClickListener(listener);
        option2.setOnClickListener(listener);
        option3.setOnClickListener(listener);*/
    }


    private void showNextQuestion() {
        option1.setTextColor(textColorDefault);
        option2.setTextColor(textColorDefault);
        option3.setTextColor(textColorDefault);

        if (questionCounter < questionCountTotal) {
            currentQuestion = questionList.get(questionCounter);

            textViewQuestion.setText(currentQuestion.getQuestion());
            option1.setText(currentQuestion.getOption1());
            option2.setText(currentQuestion.getOption2());
            option3.setText(currentQuestion.getOption3());

            questionCounter++;
            textViewQuestionCount.setText("Question: " + questionCounter + "/" + questionCountTotal);
            answered = false;
            confirm.setText("Next");

        } else {
            finishQuiz();
        }
    }

    public void optionButtonIsPressed(View view) {
        answered = true;

        switch (view.getId()) {
            case (R.id.button_1):
                showSolution();
                break;
            case (R.id.button_2):
                showSolution();
                break;
            case (R.id.button_3):
                showSolution();
                break;


        }
    }




    public void checkAnswer(ViewGroup view) {
        answered = true;

        int answerNumber = layout2.indexOfChild(findViewById(R.id.layout_2)) + 1;

        if (answerNumber == currentQuestion.getAnswerNr()) {
            score++;
            textViewScore.setText("Score: " + score);
        }
    }

    public void showSolution () {
        option1.setTextColor(Color.RED);
        option2.setTextColor(Color.RED);
        option3.setTextColor(Color.RED);

        switch (currentQuestion.getAnswerNr()) {
            case 1:
                option1.setTextColor(Color.GREEN);
                textViewQuestion.setText("Answer 1 is correct");
                score++;
                break;
            case 2:
                option2.setTextColor(Color.GREEN);
                textViewQuestion.setText("Answer 2 is correct");
                score++;
                break;
            case 3:
                option3.setTextColor(Color.GREEN);
                textViewQuestion.setText("Answer 3 is correct");
                score++;
                break;
        }
        if (questionCounter < questionCountTotal) {
            confirm.setText("Next");
        }else {
            confirm.setText("Finish");
        }

    }


    private void startNewActivity () {
        Intent intention = new Intent();

    }

    private void finishQuiz () {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(EXTRA_SCORE, score);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_SCORE, score);
        outState.putInt(KEY_QUESTION_COUNT, questionCounter);
        outState.putBoolean(KEY_ANSWERED, answered);
        outState.putParcelableArrayList(KEY_QUESTION_LIST, questionList);
    }
}
