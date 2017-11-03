package bethesda.com.bethesdahospitalmobile.main.room.service;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import bethesda.com.bethesdahospitalmobile.main.room.model.Room;
import bethesda.com.bethesdahospitalmobile.main.utility.WebServicesUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Rs Bethesda on 10/31/2017.
 */

public class RoomServices {
    static OkHttpClient client = WebServicesUtil.connect();
    static String url = WebServicesUtil.getServiceUrl();

    public static List<Room> getAvailableRoom() throws IOException {
        List<Room> roomList = new ArrayList<>();
        Request request = new Request.Builder().url(url + "/dashboard/").build();
        Response response = client.newCall(request).execute();
        String results = response.body().string();
        try {
            JSONArray jsonArray = new JSONArray(results);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                Integer numKosong = (jsonObject.getInt("KamarKosong"));
                String kodeKelas = (jsonObject.getString("KodeKelKelas"));
                String namaKelas = (jsonObject.getString("NamaKelas"));
                Room room = new Room(kodeKelas, namaKelas, numKosong);
                Log.d("Kamar",kodeKelas);
                roomList.add(room);
            }
        } catch (JSONException jEx) {
            Log.d("Kamar Error", jEx.getLocalizedMessage());
        }
        return roomList;

    }
}
