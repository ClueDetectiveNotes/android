package com.jobseeker.cluedetectivenotes.persistence.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.jobseeker.cluedetectivenotes.utils.DataMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        //옵션 생성
        db.execSQL("DROP TABLE IF EXISTS OPTIONS");
        db.execSQL("create table OPTIONS (CODE text, SEQ integer, TYPE text, VALUE text, primary key(CODE));");

        insertOptions(db);

        //공통 코드 생성
        db.execSQL("DROP TABLE IF EXISTS COMMON_CODE");
        db.execSQL("create table COMMON_CODE (CODE text, TYPE text, PART text, SEQ integer, primary key(CODE,PART));");

        insertCommonCode(db);

        //다국어 생성
        db.execSQL("DROP TABLE IF EXISTS MULTI_LANG");
        db.execSQL("create table MULTI_LANG (CODE text, TYPE text, LANG text, VALUE text, primary key(CODE, TYPE, LANG));");

        insertOptionsMultiLang(db);
        insertCardMultiLang(db);
        insertMessageMultiLang(db);
        insertButtonMultiLang(db);
    }

    private void insertOptions(SQLiteDatabase db){
        db.execSQL("INSERT INTO OPTIONS VALUES ('LANGUAGE',1,'SELECT_BOX','KR');");
        db.execSQL("INSERT INTO OPTIONS VALUES ('IS_USE_DARK_THEME',2,'TOGGLE','Y');");
        db.execSQL("INSERT INTO OPTIONS VALUES ('DARK_THEME_TYPE',2,'SELECT_BOX','FOLLOW_SYSTEM');");
        db.execSQL("INSERT INTO OPTIONS VALUES ('BLIND_TRANSPARENCY',1,'SLIDER','60');");
    }

    private void insertCommonCode(SQLiteDatabase db){
        db.execSQL("INSERT INTO COMMON_CODE VALUES ('KR','SELECT_BOX','LANGUAGE',1);");
        db.execSQL("INSERT INTO COMMON_CODE VALUES ('EN','SELECT_BOX','LANGUAGE',2);");

        db.execSQL("INSERT INTO COMMON_CODE VALUES ('Y','TOGGLE','IS_USE_DARK_THEME',1);");
        db.execSQL("INSERT INTO COMMON_CODE VALUES ('N','TOGGLE','IS_USE_DARK_THEME',2);");

        db.execSQL("INSERT INTO COMMON_CODE VALUES ('FOLLOW_SYSTEM','SELECT_BOX','DARK_THEME_TYPE',1);");
        db.execSQL("INSERT INTO COMMON_CODE VALUES ('APPLY_DARK_THEME','SELECT_BOX','DARK_THEME_TYPE',2);");
    }

    private void insertOptionsMultiLang(SQLiteDatabase db){
        //한국어
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('LANGUAGE','OPT','KR','언어');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('KR','CM_CD','KR','한국어');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('EN','CM_CD','KR','영어');");

        db.execSQL("INSERT INTO MULTI_LANG VALUES ('IS_USE_DARK_THEME','OPT','KR','다크 테마 사용 여부');");

        db.execSQL("INSERT INTO MULTI_LANG VALUES ('DARK_THEME_TYPE','OPT','KR','다크 테마 적용 유형');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('FOLLOW_SYSTEM','CM_CD','KR','시스템 설정에 따름');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('APPLY_DARK_THEME','CM_CD','KR','다크 테마 적용');");

        db.execSQL("INSERT INTO MULTI_LANG VALUES ('BLIND_TRANSPARENCY','OPT','KR','블라인드 투명도');");

        //영어
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('LANGUAGE','OPT','EN','Language');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('KR','CM_CD','EN','Korean');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('EN','CM_CD','EN','English');");

        db.execSQL("INSERT INTO MULTI_LANG VALUES ('IS_USE_DARK_THEME','OPT','EN','Whether to use the dark theme');");

        db.execSQL("INSERT INTO MULTI_LANG VALUES ('DARK_THEME_TYPE','OPT','EN','Dark Theme Application Types');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('FOLLOW_SYSTEM','CM_CD','EN','Depending on the system');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('APPLY_DARK_THEME','CM_CD','EN','Apply Dark Theme');");

        db.execSQL("INSERT INTO MULTI_LANG VALUES ('BLIND_TRANSPARENCY','OPT','EN','Blind Transparency');");

    }

    private void insertCardMultiLang(SQLiteDatabase db){
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

    private void insertMessageMultiLang(SQLiteDatabase db) {
        //한국어
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('PS_TITLE','MSG','KR','플레이어 설정');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('PS_DESC','MSG','KR','게임에 참여하는 인원 수와 이름을 설정해주세요.');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('PS_COND','MSG','KR','이름이 입력되지 않았거나, 중복된 이름이 있습니다.');");

        db.execSQL("INSERT INTO MULTI_LANG VALUES ('PDS_TITLE','MSG','KR','플레이어 설정');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('PDS_DESC','MSG','KR','자신을 선택하고, 플레이 순서에 맞게 정렬해주세요.');");

        db.execSQL("INSERT INTO MULTI_LANG VALUES ('HDS_TITLE','MSG','KR','개인 카드 설정');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('HDS_DESC','MSG','KR','개인 카드를 설정해주세요.');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('HDS_ALERT_TOAST','MSG','KR','한 종류의 카드를 모두 선택할 수 없습니다.');");

        db.execSQL("INSERT INTO MULTI_LANG VALUES ('PCS_TITLE','MSG','KR','공용 카드 설정');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('PCS_DESC','MSG','KR','공용 카드를 설정해주세요.');");

        db.execSQL("INSERT INTO MULTI_LANG VALUES ('SHT_IF_DL_TITLE','MSG','KR','추리모드를 해제 하시겠습니까?');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('SHT_IF_DL_DESC','MSG','KR','확인 버튼을 누르면 추리모드가 해제되며 기본 선택 모드로 돌아갑니다.');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('SHT_CB_ADSBMK_DESC','MSG','KR','사용할 서브 마커를 입력해주세요.');");

        db.execSQL("INSERT INTO MULTI_LANG VALUES ('CM_BOP','MSG','KR','한 번 더 누르시면 앱이 종료됩니다.');");


        //영어
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('PS_TITLE','MSG','EN','Player Setting');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('PS_DESC','MSG','EN','Please set the number of the players and name of the players who join in the game.');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('PS_COND','MSG','EN','The name is not entered, or there is a duplicate name.');");

        db.execSQL("INSERT INTO MULTI_LANG VALUES ('PDS_TITLE','MSG','EN','Player Setting');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('PDS_DESC','MSG','EN','Please choose yourself, and arrange it in order of play.');");

        db.execSQL("INSERT INTO MULTI_LANG VALUES ('HDS_TITLE','MSG','EN','Hand Setting');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('HDS_DESC','MSG','EN','Please set the your hand.');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('HDS_ALERT_TOAST','MSG','EN','Can not select all one type of card.');");

        db.execSQL("INSERT INTO MULTI_LANG VALUES ('PCS_TITLE','MSG','EN','Public Card Setting');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('PCS_DESC','MSG','EN','Please set up public cards.');");

        db.execSQL("INSERT INTO MULTI_LANG VALUES ('SHT_IF_DL_TITLE','MSG','EN','Do you want to turn off the inference mode?');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('SHT_IF_DL_DESC','MSG','EN','Press the Confirm button to release the inference mode and return to the default selection mode.');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('SHT_CB_ADSBMK_DESC','MSG','EN','Please enter the sub-marker to use.');");

        db.execSQL("INSERT INTO MULTI_LANG VALUES ('CM_BOP','MSG','EN','If you press it again, the app will be shut down.');");

    }

    private void insertButtonMultiLang(SQLiteDatabase db) {
        //한국어
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('START','BTN','KR','시작');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('OPTION','BTN','KR','옵션');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('NEXT','BTN','KR','다음');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('PLAY','BTN','KR','플레이');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('CONFIRM','BTN','KR','확인');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('CANCEL','BTN','KR','취소');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('HOME','BTN','KR','홈');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('SETTING','BTN','KR','설정');");

        //영어
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('START','BTN','EN','Start');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('OPTION','BTN','EN','Option');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('NEXT','BTN','EN','Next');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('PLAY','BTN','EN','Play');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('CONFIRM','BTN','EN','Confirm');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('CANCEL','BTN','EN','Cancel');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('HOME','BTN','EN','Home');");
        db.execSQL("INSERT INTO MULTI_LANG VALUES ('SETTING','BTN','EN','Setting');");
    }

    public Map<String, Map<String, String>> getOptions (){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT CODE, TYPE, VALUE FROM OPTIONS ORDER BY SEQ",null);
        Map<String,Map<String,String>> result = new HashMap<>();
        while (cursor.moveToNext()) {
            Map<String,String> row = new HashMap<>();
            row.put("TYPE",cursor.getString(1));
            row.put("VALUE",cursor.getString(2));
            result.put(cursor.getString(0),row);
        }
        cursor.close();
        return result;
    }

    public Map<String,List<Map<String,String>>> getCommonCode (){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT CODE, PART, TYPE FROM COMMON_CODE ORDER BY PART, SEQ",null);
        Map<String,List<Map<String,String>>> result = new HashMap<>();
        String key = "";
        List<Map<String,String>> pairList = new ArrayList<>();
        while (cursor.moveToNext()) {
            if(!key.equals(cursor.getString(1)+"."+cursor.getString(2))){
                pairList = new ArrayList<>();
                key = cursor.getString(1)+"."+cursor.getString(2);
                result.put(key,pairList);
            }
            Map<String,String> row = new HashMap<>();
            row.put("CODE",cursor.getString(0));
            pairList.add(row);
        }
        cursor.close();
        return result;
    }

    public DataMap getMultiLang (String language){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] selectionArgs = {language};
        Cursor cursor = db.rawQuery("SELECT CODE, TYPE, VALUE FROM MULTI_LANG WHERE LANG = ?",selectionArgs);
        DataMap result = new DataMap();
        while (cursor.moveToNext()) {
            result.putString(cursor.getString(1)+"."+cursor.getString(0),cursor.getString(2));
        }
        cursor.close();
        return result;
    }

    public void updateOption(String code, String value){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] bindArgs = {value, code};
        db.execSQL("UPDATE OPTIONS SET VALUE = ? WHERE CODE = ?",bindArgs);
    }
}
