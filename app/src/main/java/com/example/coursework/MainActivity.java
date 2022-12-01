package com.example.coursework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private EditText mEnterMovieResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonParse = findViewById(R.id.button_parse);

        mEnterMovieResult = (EditText)findViewById(R.id.enterMovie);

        buttonParse.setOnClickListener(new View.OnClickListener() {
            // STOP CONTROL Z HERE PLS
            @Override
            public void onClick(View view) {
                openMovieRec();
            }
        });
    }

    public void openMovieRec() {
        Log.i("====== DEBUG ", "LOG 1 - BUTTON LISTENER ======");
        Intent openMovieRec = new Intent(this, MovieRecPage.class);
        openMovieRec.putExtra("user_input", mEnterMovieResult.getText().toString());
        startActivity(openMovieRec);
    }
}