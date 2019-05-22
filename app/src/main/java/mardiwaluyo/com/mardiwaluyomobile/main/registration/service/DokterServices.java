package mardiwaluyo.com.mardiwaluyomobile.main.registration.service;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mardiwaluyo.com.mardiwaluyomobile.main.registration.model.Dokter;
import mardiwaluyo.com.mardiwaluyomobile.main.utility.DatabaseHandler;
import mardiwaluyo.com.mardiwaluyomobile.main.utility.WebServicesUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Wendra on 10/16/2017.
 */

public class DokterServices {

    static OkHttpClient client = WebServicesUtil.connect();
    static String url = WebServicesUtil.getServiceUrl();

    public static List<Dokter> getDokterByKlinik(String klinik,String tgl) throws IOException {
        List<Dokter> dokterList = new ArrayList<>();
      //  Request request = new Request.Builder().url(url + "/DokterKlinik/" + klinik).build();
        //change to dokter klink perjanjian
        Request request = new Request.Builder().url(url + "/DokterKlinikJanji/?cKodeKlinik=" + klinik+"&dTanggal="+tgl).build();
        Response response = client.newCall(request).execute();
        String results = response.body().string();
        try {
            JSONArray jsonArray = new JSONArray(results);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Dokter dokter = new Dokter();
                dokter.setMax(jsonObject.getInt("Max"));
                dokter.setNid(jsonObject.getString("NID"));
                dokter.setNamaDokter(jsonObject.getString("NamaDokter"));
                dokter.setPraktek(jsonObject.getString("praktek"));
                dokter.setResponse(jsonObject.getString("response"));
                dokterList.add(dokter);
            }
        } catch (JSONException jEx) {
            Log.d("Dokter error", jEx.getLocalizedMessage());
        }
        return dokterList;

    }

    public static Boolean insertDokterToTable(DatabaseHandler db) throws IOException {
        Request request = new Request.Builder().url(url + "/Dokter/").build();
        Response response = client.newCall(request).execute();
        String results = response.body().string();
        try {
            JSONArray jsonArray = new JSONArray(results);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String kodeDokter = jsonObject.getString("NID");
                String namaDokter = jsonObject.getString("NamaDokter");
                Dokter dokter = new Dokter();
                dokter.setNid(kodeDokter);
                dokter.setNamaDokter(namaDokter);
                db.addDokterToTable(dokter);
            }
        } catch (JSONException jEx) {
            Log.d("Klinik Error", jEx.getLocalizedMessage());
            return false;
        }


        return true;
    }

}
