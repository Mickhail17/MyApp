package mika.invul.myapp;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class MainLogic extends AppCompatActivity {
    public static final String EXTRA_SCORE = "extraScore";

    private static final String KEY_SCORE = "keyScore";
    private static final String KEY_QUESTION_COUNT = "keyQuestionCount";
    private static final String KEY_ANSWERED = "keyAnswered";
    private static final String KEY_QUESTION_LIST = "keyQuestionList";



    private TextView textViewQuestion;
    private TextView textViewScore;
    private TextView textViewQuestionCount;
    private TextView textViewExplanation;

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
        setContentView(R.layout.main_logic);
        toolbar5 = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar5);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        textViewQuestion = findViewById(R.id.text_view_question);
        textViewScore = findViewById(R.id.text_view_high_score5);
        textViewQuestionCount = findViewById(R.id.text_view_question_count);
        textViewExplanation = findViewById(R.id.explanation_text);
        textViewExplanation.setMovementMethod(new ScrollingMovementMethod());

        option1 = findViewById(R.id.button_1);
        option2 = findViewById(R.id.button_2);
        option3 = findViewById(R.id.button_3);
        confirm = findViewById(R.id.button_confirm);

        layout2 = (ViewGroup) findViewById(R.id.layout_2);

        textColorDefault = option1.getTextColors();

        Intent intent = getIntent();

        if (savedInstanceState == null) {
            QuizDbHelper dbHelper = new QuizDbHelper(this);
        }

        if (savedInstanceState == null) {
            QuizDbHelper dbHelper = new QuizDbHelper(this);
            switch (intent.getStringExtra("difficulty")) {
                case Question.DIFFICULTY_EASY:
                    questionList = dbHelper.getQuestions(1, "Easy");

                    questionCountTotal = questionList.size();
                    Collections.shuffle(questionList);
                    showNextQuestion();
                    break;
                case Question.DIFFICULTY_MEDIUM:
                    questionList = dbHelper.getQuestions(1, "Medium");

                    questionCountTotal = questionList.size();
                    Collections.shuffle(questionList);
                    showNextQuestion();
                    break;
                case Question.DIFFICULTY_HARD:
                    questionList = dbHelper.getQuestions(1, "Hard");

                    questionCountTotal = questionList.size();
                    Collections.shuffle(questionList);
                    showNextQuestion();
                    break;
                case Question.DIFFICULTY_VERB_GET:
                    questionList = dbHelper.getQuestions(1, "Verb_get");

                    questionCountTotal = questionList.size();
                    Collections.shuffle(questionList);
                    showNextQuestion();
                    break;
                case Question.DIFFICULTY_VERB_MAKE:
                    questionList = dbHelper.getQuestions(1, "Verb_make");

                    questionCountTotal = questionList.size();
                    Collections.shuffle(questionList);
                    showNextQuestion();
                    break;
                case Question.DIFFICULTY_VERB_TAKE:
                    questionList = dbHelper.getQuestions(1, "Verb_take");

                    questionCountTotal = questionList.size();
                    Collections.shuffle(questionList);
                    showNextQuestion();
                    break;
            }


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
                    Toast.makeText(MainLogic.this, "Please, select an answer", Toast.LENGTH_SHORT).show();
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

            textViewExplanation.setText("");

        } else {
            finishQuiz();
        }
    }

    public void optionButtonIsPressed(View view) {
        answered = true;


        int rightAnswer = view.getId();

        switch (view.getId()) {
            case (R.id.button_1):
            case (R.id.button_2):
            case (R.id.button_3):
                if (rightAnswer == currentQuestion.getAnswerNr()){
                    score++;
                    textViewScore.setText("Score: " + score);
                }
                showSolution();
                break;
        }
    }



    private void startAnimation() {
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setDuration(1000);

        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setStartOffset(1000);
        fadeOut.setDuration(1000);

        AnimationSet animation = new AnimationSet(false);
        animation.addAnimation(fadeIn);
        animation.addAnimation(fadeOut);
    }


/*    public void checkAnswer(View view) {
        answered = true;

        int answerNumber = layout2.indexOfChild(findViewById(R.id.layout_2)) + 1;

        if (answerNumber == currentQuestion.getAnswerNr()) {
            score++;
            textViewScore.setText("Score: " + score);
        }
    }*/

    public void showSolution () {
        option1.setTextColor(Color.RED);
        option2.setTextColor(Color.RED);
        option3.setTextColor(Color.RED);

        switch (currentQuestion.getAnswerNr()) {
            case 1:
                option1.setTextColor(Color.GREEN);
                textViewQuestion.setText("Answer 1 is correct");
                if (answered) {
                    startAnimation();
                    textViewExplanation.setText(currentQuestion.getExplanation());
                }
                break;
            case 2:
                option2.setTextColor(Color.GREEN);
                textViewQuestion.setText("Answer 2 is correct");
                if (answered) {
                    startAnimation();
                    textViewExplanation.setText(currentQuestion.getExplanation());
                }
                break;
            case 3:
                option3.setTextColor(Color.GREEN);
                textViewQuestion.setText("Answer 3 is correct");
                if (answered) {
                    startAnimation();
                    textViewExplanation.setText(currentQuestion.getExplanation());
                }
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
