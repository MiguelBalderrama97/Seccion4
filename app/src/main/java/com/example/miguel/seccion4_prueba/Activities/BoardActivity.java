package com.example.miguel.seccion4_prueba.Activities;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.miguel.seccion4_prueba.Models.Board;
import com.example.miguel.seccion4_prueba.R;

import io.realm.Realm;

public class BoardActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

//        DB Realm
        realm = Realm.getDefaultInstance();

        fab = findViewById(R.id.fabAddBoard);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertForCreatingBoard("Title", "Message");
            }
        });
    }

//    DIALOGS
    private void showAlertForCreatingBoard(String title, String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if(title != null){
            builder.setTitle(title);
        }
        if(msg != null){
            builder.setMessage(msg);
        }

        View view = LayoutInflater.from(this).inflate(R.layout.dialog_create_board, null);
        builder.setView(view);

        final EditText input = view.findViewById(R.id.editTextNewBoard);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String boardName = input.getText().toString().trim();
                if(boardName.length() > 0){
                    createNewBoard(boardName);
                }else{
                    Toast.makeText(BoardActivity.this, "The name is required to create a new Board", Toast.LENGTH_SHORT).show();
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

//    CRUD ACTIONS
    private void createNewBoard(String boardName){
        realm.beginTransaction();
        Board board = new Board(boardName);
        realm.copyToRealm(board);
        realm.commitTransaction();
    }
}
