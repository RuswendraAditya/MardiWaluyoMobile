package bethesda.com.bethesdahospitalmobile.main.registration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;

import bethesda.com.bethesdahospitalmobile.R;
import bethesda.com.bethesdahospitalmobile.main.Utility.DialogAlert;

public class RegistrationOnlineActivity extends AppCompatActivity {
    private EditText editklinikpicker;
    private EditText editDokPicker;
    private DialogAlert dialogAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_online);
        initDataComponent();
        setEditklinikPickerListener();
        setEditDokPickerListener();
    }

    private void initDataComponent() {
        editklinikpicker = (EditText) findViewById(R.id.editklinikpicker);
        editDokPicker = (EditText) findViewById(R.id.editDokPicker);
    }

    private void setEditklinikPickerListener() {
        editklinikpicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationOnlineActivity.this, KlinikPickerActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setEditDokPickerListener() {
        editDokPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editklinikpicker.length() <= 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dialogAlert = new DialogAlert();
                            dialogAlert.alertValidation(RegistrationOnlineActivity.this, "Warning", "Anda Belum Memilih Klinik");
                        }
                    });

                } else {
                    Intent intent = new Intent(RegistrationOnlineActivity.this, KlinikPickerActivity.class);
                    startActivity(intent);
                }
            }
        });
    }




}
