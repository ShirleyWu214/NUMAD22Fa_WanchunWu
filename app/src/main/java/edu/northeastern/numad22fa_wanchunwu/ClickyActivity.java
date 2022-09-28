package edu.northeastern.numad22fa_wanchunwu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ClickyActivity extends AppCompatActivity implements View.OnClickListener {

    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicky);

        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);
        Button button6 = findViewById(R.id.button6);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
    }
    @Override
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.button1:
                Toast.makeText(this,"Pressed: A", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button2:
                Toast.makeText(this,"Pressed: B", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button3:
                Toast.makeText(this,"Pressed: C", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button4:
                Toast.makeText(this,"Pressed: D", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button5:
                Toast.makeText(this,"Pressed: E", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button6:
                Toast.makeText(this,"Pressed: F", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}


