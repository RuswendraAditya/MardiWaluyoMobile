package bethesda.com.bethesdahospitalmobile.main.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import bethesda.com.bethesdahospitalmobile.R;
import bethesda.com.bethesdahospitalmobile.main.registration.RegistrationOnlineActivity;

public class MainMenuActivity extends AppCompatActivity {
    private Button btnRegisOnline;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        initDataComponent();
        setBtnRegisOnlineListener();
    }

    private void initDataComponent()
    {
        btnRegisOnline = (Button)findViewById(R.id.btnRegisOnline);
    }
    private void setBtnRegisOnlineListener()
    {
        btnRegisOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this,RegistrationOnlineActivity.class);
                startActivity(intent);

            }
        });
    }
}
