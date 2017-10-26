package bethesda.com.bethesdahospitalmobile.main.registration;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import bethesda.com.bethesdahospitalmobile.R;
import bethesda.com.bethesdahospitalmobile.main.registration.adapter.RegistrationHistoryAdapter;
import bethesda.com.bethesdahospitalmobile.main.registration.model.RegistrationResult;
import bethesda.com.bethesdahospitalmobile.main.utility.DatabaseHandler;
import bethesda.com.bethesdahospitalmobile.main.utility.SharedData;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RegistrationHistoryActivity extends AppCompatActivity {
    private List<RegistrationResult> registrationList = new ArrayList<>();
    @BindView(R.id.rv_registration_history)
    RecyclerView rv_registration_history;
    private RegistrationHistoryAdapter mAdapter;
    private DatabaseHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_history);
        ButterKnife.bind(this);
        db = new DatabaseHandler(this);
        registrationList= db.getRegisFromDBByNoRM(SharedData.getKey(RegistrationHistoryActivity.this, "noRM"));
        mAdapter = new RegistrationHistoryAdapter(RegistrationHistoryActivity.this,registrationList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv_registration_history.setLayoutManager(mLayoutManager);
        rv_registration_history.setItemAnimator(new DefaultItemAnimator());
        rv_registration_history.setAdapter(mAdapter);
    }
}
