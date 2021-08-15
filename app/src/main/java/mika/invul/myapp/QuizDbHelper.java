package mika.invul.myapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import mika.invul.myapp.QuizContract.*;

import java.util.ArrayList;
import java.util.List;

public class QuizDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "MyAwesomeQuiz.db";
    public static final int DATABASE_VERSION = 1;

    private  static QuizDbHelper instance;

    private SQLiteDatabase db;
    public QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized  QuizDbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new QuizDbHelper(context.getApplicationContext());

        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_DIFFICULTY_TABLE = "CREATE TABLE " +
                topicTable.TABLE_NAME_TOPIC + "(" +
                topicTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                topicTable.COLUMN_NAME + " TEXT " +
                ")";

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_NR + " INTEGER, " +
                QuestionsTable.COLUMN_DIFFICULTY + " TEXT, " +
                QuestionsTable.COLUMN_TOPIC_ID + " INTEGER, " +
                QuestionsTable.COLUMN_EXPLANATION + " TEXT, " +
                "FOREIGN KEY(" + QuestionsTable.COLUMN_TOPIC_ID + ") REFERENCES " +
                topicTable.TABLE_NAME_TOPIC + "(" + topicTable._ID + ")" + "ON DELETE CASCADE" +
                ")";
        db.execSQL(SQL_CREATE_DIFFICULTY_TABLE);
        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillDifficultiesTable();
        fillQuestionsTable();
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + topicTable.TABLE_NAME_TOPIC);
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    private void fillDifficultiesTable () {
        IdiomTopics c1 = new IdiomTopics("Easy");
        addDifficultyLevel(c1);
        IdiomTopics c2 = new IdiomTopics("Medium");
        addDifficultyLevel(c2);
        IdiomTopics c3 = new IdiomTopics("Hard");
        addDifficultyLevel(c3);

    }

    private void addDifficultyLevel (IdiomTopics idiomTopics) {
        ContentValues cv = new ContentValues();
        cv.put(topicTable.COLUMN_NAME, IdiomTopics.class.getName());
        db.insert(topicTable.TABLE_NAME_TOPIC, null, cv);
    }

    private void fillQuestionsTable() {
        Question q1 = new Question("Every cloud has a...",
                "Silver lining", "Head in the clouds", "Bolt from the blue", 1, "Easy", IdiomTopics.FOOD, "something important or unusual that happens suddenly or unexpectedly");
        addQuestion(q1);
        Question q2 = new Question ("In the blink of an...", "eye", "ear", "arm", 1, "Medium", IdiomTopics.FOOD,"if something happens or is done in the blink of an eye, it happens or is done very quickly");
        addQuestion(q2);
        Question q3 = new Question("Money makes...", "time", "money", "pizza", 2, "Hard", IdiomTopics.FOOD,"if you put your money in something profitable it will make more money");
        addQuestion(q3);
        Question q4 = new Question("Keep something under one’s...", "Skirt", "Pants", "Hat", 3, "Hard", IdiomTopics.FOOD,"to keep something in secret");
        addQuestion(q4);
        Question q5 = new Question("Storm in the...", "Teacup", "Bowl", "Spoon", 1, "Hard", IdiomTopics.FOOD,"a lot of unnecessary anger and worry about a matter that is not important");
        addQuestion(q5);
        Question q6 = new Question("Get along with", "Have a good relationship with someone", "Move in front of", "Walk or visit places", 1, "Verb_get", IdiomTopics.FOOD,"Get along with means to Have a good relationship with someone. \n \nI don't GET ALONG WITH my sister- we have nothing in common.");
        addQuestion(q6);
        Question q7 = new Question("Make off", "Be able to see or hear something", "Leave somewhere in a hurry", "Understand or have an opinion", 2, "Verb_make", IdiomTopics.FOOD,"Make off means to Leave somewhere in a hurry\n \nThey MADE OFF when they heard the police siren.");
        addQuestion(q7);
        Question q8 = new Question("Take over", "Make a habit of something", "Explain something to someone", "Assume control of a company or organisation", 3, "Verb_take", IdiomTopics.FOOD,"Take over means to Assume control of a company or organisation\n \nThe bank was TAKEN OVER by a Hong Kong bank that needed to buy a bank to get into the British market.");
        addQuestion(q8);
    }

    private void addQuestion(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        cv.put(QuestionsTable.COLUMN_DIFFICULTY, question.getDifficulty());
        cv.put(QuestionsTable.COLUMN_TOPIC_ID, question.getTopicID());
        cv.put(QuestionsTable.COLUMN_EXPLANATION, question.getExplanation());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }

    public List<IdiomTopics> getAllDifficulties() {
        List<IdiomTopics> difficultyList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + topicTable.TABLE_NAME_TOPIC, null);

        if (cursor.moveToFirst()) {
            do {
                IdiomTopics idiomTopics = new IdiomTopics();
                idiomTopics.setId(cursor.getInt(cursor.getColumnIndex(topicTable._ID)));
                idiomTopics.setName(cursor.getString(cursor.getColumnIndex(topicTable.COLUMN_NAME)));
                difficultyList.add(idiomTopics);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return difficultyList;
    }

    public ArrayList<Question> getAllQuestions() {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId(c.getInt(c.getColumnIndex(QuestionsTable._ID)));
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                question.setTopicID(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_TOPIC_ID)));
                question.setExplanation(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_EXPLANATION)));
                questionList.add(question);
            } while (c.moveToNext());
        }
        c.close();
        return questionList;
    }

    public ArrayList<Question> getQuestions(int topicID, String difficulty) {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();

        String selection = QuestionsTable.COLUMN_TOPIC_ID + " =? " +
                " AND " + QuestionsTable.COLUMN_DIFFICULTY + " =? ";
        String [] selectionArgs = new String [] {String.valueOf(topicID), difficulty};

        Cursor c = db.query(
                QuestionsTable.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId(c.getInt(c.getColumnIndex(QuestionsTable._ID)));
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                question.setTopicID(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_TOPIC_ID)));
                question.setExplanation(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_EXPLANATION)));
                questionList.add(question);
            } while (c.moveToNext());
        }
        c.close();
        return questionList;
    }
}
