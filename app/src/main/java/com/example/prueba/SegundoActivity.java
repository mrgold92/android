package com.example.prueba;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SegundoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segundo);

        TextView resultado = (TextView) findViewById(R.id.textResultado);

        resultado.setText(getIntent().getStringExtra("total"));

    }
}
