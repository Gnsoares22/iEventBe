package com.example.ieventbe.BancoBD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.strictmode.SqliteObjectLeakedViolation;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "login";
    private static final int DB_VERSION = 1;


    public DatabaseHelper(Context context) {
        super(context,DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sqlLogin = "CREATE TABLE login(id INTEGER PRIMARY KEY AUTOINCREMENT, email VARCHAR NOT NULL," +
                "password VARCHAR NOT NULL, type VARCHAR NOT NULL);";

        db.execSQL(sqlLogin);
    }

    public boolean addLogin(String email, String password, String type){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email",email);
        contentValues.put("password",password);
        contentValues.put("type",type);
        db.insert("login",null,contentValues);
        db.close();
        return true;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sqlLogin = "DROP TABLE IF EXISTS login";

        db.execSQL(sqlLogin);

        onCreate(db);

    }

    public boolean checkemailesenhaUsuario(String email,String senha){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM login WHERE email=? and password=? and type='U'", new String[]{email, senha});

       if(cursor.getCount() > 0) return true;
       else return false;
    }

    public boolean checkemailesenhaEmpresa(String email,String senha){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM login WHERE email=? and password=? and type='E'", new String[]{email, senha});

        if(cursor.getCount() > 0) return true;
        else return false;
    }

}
