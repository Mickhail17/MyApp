package mika.invul.myapp;

import android.provider.BaseColumns;

public final class QuizContract {
    private QuizContract() {
    }

    public static class topicTable implements BaseColumns {
        public static final String TABLE_NAME_TOPIC = "quiz_topic";
        public static final String COLUMN_NAME = "name";
    }

    public static class QuestionsTable implements BaseColumns {
        public static final String TABLE_NAME = "quiz_questions";
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_OPTION1 = "option1";
        public static final String COLUMN_OPTION2 = "option2";
        public static final String COLUMN_OPTION3 = "option3";
        public static final String COLUMN_ANSWER_NR = "answer_nr";
        public static final String COLUMN_DIFFICULTY = "difficulty";
        public static final String COLUMN_TOPIC_ID = "topic_id";
        public static final String COLUMN_EXPLANATION = "explanation";
    }
}
