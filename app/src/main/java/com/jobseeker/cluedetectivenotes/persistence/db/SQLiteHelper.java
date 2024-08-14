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

        insertCardInfo(db);
        insertMessageInfo(db);
        insertButtonInfo(db);
    }

    private void insertCardInfo(SQLiteDatabase db){
        //한국어
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('ANSWER','CRD_HD','KR','정답');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('PUBLIC','CRD_HD','KR','공용');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('UNKNOWN','CRD_HD','KR','미확인');");

        db.execSQL("INSERT INTO MULTI_LANG VALUES ('SUSPECT','CRD_TP','KR','용의자');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('WEAPON','CRD_TP','KR','흉기');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('CRIME_SCENE','CRD_TP','KR','범행장소');");

        db.execSQL("INSERT INTO MULTI_LANG VALUES ('SCARLET','CRD','KR','스칼렛');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('MUSTARD','CRD','KR','머스타드');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('WHITE','CRD','KR','화이트');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('GREEN','CRD','KR','그린');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('PEACOCK','CRD','KR','픽콕');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('PLUM','CRD','KR','플럼');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('CANDLESTICK','CRD','KR','촛대');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('KNIFE','CRD','KR','단검');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('LEAD_PIPE','CRD','KR','파이프');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('REVOLVER','CRD','KR','권총');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('ROPE','CRD','KR','밧줄');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('WRENCH','CRD','KR','렌치');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('BATHROOM','CRD','KR','욕실');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('STUDY','CRD','KR','서재');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('GAME_ROOM','CRD','KR','게임룸');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('GARAGE','CRD','KR','차고');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('BEDROOM','CRD','KR','침실');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('LIVING_ROOM','CRD','KR','거실');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('KITCHEN','CRD','KR','부엌');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('YARD','CRD','KR','마당');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('DINING_ROOM','CRD','KR','식당');");
        
        //영어
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('ANSWER','CRD_HD','EN','Answer');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('PUBLIC','CRD_HD','EN','Public One');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('UNKNOWN','CRD_HD','EN','Unknown One');");

        db.execSQL("INSERT INTO MULTI_LANG VALUES ('SUSPECT','CRD_TP','EN','Suspect');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('WEAPON','CRD_TP','EN','Weapon');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('CRIME_SCENE','CRD_TP','EN','Crime Scene');");

        db.execSQL("INSERT INTO MULTI_LANG VALUES ('SCARLET','CRD','EN','Scarlet');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('MUSTARD','CRD','EN','Mustard');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('WHITE','CRD','EN','White');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('GREEN','CRD','EN','Green');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('PEACOCK','CRD','EN','Peacock');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('PLUM','CRD','EN','Plum');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('CANDLESTICK','CRD','EN','Candlestick');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('KNIFE','CRD','EN','Knife');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('LEAD_PIPE','CRD','EN','Lead Pipe');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('REVOLVER','CRD','EN','Revolver');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('ROPE','CRD','EN','Rope');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('WRENCH','CRD','EN','Wrench');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('BATHROOM','CRD','EN','Bathroom');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('STUDY','CRD','EN','Study');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('GAME_ROOM','CRD','EN','Game Room');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('GARAGE','CRD','EN','Garage');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('BEDROOM','CRD','EN','Bedroom');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('LIVING_ROOM','CRD','EN','Living Room');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('KITCHEN','CRD','EN','Kitchen');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('YARD','CRD','EN','Yard');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('DINING_ROOM','CRD','EN','Dining Room');");
    }

    private void insertMessageInfo(SQLiteDatabase db) {
        //한국어
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('PS_TITLE','MSG','KR','플레이어 설정');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('PS_DESC','MSG','KR','게임에 참여하는 인원 수와 이름을 설정해주세요.');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('PS_COND','MSG','KR','이름이 입력되지 않았거나, 중복된 이름이 있습니다.');");

        db.execSQL("INSERT INTO MULTI_LANG VALUES ('PDS_TITLE','MSG','KR','플레이어 설정');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('PDS_DESC','MSG','KR','자신을 선택하고, 플레이 순서에 맞게 정렬해주세요.');");

        db.execSQL("INSERT INTO MULTI_LANG VALUES ('HDS_TITLE','MSG','KR','개인 카드 설정');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('HDS_DESC','MSG','KR','개인 카드를 설정해주세요.');");

        db.execSQL("INSERT INTO MULTI_LANG VALUES ('PCS_TITLE','MSG','KR','공용 카드 설정');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('PCS_DESC','MSG','KR','공용 카드를 설정해주세요.');");

        db.execSQL("INSERT INTO MULTI_LANG VALUES ('SHT_IF_DL_TITLE','MSG','KR','추리모드를 해제 하시겠습니까?');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('SHT_IF_DL_DESC','MSG','KR','확인 버튼을 누르면 추리모드가 해제되며 기본 선택 모드로 돌아갑니다.');");

        db.execSQL("INSERT INTO MULTI_LANG VALUES ('CM_BOP','MSG','KR','한 번 더 누르시면 앱이 종료됩니다.');");
        
        //영어
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('PS_TITLE','MSG','EN','Player Setting');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('PS_DESC','MSG','EN','Please set the number of the players and name of the players who join in the game.');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('PS_COND','MSG','EN','The name is not entered, or there is a duplicate name.');");

        db.execSQL("INSERT INTO MULTI_LANG VALUES ('PDS_TITLE','MSG','EN','Player Setting');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('PDS_DESC','MSG','EN','Please choose yourself, and arrange it in order of play.');");

        db.execSQL("INSERT INTO MULTI_LANG VALUES ('HDS_TITLE','MSG','EN','Hand Setting');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('HDS_DESC','MSG','EN','Please set the your hand.');");

        db.execSQL("INSERT INTO MULTI_LANG VALUES ('PCS_TITLE','MSG','EN','Public Card Setting');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('PCS_DESC','MSG','EN','Please set up public cards.');");

        db.execSQL("INSERT INTO MULTI_LANG VALUES ('SHT_IF_DL_TITLE','MSG','EN','Do you want to turn off the inference mode?');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('SHT_IF_DL_DESC','MSG','EN','Press the Confirm button to release the inference mode and return to the default selection mode.');");

        db.execSQL("INSERT INTO MULTI_LANG VALUES ('CM_BOP','MSG','EN','If you press it again, the app will be shut down.');");
    }

    private void insertButtonInfo(SQLiteDatabase db) {
        //한국어
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('START','BTN','KR','시작');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('NEXT','BTN','KR','다음');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('PLAY','BTN','KR','플레이');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('CONFIRM','BTN','KR','확인');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('CANCEL','BTN','KR','취소');");

        //영어
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('START','BTN','EN','Start');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('NEXT','BTN','EN','Next');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('PLAY','BTN','EN','Play');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('CONFIRM','BTN','EN','Confirm');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('CANCEL','BTN','EN','Cancel');");
    }

    public Map<String, String> getMultiLang (String language){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] selectionArgs = {language};
        Cursor cursor = db.rawQuery("SELECT CODE, TYPE, VALUE FROM MULTI_LANG WHERE LANG = ?",selectionArgs);
        Map<String,String> result = new HashMap<>();
        while (cursor.moveToNext()) {
            result.put(cursor.getString(1)+"."+cursor.getString(0),cursor.getString(2));
        }
        cursor.close();
        return result;
    }
}