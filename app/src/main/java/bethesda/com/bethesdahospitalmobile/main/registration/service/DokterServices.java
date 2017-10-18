package bethesda.com.bethesdahospitalmobile.main.registration.service;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import bethesda.com.bethesdahospitalmobile.main.registration.model.Dokter;
import bethesda.com.bethesdahospitalmobile.main.utility.WebServicesUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Wendra on 10/16/2017.
 */

public class DokterServices {

    static OkHttpClient client = WebServicesUtil.connect();
    static String url = WebServicesUtil.getServiceUrl();

    public static List<Dokter> getDokterByKlinik(String klinik) throws IOException {
        List<Dokter> dokterList = new ArrayList<>();
        Request request = new Request.Builder().url(url + "/DokterKlinik/" + klinik).build();
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

                dokterList.add(dokter);
            }
        } catch (JSONException jEx) {
            Log.d("Dokter error", jEx.getLocalizedMessage());
        }
        return dokterList;

    }

}
