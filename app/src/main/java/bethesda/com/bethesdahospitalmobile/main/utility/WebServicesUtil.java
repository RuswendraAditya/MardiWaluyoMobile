package bethesda.com.bethesdahospitalmobile.main.utility;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by Wendra on 10/9/2017.
 */

public class WebServicesUtil {

    public static String getServiceUrl() {
        //release version
       final String SERVICE_URL = "http://180.214.244.190:8090/API/";
        //testing version
        //final String SERVICE_URL = "http://180.214.244.190:8010/API/";
        return SERVICE_URL;
    }

    public String getData()
    {
        return "test123";
    }
    public static OkHttpClient connect() {

        final OkHttpClient client;
        client = new OkHttpClient.Builder()
                .connectTimeout(90, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS).build();

        return client;
    }
}
