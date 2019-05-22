package mardiwaluyo.com.mardiwaluyomobile.main.registration;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mardiwaluyo.com.mardiwaluyomobile.R;
import mardiwaluyo.com.mardiwaluyomobile.main.main.MainMenuActivity;
import mardiwaluyo.com.mardiwaluyomobile.main.registration.adapter.DokterListAdapter;
import mardiwaluyo.com.mardiwaluyomobile.main.registration.model.Dokter;
import mardiwaluyo.com.mardiwaluyomobile.main.registration.service.DokterServices;
import mardiwaluyo.com.mardiwaluyomobile.main.schedule.DokterScheduleFragment;
import mardiwaluyo.com.mardiwaluyomobile.main.schedule.ScheduleActivity;
import mardiwaluyo.com.mardiwaluyomobile.main.utility.DatabaseHandler;
import mardiwaluyo.com.mardiwaluyomobile.main.utility.DialogAlert;
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
    String tgl_regis = "";

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
            dialogAlert.alertValidationWithJadwal(DokterPickerActivity.this, "Peringatan", dokter.getResponse(), dokter.getNid(), dokter.getNamaDokter());
        } else {
            intent.putExtra("nidDokter", dokter.getNid());
            intent.putExtra("namaDokter", dokter.getNamaDokter());

            setResult(RESULT_OK, intent);
            finish();
        }

    }


   /* private void alertValidationWithJadwal(final Context context, String title, String message, final String nid, final String namaDokter) {

        new MaterialStyledDialog.Builder(context)
                .setTitle(title)
                .setCancelable(Boolean.FALSE)
                .setDescription(message)
                .setPositiveText("OK")
                .setNegativeText("Lihat Jadwal Dokter")
                .setIcon(R.drawable.logo_rsmw)
                .withIconAnimation(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Intent intent = new Intent(DokterPickerActivity.this, ScheduleActivity.class);
                        intent.putExtra("viewpager_position", 1);
                        intent.putExtra("source", "dialog");
                        intent.putExtra("nid", nid);
                        intent.putExtra("namaDokter", namaDokter);
                        startActivity(intent);

                    }
                })
                .show();
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dokter_picker);
        ButterKnife.bind(this);
        db = new DatabaseHandler(this);
        tgl_regis = getIntent().getStringExtra("tgl_regis");
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
            dokterList = DokterServices.getDokterByKlinik(kodeKlinik,tgl_regis);
            for (Dokter dokter : dokterList) {
                Log.d("Dokter List", dokter.getNamaDokter());
            }
        } catch (IOException e) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    DialogAlert dialogAlert = new DialogAlert();
                    dialogAlert.alertValidation(DokterPickerActivity.this, "Peringatan", "Mohon Maaf tidak Mendapatkan Data Dokter, mohon dicoba kembali");
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
                    dialogAlert.alertValidation(DokterPickerActivity.this, "Peringatan", "Mohon Maaf tidak Mendapatkan Data Dokter, mohon dicoba kembali");
                }
            });
        }


    }
}





