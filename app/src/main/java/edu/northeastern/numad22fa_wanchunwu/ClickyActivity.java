package edu.northeastern.numad22fa_wanchunwu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ClickyActivity extends AppCompatActivity {

    Button A;
    Button B;
    Button C;
    Button D;
    Button E;
    Button F;

    String pressedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicky);

        A = findViewById(R.id.A);
        B = findViewById(R.id.B);
        C = findViewById(R.id.C);
        D = findViewById(R.id.D);
        E = findViewById(R.id.E);
        F = findViewById(R.id.F);

        if(savedInstanceState!=null){
            pressedText = savedInstanceState.getString("pressedText");
        }
        if( pressedText != null) {
            setPressedValue(pressedText);
        }
    }

    // OnClicks for each button
    public void onClickA(View view) {
        pressedText = "A";
        setPressedValue(pressedText);
    }

    public void onClickB(View view) {
        pressedText = "B";
        setPressedValue(pressedText);
    }

    public void onClickC(View view) {
        pressedText = "C";
        setPressedValue(pressedText);
    }

    public void onClickD(View view) {
        pressedText = "D";
        setPressedValue(pressedText);
    }

    public void onClickE(View view) {
        pressedText = "E";
        setPressedValue(pressedText);
    }

    public void onClickF(View view) {
        pressedText = "F";
        setPressedValue(pressedText);
    }

    // Set Pressed: text
    private void setPressedValue(String val) {
        TextView tv = (TextView) findViewById(R.id.pressedButtonName);
        tv.setText("Pressed: " + val);
    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("pressedText",pressedText);
    }
}



