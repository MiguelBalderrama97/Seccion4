package com.example.miguel.seccion4_prueba.App;

import android.app.Application;

import com.example.miguel.seccion4_prueba.Models.Board;
import com.example.miguel.seccion4_prueba.Models.Note;

import java.util.concurrent.atomic.AtomicInteger;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class MyApplication extends Application {

    public static AtomicInteger BoardID = new AtomicInteger();
    public static AtomicInteger NoteID = new AtomicInteger();

    @Override
    public void onCreate() {
        super.onCreate();
        Realm realm = Realm.getDefaultInstance();
        BoardID = getIdByTable(realm, Board.class);
        NoteID = getIdByTable(realm, Note.class);
        realm.close();
    }

//    METODO PARA AUTOINCREMENT
    private <T extends RealmObject> AtomicInteger getIdByTable(Realm realm, Class<T> annyClass){
        RealmResults<T> results = realm.where(annyClass).findAll();
        return (results.size() > 0) ? new AtomicInteger(results.max("id").intValue()): new AtomicInteger(0);
    }
}
