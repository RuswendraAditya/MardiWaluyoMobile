package bethesda.com.bethesdahospitalmobile.main.utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import bethesda.com.bethesdahospitalmobile.main.registration.model.Dokter;
import bethesda.com.bethesdahospitalmobile.main.registration.model.Klinik;
import bethesda.com.bethesdahospitalmobile.main.registration.model.RegistrationResult;

/**
 * Created by Wendra on 10/14/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "bethesdaMobiledb";
    public static final int DATABASE_VERSION = 1;
    public static final String KLINIK_TABLE_NAME = "Klinik";
    public static final String KLINIK_KODE = "kode_klinik";
    public static final String KLINIK_NAME = "nama_klinik";

    public static final String REGIS_TABLE_NAME = "Registrasi";
    public static final String REGIS_TGL = "tgl_regis";
    public static final String REGIS_JAM = "jam_regis";
    public static final String REGIS_NO_RM = "no_rm";
    public static final String REGIS_NAMA_PASIEN = "nama_pasien";
    public static final String REGIS_NO_REG = "no_reg";
    public static final String REGIS_NO_URUT = "no_urut";
    public static final String REGIS_KODE_KLINIK = "kode_klinik";
    public static final String REGIS_NAMA_KLINIK = "nama_klinik";
    public static final String REGIS_KODE_DOKTER = "kode_dokter";
    public static final String REGIS_NAMA_DOKTER = "nama_dokter";

    public static final String DOKTER_TABLE_NAME = "Dokter";
    public static final String DOKTER_KODE = "Kode_dokter";
    public static final String DOKTER_NAMA = "Nama_dokter";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null,
                DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        createTableKlinik(db);
        createTableRegis(db);
        createTableDokter(db);
    }

    private void createTableKlinik(SQLiteDatabase db) {
        String str_createTblKlinik = "create table " + KLINIK_TABLE_NAME +
                "(" + KLINIK_KODE + " text," +
                KLINIK_NAME + "  text);";
        db.execSQL(str_createTblKlinik);
    }

    private void createTableDokter(SQLiteDatabase db) {
        String str_createTblDokter = "create table " + DOKTER_TABLE_NAME +
                "(" + DOKTER_KODE + " text," +
                DOKTER_NAMA + "  text);";
        db.execSQL(str_createTblDokter);
    }


    private void createTableRegis(SQLiteDatabase db) {
        String str_createTblRegis = "create table " + REGIS_TABLE_NAME +
                "(" + REGIS_TGL + " text," +
                REGIS_JAM + " text," +
                REGIS_NO_RM + " text," +
                REGIS_NO_REG + " text," +
                REGIS_NAMA_PASIEN + " text," +
                REGIS_NO_URUT + " text," +
                REGIS_KODE_KLINIK + " text," +
                REGIS_NAMA_KLINIK + " text," +
                REGIS_KODE_DOKTER + " text," +
                REGIS_NAMA_DOKTER + "  text);";
        db.execSQL(str_createTblRegis);
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

    public LinkedHashMap<String, String> getKlinikFromDB() {
        LinkedHashMap<String, String> klinikMap = new LinkedHashMap<>();
        String strSql = "select * from " + KLINIK_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(strSql, null);
        if (cursor.moveToFirst()) {
            do {
                String klinik_kode = cursor.getString(cursor.getColumnIndex(KLINIK_KODE));
                String klinik_name = cursor.getString(cursor.getColumnIndex(KLINIK_NAME));
                klinikMap.put(klinik_kode, klinik_name);
            } while (cursor.moveToNext());
        }
        db.close();
        return klinikMap;
    }

    public void addDokterToTable(Dokter dokter) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DOKTER_KODE, dokter.getNid());
        values.put(DOKTER_NAMA, dokter.getNamaDokter());
        db.insert(DOKTER_TABLE_NAME, null, values);
        db.close();
    }

    public Boolean deleteAllDokter() {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.execSQL("delete from " + DOKTER_TABLE_NAME);
        } catch (SQLiteException s) {
            s.printStackTrace();
            return false;
        }
        return true;
    }

    public List<Dokter> getDokterFromDB() {
        List<Dokter> dokterList = new ArrayList<>();
        String strSql = "select * from " + DOKTER_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(strSql, null);
        if (cursor.moveToFirst()) {
            do {
                Dokter dokter = new Dokter();
                String dokter_kode = cursor.getString(cursor.getColumnIndex(DOKTER_KODE));
                String dokter_name = cursor.getString(cursor.getColumnIndex(DOKTER_NAMA));
                dokter.setNid(dokter_kode);
                dokter.setNamaDokter(dokter_name);
                dokter.setPraktek("true");
                dokterList.add(dokter);
            } while (cursor.moveToNext());
        }
        db.close();
        return dokterList;
    }

    public void addRegistrationToTable(RegistrationResult registrationResult) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(REGIS_TGL, registrationResult.getTglReg());
        values.put(REGIS_JAM, registrationResult.getJamReg());
        values.put(REGIS_NO_RM, registrationResult.getNoRm());
        values.put(REGIS_NO_REG, registrationResult.getNoRegj());
        values.put(REGIS_NAMA_PASIEN, registrationResult.getNamaPasien());
        values.put(REGIS_NO_URUT, registrationResult.getNoUrutdokter());
        values.put(REGIS_KODE_DOKTER, registrationResult.getKodeDokter());
        values.put(REGIS_NAMA_DOKTER, registrationResult.getNamaDokter());
        values.put(REGIS_KODE_KLINIK, registrationResult.getKodeKlinik());
        values.put(REGIS_NAMA_KLINIK, registrationResult.getNamaKlinik());
        db.insert(REGIS_TABLE_NAME, null, values);
        db.close();
    }

    public List<RegistrationResult> getRegisFromDB() {
        List<RegistrationResult> regisList = new ArrayList<>();
        String strSql = "select * from " + REGIS_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(strSql, null);
        if (cursor.moveToFirst()) {
            do {
                RegistrationResult result = new RegistrationResult();
                result.setTglReg(cursor.getString(cursor.getColumnIndex(REGIS_TGL)));
                result.setJamReg(cursor.getString(cursor.getColumnIndex(REGIS_JAM)));
                result.setNoRm(cursor.getString(cursor.getColumnIndex(REGIS_NO_RM)));
                result.setNoRegj(cursor.getString(cursor.getColumnIndex(REGIS_NO_REG)));
                result.setNamaPasien(cursor.getString(cursor.getColumnIndex(REGIS_NAMA_PASIEN)));
                result.setNoUrutdokter(cursor.getString(cursor.getColumnIndex(REGIS_NO_URUT)));
                result.setKodeKlinik(cursor.getString(cursor.getColumnIndex(REGIS_KODE_KLINIK)));
                result.setNamaKlinik(cursor.getString(cursor.getColumnIndex(REGIS_NAMA_KLINIK)));
                result.setKodeDokter(cursor.getString(cursor.getColumnIndex(REGIS_KODE_DOKTER)));
                result.setNamaDokter(cursor.getString(cursor.getColumnIndex(REGIS_NAMA_DOKTER)));
                regisList.add(result);
            } while (cursor.moveToNext());
        }
        db.close();
        return regisList;
    }

    public List<RegistrationResult> getRegisFromDBByNoRM(String noRM) {
        List<RegistrationResult> regisList = new ArrayList<>();
        String order_string = "order by date(substr("+REGIS_TGL+", 7, 4) || '-' || substr("+REGIS_TGL+", 4, 2) || '-' " +
                "|| substr("+REGIS_TGL+", 1, 2)) asc";
        String strSql = "select * from " + REGIS_TABLE_NAME + " WHERE " + REGIS_NO_RM + "=? "+order_string;
        //String strSql = "select * from Registrasi" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(strSql, new String[]{noRM});
        //Cursor cursor = db.rawQuery(strSql,null);
        Log.d("total", String.valueOf(cursor.getCount()));
        if (cursor.moveToFirst()) {
            do {
                RegistrationResult result = new RegistrationResult();
                result.setTglReg(cursor.getString(cursor.getColumnIndex(REGIS_TGL)));
                result.setJamReg(cursor.getString(cursor.getColumnIndex(REGIS_JAM)));
                result.setNoRm(cursor.getString(cursor.getColumnIndex(REGIS_NO_RM)));
                result.setNoRegj(cursor.getString(cursor.getColumnIndex(REGIS_NO_REG)));
                result.setNamaPasien(cursor.getString(cursor.getColumnIndex(REGIS_NAMA_PASIEN)));
                result.setNoUrutdokter(cursor.getString(cursor.getColumnIndex(REGIS_NO_URUT)));
                result.setKodeKlinik(cursor.getString(cursor.getColumnIndex(REGIS_KODE_KLINIK)));
                result.setNamaKlinik(cursor.getString(cursor.getColumnIndex(REGIS_NAMA_KLINIK)));
                result.setKodeDokter(cursor.getString(cursor.getColumnIndex(REGIS_KODE_DOKTER)));
                result.setNamaDokter(cursor.getString(cursor.getColumnIndex(REGIS_NAMA_DOKTER)));
                regisList.add(result);
            } while (cursor.moveToNext());
        }
        db.close();
        return regisList;
    }

    public void deleteRegisNotToday(String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        String where_delete = "date(substr("+REGIS_TGL+", 7, 4) || '-' || substr("+REGIS_TGL+", 4, 2) || '-' || substr("+REGIS_TGL+", 1, 2))";
        db.delete(REGIS_TABLE_NAME, where_delete + "< ?",
                new String[]{String.valueOf(date)});
        db.close();
    }
}
