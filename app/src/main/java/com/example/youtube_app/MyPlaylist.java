package com.example.youtube_app;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MyPlaylist extends AppCompatActivity {

    ListView listView;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        dbHelper = new DatabaseHelper(this);
        listView = findViewById(R.id.listView);

        ArrayList<String> playlist = new ArrayList<>();
        Cursor data = dbHelper.getPlaylist();

        if(data.getCount() == 0){
            // No data available in the playlist
            playlist.add("No videos in playlist.");
        } else {
            while(data.moveToNext()){
                playlist.add(data.getString(1)); // Assuming URL is stored in column index 1
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, playlist);
        listView.setAdapter(adapter);
    }
}
