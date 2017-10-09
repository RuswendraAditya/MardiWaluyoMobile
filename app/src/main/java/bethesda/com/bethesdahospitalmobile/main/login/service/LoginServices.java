package bethesda.com.bethesdahospitalmobile.main.login.service;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import bethesda.com.bethesdahospitalmobile.main.Utility.WebServicesUtil;
import bethesda.com.bethesdahospitalmobile.main.login.model.Login;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Wendra on 10/9/2017.
 */

public class LoginServices {
    static OkHttpClient client = WebServicesUtil.connect();
    static String url = WebServicesUtil.getServiceUrl();

    public static Login getLoginByNoRm(String noRM) throws IOException {
        Login login = new Login();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url + "/Pasien").newBuilder();
        urlBuilder.addPathSegment(String.valueOf(noRM));
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        String result = response.body().string();

        try {
            JSONObject jsonObject = new JSONObject(result);
            if (!jsonObject.isNull("noRM")) {
                login.setNoRM(jsonObject.getString("noRM"));
                login.setResponse(jsonObject.getString("response"));
                login.setDeskripsiResponse(jsonObject.getString("deskripsiresponse"));
            }
        } catch (JSONException jEx) {
            Log.d("MyError", jEx.getLocalizedMessage());
        }
        return login;
    }


}
