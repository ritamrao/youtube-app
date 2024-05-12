package com.example.youtube_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {

    EditText urlEditText;
    Button playButton, addToPlaylistButton, myPlaylistButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        urlEditText = findViewById(R.id.url_editText);
        playButton = findViewById(R.id.play_button);
        addToPlaylistButton = findViewById(R.id.add_to_playlist_button);
        myPlaylistButton = findViewById(R.id.my_playlist_button);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String videoUrl = urlEditText.getText().toString().trim();
                if (!videoUrl.isEmpty()) {
                    Intent intent = new Intent(Home.this, WebViewActivity.class);
                    intent.putExtra("url", videoUrl);
                    startActivity(intent);
                } else {
                    Toast.makeText(Home.this, "Please enter a YouTube URL", Toast.LENGTH_SHORT).show();
                }
            }
        });

        addToPlaylistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String videoUrl = urlEditText.getText().toString().trim();
                if (!videoUrl.isEmpty()) {
                    DatabaseHelper dbHelper = new DatabaseHelper(Home.this);
                    dbHelper.insertToPlaylist(videoUrl);
                    Toast.makeText(Home.this, "URL added to playlist", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(Home.this, "Please enter a YouTube URL", Toast.LENGTH_SHORT).show();
                }
            }
        });

        myPlaylistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, MyPlaylist.class));
            }
        });
    }
}

