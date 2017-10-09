package bethesda.com.bethesdahospitalmobile.main.Utility;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by Wendra on 10/9/2017.
 */

public class WebServicesUtil {

    public static String getServiceUrl() {
        final String SERVICE_URL = "http://10.10.0.120:8090/API/";
        return SERVICE_URL;
    }

    public String getData()
    {
        return "test";
    }
    public static OkHttpClient connect() {

        final OkHttpClient client;
        client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS).build();

        return client;
    }
}
