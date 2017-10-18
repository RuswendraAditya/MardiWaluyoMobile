package bethesda.com.bethesdahospitalmobile.main.registration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;

import bethesda.com.bethesdahospitalmobile.R;
import bethesda.com.bethesdahospitalmobile.main.registration.model.Registration;
import bethesda.com.bethesdahospitalmobile.main.registration.model.RegistrationResult;
import bethesda.com.bethesdahospitalmobile.main.registration.service.RegistrationServices;
import bethesda.com.bethesdahospitalmobile.main.utility.DialogAlert;
import bethesda.com.bethesdahospitalmobile.main.utility.SharedData;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegistrationActivity extends AppCompatActivity {
    private DialogAlert dialogAlert;
    @BindView(R.id.txtNoRM)
    TextView txtNoRM;
    @BindView(R.id.txtNama)
    TextView txtNama;
    @BindView(R.id.txtTglLahir)
    TextView txtTglLahir;
    @BindView(R.id.txtAlamat)
    TextView txtAlamat;
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
    private String nidDokter = null;
    private String namaDokter = null;

    @OnClick(R.id.btnRegistration)
    public void editbtnRegistrationClick(View view) {
        RegistrationTask registrationTask = new RegistrationTask();
        registrationTask.execute();

    }

    @OnClick(R.id.editklinikpicker)
    public void editklinikpickerClick(View view) {
        editklinikpicker.setText("");
        editDokPicker.setText("");
        Intent intent = new Intent(RegistrationActivity.this, KlinikPickerActivity.class);
        startActivityForResult(intent, REQUEST_CODE_KLINIK);

    }

    @OnClick(R.id.editDokPicker)
    public void editDokPickerrClick(View view) {
        if (editklinikpicker.length() <= 0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dialogAlert = new DialogAlert();
                    dialogAlert.alertValidation(RegistrationActivity.this, "Warning", "Anda Belum Memilih Klinik");
                }
            });

        } else if (kodeKlinik.length() <= 0 || kodeKlinik.equals(null)) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dialogAlert = new DialogAlert();
                    dialogAlert.alertValidation(RegistrationActivity.this, "Warning", "Klinik tidak Ditemukan,mohon pilih ulang klinik");
                }
            });

        } else {
            Intent intent = new Intent(RegistrationActivity.this, DokterPickerActivity.class);
            intent.putExtra("kodeKlinik", kodeKlinik);
            startActivityForResult(intent, REQUEST_CODE_DOKTER);
        }

    }

    private void doRegistration() {
        Registration registration = new Registration();
        RegistrationResult registrationResult = new RegistrationResult();
        registration.setKodeDokter(nidDokter);
        registration.setKodeKlinik(kodeKlinik);
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
                    dialogAlert.alertValidation(RegistrationActivity.this, "Warning", deskripsiresponse);

                }
            });

        } else {
            final String deskripsiresponse = registrationResult.getDeskripsiResponse();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    dialogAlert = new DialogAlert();
                    dialogAlert.alertValidation(RegistrationActivity.this, "Warning", deskripsiresponse);

                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
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
