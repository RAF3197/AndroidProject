package com.raf.jedi.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button logButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        logButton = findViewById(R.id.LogButton);

        logButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        startMain();

    }

    private void startMain() {
        Intent i = new Intent(this,NavActivity.class);
        startActivity(i);
        finish();
    }

}
