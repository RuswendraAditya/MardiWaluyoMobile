package mardiwaluyo.com.mardiwaluyomobile.main.registration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mardiwaluyo.com.mardiwaluyomobile.R;
import mardiwaluyo.com.mardiwaluyomobile.main.registration.model.Klinik;
import mardiwaluyo.com.mardiwaluyomobile.main.registration.service.KlinikServices;
import mardiwaluyo.com.mardiwaluyomobile.main.utility.DatabaseHandler;
import bethesda.com.bethesdahospitalmobile.main.registration.adapter.KlinikListAdapter;

import bethesda.com.bethesdahospitalmobile.main.utility.DialogAlert;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class KlinikPickerActivity extends AppCompatActivity {
    @BindView(R.id.listKlinik)
    ListView listKlinik;
    @BindView(R.id.editKlinikSearch)
    EditText editKlinikSearch;

    List<Klinik> klinikList = new ArrayList<>();
    List<Klinik> klinikListTemp = new ArrayList<>();
    private DatabaseHandler db;
    int textlength = 0;
    String kodeKlinik = "";
    String kodeDokter = "";
    String tgl_regis = "";
String namaDokter = "";
    KlinikListAdapter adapter;
    @OnItemClick(R.id.listKlinik)
    public void onItemClick(AdapterView<?> parent,
                            int position) {
        Klinik klinik = null;
        if (klinikList.size() > 0) {
            klinik = klinikList.get(position);
        }
        if (klinikListTemp.size() > 0) {
            klinik = klinikListTemp.get(position);
        }
        Intent intent = new Intent();

        if (!klinik.getPraktek().isEmpty()&& klinik.getPraktek().equals("false")) {
            DialogAlert dialogAlert = new DialogAlert();
            dialogAlert.alertValidationWithJadwal(KlinikPickerActivity.this, "Peringatan", klinik.getResponse(), kodeDokter, namaDokter);
        } else {
            intent.putExtra("kodeKlinik", klinik.getKodeKlinik());
            intent.putExtra("namaKlinik", klinik.getNamaKlinik());


            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_klinik_picker);
        ButterKnife.bind(this);
        db = new DatabaseHandler(KlinikPickerActivity.this);
        tgl_regis = getIntent().getStringExtra("tgl_regis");
        kodeKlinik = getIntent().getStringExtra("kodeKlinik");
        kodeDokter = getIntent().getStringExtra("kodeDokter");
        namaDokter = getIntent().getStringExtra("namaDokter");
        KlinikTask klinikTask = new KlinikTask();
        klinikTask.execute();
        editKlinikSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                klinikListTemp.clear();
                textlength = editKlinikSearch.getText().length();
                for (int i = 0; i < klinikList.size(); i++) {
                    if (textlength <= klinikList.get(i).getNamaKlinik().length()) {
                        if (klinikList.get(i).getNamaKlinik().toLowerCase().trim().contains(
                                editKlinikSearch.getText().toString().toLowerCase().trim())) {
                            klinikListTemp.add(klinikList.get(i));
                        }
                    }
                }
                adapter = new KlinikListAdapter(klinikListTemp, KlinikPickerActivity.this);
                listKlinik.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    private class KlinikTask extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(KlinikPickerActivity.this,
                    "Please Wait",
                    "Get Data Klinik");
        }

        @Override
        protected Void doInBackground(Void... params) {
          /*  if (!kodeDokter.equals("") && kodeKlinik.equals("")) {
                getKlinikByDokter();
            }
            if (kodeDokter.equals("") && kodeKlinik.equals("")) {
                getAllKlinik();
            }*/
           // getAllKlinik();
            if(!kodeDokter.equals("") )
            {


                getKlinikByDokter();
            }
            else
            {
                getAllKlinik();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            progressDialog.dismiss();
            if (klinikList.size() > 0) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new KlinikListAdapter(klinikList, KlinikPickerActivity.this);
                        listKlinik.setAdapter(adapter);
                    }
                });
            }
        }
    }

    private void getKlinikByDokter() {
        try {
            klinikList = KlinikServices.getKlinikByDokter(kodeDokter,tgl_regis);
            for (Klinik klinik : klinikList) {
                Log.d("Klinik List", klinik.getNamaKlinik());
            }
        } catch (IOException e) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    DialogAlert dialogAlert = new DialogAlert();
                    dialogAlert.alertValidation(KlinikPickerActivity.this, "Peringatan", "Mohon Maaf tidak Mendapatkan Data Klinik, mohon dicoba kembali");
                }
            });
        }


    }


    private void getAllKlinik() {
        try {
            klinikList = db.getKlinikFromDB();
        } catch (Exception e) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    DialogAlert dialogAlert = new DialogAlert();
                    dialogAlert.alertValidation(KlinikPickerActivity.this, "Peringatan", "Mohon Maaf tidak Mendapatkan Data Klinik, mohon dicoba kembali");
                }
            });
        }


    }


}
