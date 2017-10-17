package bethesda.com.bethesdahospitalmobile.main.utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedHashMap;

import bethesda.com.bethesdahospitalmobile.main.registration.model.Klinik;

/**
 * Created by Wendra on 10/14/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "bethesdaMobiledb";
    public static final int DATABASE_VERSION = 1;
    public static final String KLINIK_TABLE_NAME = "Klinik";
    public static final String KLINIK_KODE = "kode_klinik";
    public static final String KLINIK_NAME = "nama_klinik";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null,
                DATABASE_VERSION);
    }




    @Override
    public void onCreate(SQLiteDatabase db) {
        createTableKlinik(db);
    }

    private void createTableKlinik(SQLiteDatabase db) {
        String str_createTblKlinik = "create table " + KLINIK_TABLE_NAME +
                "(" + KLINIK_KODE + " text," +
                KLINIK_NAME + "  text);";
        db.execSQL(str_createTblKlinik);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL("drop table if exists " + KLINIK_TABLE_NAME);
        onCreate(db);
    }

    public void addKlinikToTable(Klinik klinik) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KLINIK_KODE, klinik.getKodeKlinik());
        values.put(KLINIK_NAME, klinik.getNamaKlinik());
        db.insert(KLINIK_TABLE_NAME, null, values);
        db.close();
    }

    public Boolean deleteAllKlinik() {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.execSQL("delete from " + KLINIK_TABLE_NAME);
        } catch (SQLiteException s) {
            s.printStackTrace();
            return false;
        }
        return true;
    }

    public LinkedHashMap<String,String> getKlinikFromDB() {
        LinkedHashMap<String,String> klinikMap = new LinkedHashMap<>();
        String strSql = "select * from " + KLINIK_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(strSql, null);
        if (cursor.moveToFirst()) {
            do {
                String klinik_kode = cursor.getString(cursor.getColumnIndex(KLINIK_KODE));
                String klinik_name = cursor.getString(cursor.getColumnIndex(KLINIK_NAME));
                klinikMap.put(klinik_kode,klinik_name);
            } while (cursor.moveToNext());
        }
        db.close();
        return klinikMap;
    }

}
