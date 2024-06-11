package com.example.stockkeep;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBhelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "stor_keep";
    private static final String DB_TABLE1 = "user";
    private static final String DB_TABLE2 = "item_tb";
    private static final int DB_VER = 1;

    //Connect Database
    public DBhelper(Context context){
        super(context,DB_NAME,null,DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE user (email Text PRIMARY KEY,name Text NOT NULL,pw TEXT NOT NULL);");
        db.execSQL("CREATE TABLE item_tb (id INTEGER PRIMARY KEY AUTOINCREMENT,item Text NOT NULL,qt Text NOT NULL,date TEXT NOT NULL,email TEXT NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS item_tb");
        onCreate(db);
    }

    //---------------------------This is user DB Handler Part/Start---------------------------
    //Insert Database-user
    public boolean InsertUserData(String name,  String email,  String pw ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("name", name);
        contentValues.put("email", email);
        contentValues.put("pw", pw);

        long result = db.insert("user", null, contentValues);
        return result != -1;
    }

    //Search Data-user
    public Cursor ViewUserData(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM user WHERE email=?", new String[]{email});
    }


    //---------------------------This is Item DB Handler Part/Start---------------------------
    //Insert Database-user
    public boolean InsertItemData(String item,  String qt,  String date,  String email ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("item", item);
        contentValues.put("qt", qt);
        contentValues.put("date", date);
        contentValues.put("email", email);

        long result = db.insert("item_tb", null, contentValues);
        return result != -1;
    }
    //Search Data-item
    public Cursor ViewItem(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM item_tb WHERE email=?", new String[]{email});
    }
    //Search Data-item
    public Cursor ViewItemData(String item,String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM item_tb WHERE item=? AND email=?", new String[]{item,email});
    }

    //Update Database-Item
    public boolean updateItem(String item,String date,String qt) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        if (date != null) {
            contentValues.put("date", date);
        }
        if (qt != null) {
            contentValues.put("qt", qt);
        }

        @SuppressLint("") Cursor cursor = db.rawQuery("SELECT * FROM item_tb WHERE item=?", new String[]{item});
        if (cursor.getCount() > 0) {
            long result = db.update("item_tb", contentValues, "item=?", new String[]{item});
            return result != -1;
        }
        return false;
    }

    //Delete Database Data-Item
    public boolean DeleteItem(String item) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM item_tb WHERE item=?", new String[]{item});
        if (cursor.getCount() > 0) {
            cursor.close();
            long result = db.delete("item_tb", "item=?", new String[]{item});
            return result != -1;
        }
        cursor.close();
        return false;
    }
}
