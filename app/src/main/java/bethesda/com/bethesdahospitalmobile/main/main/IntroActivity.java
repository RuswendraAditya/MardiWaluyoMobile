package bethesda.com.bethesdahospitalmobile.main.main;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import bethesda.com.bethesdahospitalmobile.R;
import bethesda.com.bethesdahospitalmobile.main.registration.service.DokterServices;
import bethesda.com.bethesdahospitalmobile.main.registration.service.KlinikServices;
import bethesda.com.bethesdahospitalmobile.main.utility.DatabaseHandler;
import bethesda.com.bethesdahospitalmobile.main.utility.NetworkStatus;
import bethesda.com.bethesdahospitalmobile.main.utility.SharedData;

public class IntroActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;
    private DatabaseHandler db;
    private NetworkStatus networkStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        db = new DatabaseHandler(IntroActivity.this);
        networkStatus = new NetworkStatus();
        SharedData.clearData(IntroActivity.this);
        Log.d("Online", String.valueOf(networkStatus.isOnline(IntroActivity.this)));

        if (networkStatus.isOnline(IntroActivity.this)) {
            new PrefetchData().execute();
        } else {
            Toast.makeText(IntroActivity.this, "Koneksi Internet Tidak Ditemukan", Toast.LENGTH_LONG).show();
             finish();
        }



    /*    new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent i = new Intent(IntroActivity.this, MainMenuActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);*/
    }


    private class PrefetchData extends AsyncTask<Void, Void, Void> {
        private Boolean is_success;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            is_success = false;
            try {
                if (db.deleteAllKlinik()
                        && KlinikServices.getAllKlinikServices(db)
                        && db.deleteAllDokter()
                        && DokterServices.insertDokterToTable(db)) {
                    is_success = true;
                }
            } catch (IOException e) {
                e.printStackTrace();
                finish();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (is_success) {
                Intent intent = new Intent(IntroActivity.this, MainMenuActivity.class);
                startActivity(intent);
                finish();
            } else {
                finish();
            }

        }
    }


}


