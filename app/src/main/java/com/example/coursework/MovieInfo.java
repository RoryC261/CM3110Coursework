package com.example.coursework;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.text.TextRunShaper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class MovieInfo extends AppCompatActivity {

    private TextView mtext_view_movie_name;
    private TextView mtext_view_movie_description;
    private WebView mWebView;
    private Button buttonBackToFavourites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);

        mtext_view_movie_name = findViewById(R.id.text_view_movie_name);
        mtext_view_movie_description = findViewById(R.id.text_view_movie_description);
        mWebView = (WebView) findViewById(R.id.videoview);
        buttonBackToFavourites = findViewById(R.id.button_back_to_favourites);

        buttonBackToFavourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFavourites();
            }
        });

        Intent intent = getIntent();
        String movieName = intent.getStringExtra("MOVIE_NAME");
        String movieDescription = intent.getStringExtra("MOVIE_DESCRIPTION");
        String movieURl = intent.getStringExtra("MOVIE_URL");
        Log.d("DEBUG", movieName);
        Log.d("DEBUG", movieDescription);
        Log.d("DEBUG", movieURl);

        mtext_view_movie_name.setText(movieName);
        mtext_view_movie_description.setText(movieDescription);

        String videoStr = "<html><body>Promo video<br><iframe width=\"420\" height=\"315\" src=\"" + movieURl + "\" frameborder=\"0\" allowfullscreen></iframe></body></html>";
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        WebSettings ws = mWebView.getSettings();
        ((WebSettings) ws).setJavaScriptEnabled(true);
        mWebView.loadData(videoStr, "text/html", "utf-8");

    }

    public void openFavourites(){
        Intent openFav = new Intent(this, MovieFavourites.class);
        startActivity(openFav);
    }
}