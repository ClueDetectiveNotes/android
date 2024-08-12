package com.jobseeker.cluedetectivenotes.persistence.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "clueDetectiveNotes.db";
    private static final int DATABASE_VERSION = 1;

    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        initDb(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void initDb(){
        initDb(null);
    }

    public void initDb(SQLiteDatabase db){
        if(db == null){
            db = this.getWritableDatabase();
        }
        db.execSQL("DROP TABLE IF EXISTS MULTI_LANG");
        db.execSQL("create table MULTI_LANG (CODE text, TYPE text, LANG text, VALUE text, primary key(CODE, TYPE, LANG));");

        db.execSQL("INSERT INTO MULTI_LANG VALUES ('SCARLET','CRD','KO','스칼렛');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('MUSTARD','CRD','KO','머스타드');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('WHITE','CRD','KO','화이트');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('GREEN','CRD','KO','그린');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('PEACOCK','CRD','KO','픽콕');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('PLUM','CRD','KO','플럼');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('CANDLESTICK','CRD','KO','촛대');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('KNIFE','CRD','KO','단검');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('LEAD_PIPE','CRD','KO','파이프');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('REVOLVER','CRD','KO','권총');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('ROPE','CRD','KO','밧줄');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('WRENCH','CRD','KO','렌치');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('BATHROOM','CRD','KO','욕실');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('STUDY','CRD','KO','서재');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('GAME_ROOM','CRD','KO','게임룸');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('GARAGE','CRD','KO','차고');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('BEDROOM','CRD','KO','침실');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('LIVING_ROOM','CRD','KO','거실');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('KITCHEN','CRD','KO','부엌');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('YARD','CRD','KO','마당');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('DINING_ROOM','CRD','KO','식당');");

        db.execSQL("INSERT INTO MULTI_LANG VALUES ('SUSPECT','CTYPE','KO','용의자');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('WEAPON','CTYPE','KO','흉기');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('CRIME_SCENE','CTYPE','KO','범행장소');");
    }

    public Map<String, String> getMultiLang (){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] selectionArgs = {"KO"};
        Cursor cursor = db.rawQuery("SELECT CODE, TYPE, VALUE FROM MULTI_LANG WHERE LANG = ?",selectionArgs);
        Map<String,String> result = new HashMap<>();
        while (cursor.moveToNext()) {
            result.put(cursor.getString(1)+"."+cursor.getString(0),cursor.getString(2));
        }
        cursor.close();
        return result;
    }
}
