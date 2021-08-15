package mika.invul.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {



    private Button button_intermediate;
    private Button button_upper_intermediate;
    private Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        button_intermediate = (Button) findViewById(R.id.button_intermediate);
        button_intermediate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityPhrasalVerbs();
            }
        });

        button_upper_intermediate = (Button) findViewById(R.id.button_upper_intermediate);
        button_upper_intermediate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityIdioms();
            }
        });
    }
    public void openActivityPhrasalVerbs () {
        Intent intent = new Intent(this, PhrasalVerbs.class);
        startActivity(intent);
    }

    public void openActivityIdioms () {
        Intent intent = new Intent (this, Idioms.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.example_menu, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Toast.makeText(this, "Item 1 selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item2:
                Toast.makeText(this, "Item 2 selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item3:
                Toast.makeText(this, "Item 3 selected", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void optionButtonIsPressed(View view) { // зачем объявление метода в MainActivity?

    }
}