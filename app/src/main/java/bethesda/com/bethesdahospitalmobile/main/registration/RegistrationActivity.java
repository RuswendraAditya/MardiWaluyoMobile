package bethesda.com.bethesdahospitalmobile.main.registration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONException;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import bethesda.com.bethesdahospitalmobile.R;
import bethesda.com.bethesdahospitalmobile.main.registration.model.Registration;
import bethesda.com.bethesdahospitalmobile.main.registration.model.RegistrationResult;
import bethesda.com.bethesdahospitalmobile.main.registration.service.RegistrationServices;
import bethesda.com.bethesdahospitalmobile.main.utility.DatabaseHandler;
import bethesda.com.bethesdahospitalmobile.main.utility.DateUtil;
import bethesda.com.bethesdahospitalmobile.main.utility.DialogAlert;
import bethesda.com.bethesdahospitalmobile.main.utility.NetworkStatus;
import bethesda.com.bethesdahospitalmobile.main.utility.SharedData;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegistrationActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private DialogAlert dialogAlert;
    @BindView(R.id.txtNoRM)
    TextView txtNoRM;
    @BindView(R.id.txtNama)
    TextView txtNama;
    @BindView(R.id.txtTglLahir)
    TextView txtTglLahir;
    @BindView(R.id.txtAlamat)
    TextView txtAlamat;
    @BindView(R.id.editdatepicker)
    EditText editdatepicker;
    @BindView(R.id.editklinikpicker)
    EditText editklinikpicker;
    @BindView(R.id.editDokPicker)
    EditText editDokPicker;
    @BindView(R.id.btnRegistration)
    Button btnRegistration;
    public static final int REQUEST_CODE_KLINIK = 1;
    public static final int REQUEST_CODE_DOKTER = 2;
    private String kodeKlinik = null;
    private String namaKlinik = null;
    private String tgl_regis =  null;
    private String nidDokter = null;
    private String namaDokter = null;
    private DatabaseHandler db;
    NetworkStatus networkStatus;

    @OnClick(R.id.btnRegistration)
    public void editbtnRegistrationClick(View view) {
        if (networkStatus.isOnline(RegistrationActivity.this)) {
            if (editdatepicker.length() <= 0) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialogAlert = new DialogAlert();
                        dialogAlert.alertValidation(RegistrationActivity.this, "Peringatan", "Anda Belum Memilih Tanggal Periksa");
                    }
                });

            }
            else if (editklinikpicker.length() <= 0) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialogAlert = new DialogAlert();
                        dialogAlert.alertValidation(RegistrationActivity.this, "Peringatan", "Anda Belum Memilih Klinik Tujuan");
                    }
                });

            }
            else if (editDokPicker.length() <= 0) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialogAlert = new DialogAlert();
                        dialogAlert.alertValidation(RegistrationActivity.this, "Peringatan", "Anda Belum Memilih Dokter Klinik");
                    }
                });

            }
            else
            {
                new MaterialStyledDialog.Builder(RegistrationActivity.this)
                        .setTitle("Konfirmasi")
                        .setDescription("Apakah Anda Yakin Mendaftar Pada Tgl: "+editdatepicker.getText().toString().trim() +
                                ",Klinik:"+ editklinikpicker.getText().toString().trim()+", Dokter:"+editDokPicker.getText().toString().trim())
                        .setIcon(R.drawable.logo_bethesda)
                        .withIconAnimation(false)
                        .setCancelable(Boolean.FALSE)
                        .setNegativeText("Cancel")
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveText("Yes")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                RegistrationTask registrationTask = new RegistrationTask();
                                registrationTask.execute();
                            }
                        })
                        .show();

              //  RegistrationTask registrationTask = new RegistrationTask();
               // registrationTask.execute();
            }

        } else {
            Toast.makeText(RegistrationActivity.this, "Koneksi Internet Tidak Ditemukan,Mohon Coba Kembali", Toast.LENGTH_LONG).show();

        }


    }

    @OnClick(R.id.editdatepicker)
    public void editdatepickerClick(View view) {
        editdatepicker.setText("");
       // editklinikpicker.setText("");
        editDokPicker.setText("");
        String now_string = SharedData.getKey(RegistrationActivity.this, "currentDate");
        Date current_date = DateUtil.FormatStringToDate(now_string,"dd/MM/yyyy");
        Calendar cal_min = Calendar.getInstance();
        cal_min.setTime(current_date);
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                RegistrationActivity.this,
                cal_min.get(Calendar.YEAR),
                cal_min.get(Calendar.MONTH),
                cal_min.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setVersion(DatePickerDialog.Version.VERSION_2);
        dpd.setAccentColor(Color.rgb(41, 182, 246));
        dpd.setMinDate(cal_min);
        Calendar cal_max = Calendar.getInstance();
        cal_max.setTime(current_date);
        cal_max.add(Calendar.DATE, DateUtil.getMaxDaysRegis()-1);
        dpd.setMaxDate(cal_max);
        Locale local_indonesia = new Locale("id", "ID");
        dpd.setLocale(local_indonesia);
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    @OnClick(R.id.editklinikpicker)
    public void editklinikpickerClick(View view) {
        if (networkStatus.isOnline(RegistrationActivity.this)) {
            editklinikpicker.setText("");
            editDokPicker.setText("");
            if (editdatepicker.length() <= 0) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialogAlert = new DialogAlert();
                        dialogAlert.alertValidation(RegistrationActivity.this, "Peringatan", "Anda Belum Memilih Tanggal Periksa");
                    }
                });

            } else
            {
                Intent intent = new Intent(RegistrationActivity.this, KlinikPickerActivity.class);
                startActivityForResult(intent, REQUEST_CODE_KLINIK);
            }


        } else {
            Toast.makeText(RegistrationActivity.this, "Koneksi Internet Tidak Ditemukan Saat mengambil data Klinik,Mohon Coba Kembali", Toast.LENGTH_LONG).show();

        }

    }



    @OnClick(R.id.editDokPicker)
    public void editDokPickerClick(View view) {
        if (networkStatus.isOnline(RegistrationActivity.this)) {
            if (editdatepicker.length() <= 0) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialogAlert = new DialogAlert();
                        dialogAlert.alertValidation(RegistrationActivity.this, "Peringatan", "Anda Belum Memilih Tanggal Periksa");
                    }
                });

            }
            else if (editklinikpicker.length() <= 0) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialogAlert = new DialogAlert();
                        dialogAlert.alertValidation(RegistrationActivity.this, "Peringatan", "Anda Belum Memilih Klinik Tujuan");
                    }
                });

            }
            else if (kodeKlinik.length() <= 0 || kodeKlinik.equals(null)) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialogAlert = new DialogAlert();
                        dialogAlert.alertValidation(RegistrationActivity.this, "Peringatan", "Klinik tidak Ditemukan,mohon pilih ulang klinik");
                    }
                });

            } else {
                Intent intent = new Intent(RegistrationActivity.this, DokterPickerActivity.class);
                intent.putExtra("tgl_regis",tgl_regis);
                intent.putExtra("kodeKlinik", kodeKlinik);
                intent.putExtra("kodeDokter", "");
                startActivityForResult(intent, REQUEST_CODE_DOKTER);
            }
        } else {
            Toast.makeText(RegistrationActivity.this, "Koneksi Internet Tidak Ditemukan saat mengambil data dokter,Mohon Coba Kembali", Toast.LENGTH_LONG).show();

        }


    }

    private void doRegistration() {
        Registration registration = new Registration();
        RegistrationResult registrationResult = new RegistrationResult();
        registration.setKodeDokter(nidDokter);
        registration.setKodeKlinik(kodeKlinik);
        registration.setTglReg(tgl_regis);
        registration.setNoRM(SharedData.getKey(RegistrationActivity.this, "noRM"));
        try {
            registrationResult = RegistrationServices.postRegistration(registration);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (registrationResult.getResponse().equals("gagal")) {
            final String deskripsiresponse = registrationResult.getDeskripsiResponse();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    dialogAlert = new DialogAlert();
                    dialogAlert.alertValidation(RegistrationActivity.this, "Peringatan", deskripsiresponse);

                }
            });

        } else {
            final String deskripsiresponse = registrationResult.getDeskripsiResponse();
            db.addRegistrationToTable(registrationResult);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    dialogAlert = new DialogAlert();
                    dialogAlert.alertValidation(RegistrationActivity.this, "Sukses", deskripsiresponse);

                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
        networkStatus = new NetworkStatus();
        db = new DatabaseHandler(RegistrationActivity.this);
        initDataPasien();

    }

    private void initDataPasien() {
        txtNoRM.setText(SharedData.getKey(RegistrationActivity.this, "noRM"));
        txtNama.setText(SharedData.getKey(RegistrationActivity.this, "namaPasien"));
        txtAlamat.setText(SharedData.getKey(RegistrationActivity.this, "alamat"));
        txtTglLahir.setText(SharedData.getKey(RegistrationActivity.this, "tglLahir"));
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_KLINIK) {
            if (resultCode == RESULT_OK) {
                kodeKlinik = data.getStringExtra("kodeKlinik");
                namaKlinik = data.getStringExtra("namaKlinik");
                editklinikpicker.setText(namaKlinik);
            }
        }
        if (requestCode == REQUEST_CODE_DOKTER) {
            if (resultCode == RESULT_OK) {
                //Log.d("dokter",data.getStringExtra("nidDokter"));
                nidDokter = data.getStringExtra("nidDokter");
                namaDokter = data.getStringExtra("namaDokter");
                editDokPicker.setText(namaDokter);
            }
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date_selected =(monthOfYear+1)+"/"+ dayOfMonth+"/"+year;
        editdatepicker.setText(  DateUtil.changeFormatDate(date_selected,"MM/dd/yyyy","dd-MMM-yyyy"));
        tgl_regis =  DateUtil.changeFormatDate(date_selected,"MM/dd/yyyy","MM/dd/yyyy");
    }

    private class RegistrationTask extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(RegistrationActivity.this,
                    "Please Wait",
                    "Sedang Proses Registrasi");
        }

        @Override
        protected Void doInBackground(Void... params) {
            doRegistration();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            progressDialog.dismiss();
        }
    }

}
