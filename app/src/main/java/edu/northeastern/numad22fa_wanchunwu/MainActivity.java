package edu.northeastern.numad22fa_wanchunwu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1= findViewById(R.id.button1);

        button1.setOnClickListener(view -> Toast.makeText(getApplicationContext(),
                "Wanchun Wu: wu.wan@northeastern.edu", Toast.LENGTH_LONG).show());

        Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openClickyActivity();
            }
        });
    }
    public void openClickyActivity(){
        Intent intent = new Intent(this, ClickyActivity.class);
        startActivity(intent);
    }
}