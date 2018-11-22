package com.example.piotr.myapplication;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String database_name = "Listy_zakupowe";
    public static final String database_table = "Listy";

    public DatabaseHelper(Context context) {
        super(context, database_name, null, 2);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+database_table + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, PRODUKT TEXT, ILOSC TEXT, CENA TEXT)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +database_table);
        onCreate(db);
    }


    public long wstawdane (String Produkt, String Ilosc, String Cena) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put ("Produkt", Produkt);
        cv.put ("ilosc", Ilosc);
        cv.put ("cena", Cena);

        return db.insert(database_table, null, cv);
    }



    public SQLiteCursor pobierzdane (){
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteCursor kursor = (SQLiteCursor) db.rawQuery("SELECT * FROM " + database_table ,null );
        return kursor;

    }

    public boolean aktualizuj (String id, String Produkt, String Ilosc, String Cena) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put ("ID", id);
        cv.put ("Produkt", Produkt);
        cv.put ("ilosc", Ilosc);
        cv.put ("cena", Cena);

        db.update(database_table, cv, "ID = ?", new String[] { id });
        return true;

    }


    public boolean usun (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put ("ID", id);
        if (db.delete(database_table, "ID = ?", new String[] { id })>0)
        return true;
        else
            return false;

    }



}
