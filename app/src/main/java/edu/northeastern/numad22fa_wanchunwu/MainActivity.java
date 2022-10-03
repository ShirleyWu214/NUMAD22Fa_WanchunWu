package edu.northeastern.numad22fa_wanchunwu;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void viewAbout(View view) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);


    }

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
        }

    }

}