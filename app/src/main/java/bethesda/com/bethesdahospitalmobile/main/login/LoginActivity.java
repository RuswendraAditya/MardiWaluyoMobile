package bethesda.com.bethesdahospitalmobile.main.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

import bethesda.com.bethesdahospitalmobile.R;
import bethesda.com.bethesdahospitalmobile.main.login.model.Login;
import bethesda.com.bethesdahospitalmobile.main.login.service.LoginServices;

public class LoginActivity extends AppCompatActivity {
    private Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setPropertiesLoginActivity();
        setListenerClickBtnLogin(btn_login);
    }

    private void setPropertiesLoginActivity() {
        btn_login = (Button) findViewById(R.id.btn_login);
    }

    private void setListenerClickBtnLogin(Button btn_login) {
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin();
                //          Intent intent = new Intent(LoginActivity.this, MainMenuActivity.class);
                //       startActivity(intent);

            }
        });
    }


    private void doLogin() {
        try {
            Login login = LoginServices.getLoginByNoRm("00590724");
            String noRM = login.getNoRM();
            String response = login.getResponse();
            Log.d("noRM", noRM);
            Log.d("noRM", response);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}