package edu.northeastern.numad22fa_wanchunwu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    button = findViewById(R.id.button);

    button.setOnClickListener(view -> Toast.makeText(getApplicationContext(),
            "Wanchun Wu: wu.wan@northeastern.edu", Toast.LENGTH_LONG).show());
    }
}