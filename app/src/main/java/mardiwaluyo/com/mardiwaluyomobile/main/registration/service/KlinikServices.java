package mardiwaluyo.com.mardiwaluyomobile.main.registration.service;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import mardiwaluyo.com.mardiwaluyomobile.main.registration.model.Klinik;
import mardiwaluyo.com.mardiwaluyomobile.main.utility.DatabaseHandler;
import mardiwaluyo.com.mardiwaluyomobile.main.utility.WebServicesUtil;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Wendra on 10/13/2017.
 */

public class KlinikServices {

    static OkHttpClient client = WebServicesUtil.connect();
    static String url = WebServicesUtil.getServiceUrl();

    public static Boolean getAllKlinikServices(DatabaseHandler db) throws IOException {
        Request request = new Request.Builder().url(url + "/Klinik/").build();
        Response response = client.newCall(request).execute();
        String results = response.body().string();
        try {
            JSONArray jsonArray = new JSONArray(results);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String kodeKlinik = jsonObject.getString("KodeKlinik");
                String namaKlinik = jsonObject.getString("NamaKlinik");
                Klinik klinik = new Klinik(kodeKlinik,namaKlinik);
                db.addKlinikToTable(klinik);
            }
        } catch (JSONException jEx) {
            Log.d("Klinik Error", jEx.getLocalizedMessage());
            return false;
        }


        return true;
    }


    public static List<Klinik> getKlinikByDokter(String dokter, String tgl) throws IOException {
        List<Klinik> klinikList = new ArrayList<>();
        Request request = new Request.Builder().url(url + "/KlinikDokterJanji/?cNID=" + dokter+"&dTanggal="+tgl).build();
        Response response = client.newCall(request).execute();
        String results = response.body().string();
        try {
            JSONArray jsonArray = new JSONArray(results);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Klinik klinik  = new Klinik();
               klinik.setKodeKlinik(jsonObject.getString("KodeKlinik"));
                klinik.setNamaKlinik(jsonObject.getString("NamaKlinik"));
                klinik.setPraktek(jsonObject.getString("praktek"));
                klinik.setResponse(jsonObject.getString("response"));
                klinikList.add(klinik);
            }
        } catch (JSONException jEx) {
            Log.d("Klinik by Dokter error", jEx.getLocalizedMessage());
        }
        return klinikList;

    }

}
