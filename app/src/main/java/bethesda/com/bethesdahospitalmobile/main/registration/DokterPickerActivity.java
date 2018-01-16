package bethesda.com.bethesdahospitalmobile.main.registration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import bethesda.com.bethesdahospitalmobile.R;
import bethesda.com.bethesdahospitalmobile.main.registration.adapter.DokterListAdapter;
import bethesda.com.bethesdahospitalmobile.main.registration.model.Dokter;
import bethesda.com.bethesdahospitalmobile.main.registration.service.DokterServices;
import bethesda.com.bethesdahospitalmobile.main.utility.DatabaseHandler;
import bethesda.com.bethesdahospitalmobile.main.utility.DialogAlert;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class DokterPickerActivity extends AppCompatActivity {
    DatabaseHandler db;
    @BindView(R.id.listDokter)
    ListView listDokter;
    @BindView(R.id.editDokterSearch)
    EditText editDokterSearch;
    List<Dokter> dokterList = new ArrayList<>();
    List<Dokter> dokterListTemp = new ArrayList<>();
    int textlength = 0;
    DokterListAdapter adapter;
    String kodeKlinik = "";
    String kodeDokter = "";

    @OnItemClick(R.id.listDokter)
    public void onItemClick(AdapterView<?> parent,
                            int position) {

        Dokter dokter = null;
        if (dokterList.size() > 0) {
            dokter = dokterList.get(position);
        }
        if (dokterListTemp.size() > 0) {
            dokter = dokterListTemp.get(position);
        }

        Intent intent = new Intent();
        if (!dokter.getPraktek().isEmpty()&& dokter.getPraktek().equals("false")) {
            DialogAlert dialogAlert = new DialogAlert();
            dialogAlert.alertValidation(DokterPickerActivity.this, "Peringatan", "Dokter Sedang Tidak Praktek Hari Ini");
        } else {
            intent.putExtra("nidDokter", dokter.getNid());
            intent.putExtra("namaDokter", dokter.getNamaDokter());

            setResult(RESULT_OK, intent);
            finish();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dokter_picker);
        ButterKnife.bind(this);
        db = new DatabaseHandler(this);
        kodeKlinik = getIntent().getStringExtra("kodeKlinik");
        kodeDokter = getIntent().getStringExtra("kodeDokter");
        if (!kodeKlinik.equals("") || !kodeDokter.equals("")) {
            DokterKlinikTask dokterKlinikTask = new DokterKlinikTask();
            dokterKlinikTask.execute();
        } else {
            DialogAlert dialogAlert = new DialogAlert();
            dialogAlert.alertValidation(DokterPickerActivity.this, "Peringatan", "Mohon Maaf tidak Mendapatkan Data Dokter, mohon dicoba kembali");
        }

        editDokterSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                dokterListTemp.clear();
                textlength = editDokterSearch.getText().length();
                for (int i = 0; i < dokterList.size(); i++) {
                    if (textlength <= dokterList.get(i).getNamaDokter().length()) {
                        if (dokterList.get(i).getNamaDokter().toLowerCase().trim().contains(
                                editDokterSearch.getText().toString().toLowerCase().trim())) {
                            dokterListTemp.add(dokterList.get(i));
                        }
                    }
                }
                adapter = new DokterListAdapter(dokterListTemp, DokterPickerActivity.this);
                listDokter.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }


    private class DokterKlinikTask extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(DokterPickerActivity.this,
                    "Please Wait",
                    "Get Data Dokter");
        }

        @Override
        protected Void doInBackground(Void... params) {
            if (kodeDokter.equals("") && !kodeKlinik.equals("")) {
                getDokterKlinik();
            }
            if (kodeKlinik.equals("") && !kodeDokter.equals("")) {
                getAllDokter();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            progressDialog.dismiss();
            if (dokterList.size() > 0) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new DokterListAdapter(dokterList, DokterPickerActivity.this);
                        listDokter.setAdapter(adapter);
                    }
                });
            }
        }
    }

    private void getDokterKlinik() {
        try {
            dokterList = DokterServices.getDokterByKlinik(kodeKlinik);
            for (Dokter dokter : dokterList) {
                Log.d("Dokter List", dokter.getNamaDokter());
            }
        } catch (IOException e) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    DialogAlert dialogAlert = new DialogAlert();
                    dialogAlert.alertValidation(DokterPickerActivity.this, "Peringatan", "Mohon Maaf tidak Mendaptkan Data Dokter, mohon dicoba kembali");
                }
            });
        }


    }

    private void getAllDokter() {
        try {
            dokterList = db.getDokterFromDB();
            for (Dokter dokter : dokterList) {
                Log.d("Dokter List", dokter.getNamaDokter());
            }
        } catch (Exception e) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    DialogAlert dialogAlert = new DialogAlert();
                    dialogAlert.alertValidation(DokterPickerActivity.this, "Peringatan", "Mohon Maaf tidak Mendaptkan Data Dokter, mohon dicoba kembali");
                }
            });
        }


    }
}





