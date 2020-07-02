package mardiwaluyo.com.mardiwaluyomobile.main.utility;

import android.content.Context;
import android.util.Log;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import mardiwaluyo.com.mardiwaluyomobile.main.main.MainMenuActivity;
import mardiwaluyo.com.mardiwaluyomobile.main.registration.model.Klinik;
import mardiwaluyo.com.mardiwaluyomobile.main.room.model.Room;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Wendra on 10/21/2017.
 */

public class DateUtil {

    static OkHttpClient client = WebServicesUtil.connect();
    static String url = WebServicesUtil.getServiceUrl();


    public static Integer getMaxDaysRegis(Context context) {
        //final Integer  max_days = 7;
        Integer  max_days =0 ;
        if(SharedData.getKey(context, "maxHari")!=null)
        {
            try {
                 max_days  = Integer.valueOf(SharedData.getKey(context, "maxHari"));
                max_days  = Integer.valueOf(SharedData.getKey(context, "maxHari"));
                return  max_days;
            }
            catch (Exception e)
            {
                return max_days;
            }

        }

        return max_days;

    }

    public static String changeFormatDate(String date_input,String format_old,String format_new)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(format_old);
        Date parsedDate = null;
        try {
            parsedDate = sdf.parse(date_input);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sdf= new SimpleDateFormat(format_new);
        String newFormatDate = sdf.format(parsedDate);
        return  newFormatDate;
    }

    public static Date FormatStringToDate(String date_input,String format_old)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(format_old);
        Date parsedDate = null;
        try {
            parsedDate = sdf.parse(date_input);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parsedDate;
    }

    public static String getCurrentDateTime(String format)
    { //"dd-MM-yyyy HH:mm:ss"
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String strDate = sdf.format(c.getTime());
        return strDate;
    }

    public static String formatDateTimeToDate(String date)
    {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa", Locale.US);
        Date date_new = null;
        try {
          date_new = format.parse(date);
            Log.d("datenya", String.valueOf(date_new));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        format = new SimpleDateFormat("HH:mm:ss");
        String dateString = format.format(date_new);
        return dateString;
    }


    public static Boolean getCurrentDateFromServer(Context context ) throws IOException {
       try{
           Request request = new Request.Builder().url(url + "/currentdate/").build();
           Response response = client.newCall(request).execute();
           String results = response.body().string();
           SharedData.setKey(context, "currentDate", results.substring(1,11) );

       }
       catch (Exception e){
           return false;
       }

        return true;
    }

    public static Boolean getMaxDate(Context context ) throws IOException {
        Request request = new Request.Builder().url(url + "/GetOpenHari/01/").build();
        Response response = client.newCall(request).execute();
        String results = response.body().string();
        Log.d("Hari", results );
        try {
            Log.d("Hari", "123" );
            JSONObject json = new JSONObject(results);
            String maxHari = json.getString("hari");
            Log.d("Hari", maxHari );
            SharedData.setKey(context, "maxHari", maxHari);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
