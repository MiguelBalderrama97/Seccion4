package com.example.miguel.seccion4_prueba.Activities;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.miguel.seccion4_prueba.Adapters.BoardAdapter;
import com.example.miguel.seccion4_prueba.Models.Board;
import com.example.miguel.seccion4_prueba.R;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class BoardActivity extends AppCompatActivity {

    private Realm realm;

    private FloatingActionButton fab;
    private ListView listView;
    private BoardAdapter adapter;
    private RealmResults<Board> boards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
//        DB Realm
        realm = Realm.getDefaultInstance();

        boards = realm.where(Board.class).findAll();

        adapter = new BoardAdapter(this, boards, R.layout.list_view_board_item);
        listView = findViewById(R.id.listViewBoard);
        listView.setAdapter(adapter);

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
