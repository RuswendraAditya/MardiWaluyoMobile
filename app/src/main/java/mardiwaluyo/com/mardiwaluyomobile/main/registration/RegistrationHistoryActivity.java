package mardiwaluyo.com.mardiwaluyomobile.main.registration;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mardiwaluyo.com.mardiwaluyomobile.R;
import mardiwaluyo.com.mardiwaluyomobile.main.registration.adapter.RegistrationHistoryAdapter;
import mardiwaluyo.com.mardiwaluyomobile.main.registration.model.RegistrationResult;
import mardiwaluyo.com.mardiwaluyomobile.main.utility.DatabaseHandler;
import mardiwaluyo.com.mardiwaluyomobile.main.utility.DateUtil;
import mardiwaluyo.com.mardiwaluyomobile.main.utility.DialogAlert;
import mardiwaluyo.com.mardiwaluyomobile.main.utility.SharedData;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RegistrationHistoryActivity extends AppCompatActivity {
    private List<RegistrationResult> registrationList = new ArrayList<>();
    @BindView(R.id.rv_registration_history)
    RecyclerView rv_registration_history;
    @BindView(R.id.txtNotif)
    TextView txtNotif;
    private RegistrationHistoryAdapter mAdapter;
    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_history);
        ButterKnife.bind(this);
        db = new DatabaseHandler(this);
        try {
            deleteOldData();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (SharedData.getKey(RegistrationHistoryActivity.this, "noRM") == "") {
            DialogAlert dialogAlert = new DialogAlert();
            dialogAlert.alertValidation(RegistrationHistoryActivity.this, "Peringatan", "Maaf tidak bisa menemukan no RM");
        } else {
            registrationList = db.getRegisFromDBByNoRM(SharedData.getKey(RegistrationHistoryActivity.this, "noRM"));
            // registrationList = db.getRegisFromDB();
            if (registrationList.size() == 0) {
                txtNotif.setText("Tidak Ada Transaksi Pendaftaran Online");
                txtNotif.setAnimation(AnimationUtils.loadAnimation(this, R.anim.blink));
            } else if (registrationList.size() >= 1) {
                mAdapter = new RegistrationHistoryAdapter(RegistrationHistoryActivity.this, registrationList);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                rv_registration_history.setLayoutManager(mLayoutManager);
                rv_registration_history.setItemAnimator(new DefaultItemAnimator());
                rv_registration_history.setAdapter(mAdapter);
            }

        }


    }

    private void deleteOldData() {
        String date = SharedData.getKey(RegistrationHistoryActivity.this, "currentDate");
        // Log.d("data old",DateUtil.changeFormatDate(date,"dd/MM/yyyy","yyyy-MM-dd"));
        db.deleteRegisNotToday(DateUtil.changeFormatDate(date, "dd/MM/yyyy", "yyyy-MM-dd"));

    }
}
