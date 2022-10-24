package edu.northeastern.numad22fa_wanchunwu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button findPrimesButton = findViewById(R.id.findPrimesMainID);
        findPrimesButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FindPrimesActivity.class);
            startActivity(intent);
        });

        button = findViewById(R.id.button);
        button.setOnClickListener(view -> Toast.makeText(getApplicationContext(),
                "Wanchun Wu: wu.wan@northeastern.edu", Toast.LENGTH_SHORT).show());
    }

    public void viewAbout(View view) {
        new AlertDialog.Builder(this);
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View view)
    {
        switch (view.getId()) {
            case R.id.clickybutton:
                Intent intent_clicky = new Intent(MainActivity.this, ClickyActivity.class);
                startActivity(intent_clicky);
                break;
            case R.id.linkcollectorbutton:
                Intent intent_linkcollect = new Intent(MainActivity.this, LinkCollectorActivity.class);
                startActivity(intent_linkcollect);
                break;
//            case R.id.findPrimesMainID:
//                Intent intent_primes = new Intent(MainActivity.this, FindPrimesActivity.class);
//                startActivity(intent_primes);
//                break;
            case R.id.locationID:
                Intent intent_location = new Intent(MainActivity.this, LocationActivity.class);
                startActivity(intent_location);
                break;
        }

    }

}