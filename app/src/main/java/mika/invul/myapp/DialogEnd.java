/*package mika.invul.myapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class DialogEnd extends AppCompatDialogFragment {

    public static final int REQUEST_CODE_RESULT = 1;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String KEY_HIGHSCORE = "keyHighscore";


    private TextView textViewHighScore;
    private int highScore;

    public static DialogEnd newInstance(int numberScore) {
        DialogEnd dialog = new DialogEnd();
        Bundle bundle = new Bundle();
        bundle.putInt("numberScore", numberScore);

        return dialog;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Your Result");
        builder.setMessage("Score: " + getArguments().getInt("numberScore"))
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return builder.create();
    }
}*/
