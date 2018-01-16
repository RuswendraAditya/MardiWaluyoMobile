package bethesda.com.bethesdahospitalmobile.main.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import bethesda.com.bethesdahospitalmobile.R;
import bethesda.com.bethesdahospitalmobile.main.login.model.Login;
import bethesda.com.bethesdahospitalmobile.main.login.service.LoginServices;
import bethesda.com.bethesdahospitalmobile.main.registration.RegistrationActivity;
import bethesda.com.bethesdahospitalmobile.main.registration.RegistrationHistoryActivity;
import bethesda.com.bethesdahospitalmobile.main.utility.DatabaseHandler;
import bethesda.com.bethesdahospitalmobile.main.utility.DateUtil;
import bethesda.com.bethesdahospitalmobile.main.utility.DialogAlert;
import bethesda.com.bethesdahospitalmobile.main.utility.NetworkStatus;
import bethesda.com.bethesdahospitalmobile.main.utility.SharedData;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    private DialogAlert dialogAlert;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.editNoRm)
    EditText editNoRm;
    @BindView(R.id.editPassword)
    EditText editPassword;
    private DatabaseHandler db;
    private NetworkStatus networkStatus;

    @OnClick(R.id.btnLogin)
    public void loginClick(View view) {
        if (isValidateLogin()) {
            if (networkStatus.isOnline(LoginActivity.this)) {
                LoginTask loginTask = new LoginTask();
                loginTask.execute();
            } else {
                Toast.makeText(LoginActivity.this, "Koneksi Internet Tidak Ditemukan saat login, mohon cek kembali", Toast.LENGTH_LONG).show();
            }

        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        networkStatus= new NetworkStatus();
        db = new DatabaseHandler(LoginActivity.this);

    }

    private void doLogin() {
        dialogAlert = new DialogAlert();
        try {
            Login login = LoginServices.getLoginByNoRmServices(editNoRm.getText().toString());
            String noRM = login.getNoRM();
            String tgl_lahir ="";
            try {
                 tgl_lahir  = DateUtil.changeFormatDate(login.getDtglLahir(), "dd/MM/yyyy", "ddMMyyyy");
            }
            catch(Exception e){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialogAlert = new DialogAlert();
                        dialogAlert.alertValidation(LoginActivity.this, "Peringatan",
                                "Format Tanggal yang anda masukan tidak sesuai");
                    }
                });
            }

            String source = SharedData.getKey(LoginActivity.this, "source");
            SharedData.setKey(LoginActivity.this, "noRM", login.getNoRM());
            SharedData.setKey(LoginActivity.this, "namaPasien", login.getNamaPasien());
            SharedData.setKey(LoginActivity.this, "tglLahir", login.getDtglLahir());
            SharedData.setKey(LoginActivity.this, "alamat", login.getAlamat());
            final String response = login.getResponse();
            final String desk_response = login.getDeskripsiResponse();
            if (response.equals("ok")) {
                if (editNoRm.getText().toString().equals(noRM) && editPassword.getText().toString().equals(tgl_lahir)) {
                    if (source.equalsIgnoreCase("registration")) {
                        Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                        startActivity(intent);
                    }
                    if (source.equalsIgnoreCase("history")) {
                        Intent intent = new Intent(LoginActivity.this, RegistrationHistoryActivity.class);
                        startActivity(intent);
                    }
                    finish();
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dialogAlert = new DialogAlert();
                            dialogAlert.alertValidation(LoginActivity.this, "Peringatan",
                                    "Login Gagal,mohon cek kembali No RM dan Tanggal lahir anda");
                        }
                    });

                }
            }
            if (response.equals("gagal")) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialogAlert = new DialogAlert();
                        dialogAlert.alertValidation(LoginActivity.this, "Peringatan",
                                desk_response);

                    }
                });
            }
        } catch (final Exception e) {
            e.printStackTrace();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dialogAlert.alertValidation(LoginActivity.this, "Peringatan",
                            e.getMessage());
                }
            });

        }


    }

    private boolean isValidateLogin() {
        if (editNoRm.getText().toString().length() == 0) {
            editNoRm.requestFocus();
            editNoRm.setError("Silahkan Masukkan no RM");
            return false;
        }
        if (editPassword.getText().toString().length() == 0) {
            editPassword.requestFocus();
            editPassword.setError("Masukan Tanggal Lahir");
            return false;
        }

        return true;
    }

    private class LoginTask extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(LoginActivity.this,
                    "Please Wait",
                    "Process Login");
        }

        @Override
        protected Void doInBackground(Void... params) {
            doLogin();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            progressDialog.dismiss();
        }
    }


}