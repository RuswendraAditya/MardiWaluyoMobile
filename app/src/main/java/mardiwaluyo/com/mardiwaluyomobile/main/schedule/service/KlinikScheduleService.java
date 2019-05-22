package mardiwaluyo.com.mardiwaluyomobile.main.schedule.service;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mardiwaluyo.com.mardiwaluyomobile.main.schedule.model.DokterKlinikSchedule;
import mardiwaluyo.com.mardiwaluyomobile.main.utility.WebServicesUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Wendra on 10/23/2017.
 */

public class KlinikScheduleService {

    static OkHttpClient client = WebServicesUtil.connect();
    static String url = WebServicesUtil.getServiceUrl();
    public static List<DokterKlinikSchedule> getJadwalDokterKlinik(String klinik) throws IOException {
        List<DokterKlinikSchedule> dokterKlinikList = new ArrayList<>();
        Request request = new Request.Builder().url(url + "/dokterPraktekberdasarKlinik/" + klinik).build();
        Response response = client.newCall(request).execute();
        String results = response.body().string();
        try {
            JSONArray jsonArray = new JSONArray(results);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                DokterKlinikSchedule dokter = new DokterKlinikSchedule();
                dokter.setNamaDokter(jsonObject.getString("NamaDokter"));
                dokter.setHari(jsonObject.getString("hari"));
                dokter.setJam_dari(jsonObject.getString("jam_dari"));
                dokter.setJam_selesai(jsonObject.getString("jam_selesai"));
                dokterKlinikList.add(dokter);
            }
        } catch (JSONException jEx) {
            Log.d("Dokter error", jEx.getLocalizedMessage());
        }
        return dokterKlinikList;

    }
}
