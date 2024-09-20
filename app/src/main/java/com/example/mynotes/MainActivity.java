package com.example.mynotes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.mynotes.Adapter.NotesListAdapter;
import com.example.mynotes.DataBase.RoomDB;
import com.example.mynotes.Models.Notes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton fab_add;
    NotesListAdapter notesListAdapter;
    RoomDB database;
    List<Notes> notes = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_home);
        fab_add = findViewById(R.id.fab_add);


        database = RoomDB.getInstance(this);
        notes = database.mainDao().getALL();

        updateRecycle(notes);

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NotesTakerActivity.class);
                startActivityForResult(intent, 101);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                Notes new_notes = (Notes) data.getSerializableExtra("note");
                if (new_notes != null) {
                    database.mainDao().insert(new_notes);
                    notes.clear();
                    notes.addAll(database.mainDao().getALL());
                    notesListAdapter.notifyDataSetChanged();
                }
            }
        }
    }


    private void updateRecycle(List<Notes> notes) {

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));

        notesListAdapter = new NotesListAdapter(MainActivity.this, notes, NotesClickListener);
        recyclerView.setAdapter(notesListAdapter);


    }

    private final NotesClickListener NotesClickListener = new NotesClickListener() {

        @Override
        public void onClick(Notes notes) {

        }

        @Override
        public void onLongClick(Notes notes, CardView cardView) {


        }
    };
}