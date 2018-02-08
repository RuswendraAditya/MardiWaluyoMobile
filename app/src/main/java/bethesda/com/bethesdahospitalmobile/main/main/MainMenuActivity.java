package bethesda.com.bethesdahospitalmobile.main.main;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.widget.TextView;
import android.widget.Toast;

import com.robohorse.gpversionchecker.GPVersionChecker;

import java.io.IOException;
import java.util.ArrayList;

import bethesda.com.bethesdahospitalmobile.R;
import bethesda.com.bethesdahospitalmobile.main.info.JamBesukActivity;
import bethesda.com.bethesdahospitalmobile.main.login.LoginActivity;
import bethesda.com.bethesdahospitalmobile.main.main.adapter.MenuAdapter;
import bethesda.com.bethesdahospitalmobile.main.main.model.Menu;
import bethesda.com.bethesdahospitalmobile.main.registration.RegistrationActivity;
import bethesda.com.bethesdahospitalmobile.main.registration.RegistrationHistoryActivity;
import bethesda.com.bethesdahospitalmobile.main.room.EmptyRoomActivity;
import bethesda.com.bethesdahospitalmobile.main.schedule.ScheduleActivity;
import bethesda.com.bethesdahospitalmobile.main.utility.ApkUtil;
import bethesda.com.bethesdahospitalmobile.main.utility.AutoFitGridLayoutManager;
import bethesda.com.bethesdahospitalmobile.main.utility.DateUtil;
import bethesda.com.bethesdahospitalmobile.main.utility.DialogAlert;
import bethesda.com.bethesdahospitalmobile.main.utility.NetworkStatus;
import bethesda.com.bethesdahospitalmobile.main.utility.SharedData;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainMenuActivity extends AppCompatActivity implements MenuAdapter.ItemListener {

    @BindView(R.id.rvMenu)
    RecyclerView recyclerView;
    ArrayList<Menu> arrayList;
    private NetworkStatus networkStatus;
    @BindView(R.id.txtVersionMainMenu)
    TextView txtVersionMainMenu;
    ApkUtil apkUtil;
    Integer width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        ButterKnife.bind(this);
        width = 0;
        networkStatus = new NetworkStatus();
        arrayList = new ArrayList<>();
        apkUtil = new ApkUtil();
        txtVersionMainMenu.setText("Version: " + apkUtil.getAppVersion(MainMenuActivity.this));
        initMenu(arrayList);
        MenuAdapter adapter = new MenuAdapter(this, arrayList, this);
        recyclerView.setAdapter(adapter);
        width = getWidth();
        AutoFitGridLayoutManager layoutManager = null;
        if (width < 800) {
            layoutManager = new AutoFitGridLayoutManager(this, 300);
        }
        if (width >= 800) {
            layoutManager = new AutoFitGridLayoutManager(this, 400);
        }
        recyclerView.setLayoutManager(layoutManager);
        if (SharedData.getKey(MainMenuActivity.this, "currentDate") != null) {
            String date_now = SharedData.getKey(MainMenuActivity.this, "currentDate");
        } else {
            try {
                DateUtil.getCurrentDateFromServer(MainMenuActivity.this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        new GPVersionChecker.Builder(this).create();

    }

    private void initMenu(ArrayList<Menu> arrayList) {
        arrayList.add(new Menu(1, "PENDAFTARAN ONLINE", R.drawable.registration));
        arrayList.add(new Menu(2, "RIWAYAT PENDAFTARAN ONLINE", R.drawable.stetoskop));
        arrayList.add(new Menu(3, "JADWAL DOKTER", R.drawable.doctor));
        arrayList.add(new Menu(4, "INFORMASI KAMAR", R.drawable.bed));
        arrayList.add(new Menu(5, "INFO JAM BESUK", R.drawable.clock));
    }

    private Integer getWidth() {
        Integer lebar;
        try {
            Display disp = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            disp.getSize(size);
            lebar = size.x;
        } catch (Exception e) {
            lebar = 0;
        }
        return lebar;
    }

    @Override
    public void onItemClick(Menu item) {
        Intent intent;
        // menu registration
        if (item.id == 1) {
            if (SharedData.getKey(MainMenuActivity.this, "noRM") == "") {
                SharedData.setKey(MainMenuActivity.this, "source", "registration");
                intent = new Intent(MainMenuActivity.this, LoginActivity.class);
                startActivity(intent);
            } else {
                intent = new Intent(MainMenuActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }

        } else if (item.id == 2) {
            if (SharedData.getKey(MainMenuActivity.this, "noRM") == "") {
                SharedData.setKey(MainMenuActivity.this, "source", "history");
                intent = new Intent(MainMenuActivity.this, LoginActivity.class);
                startActivity(intent);
            } else {
                intent = new Intent(MainMenuActivity.this, RegistrationHistoryActivity.class);
                startActivity(intent);
            }


        } else if (item.id == 3) {
            if (networkStatus.isOnline(MainMenuActivity.this)) {
                intent = new Intent(MainMenuActivity.this, ScheduleActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(MainMenuActivity.this, "Koneksi Internet Tidak Ditemukan untuk mengakses menu ini", Toast.LENGTH_LONG).show();
                // finish();
            }

        } else if (item.id == 4) {
            if (networkStatus.isOnline(MainMenuActivity.this)) {
                intent = new Intent(MainMenuActivity.this, EmptyRoomActivity.class);
                //   intent = new Intent(MainMenuActivity.this, CalendarPicker.class);
                startActivity(intent);
            } else {
                Toast.makeText(MainMenuActivity.this, "Koneksi Internet Tidak Ditemukan untuk mengakses menu ini", Toast.LENGTH_LONG).show();
                // finish();
            }

        } else if (item.id == 5) {
            intent = new Intent(MainMenuActivity.this, JamBesukActivity.class);
            //   intent = new Intent(MainMenuActivity.this, CalendarPicker.class);
            startActivity(intent);
        } else {
            DialogAlert alert = new DialogAlert();
            alert.alertValidation(MainMenuActivity.this, "Info", "Mohon Maaf,menu masih dalam tahap pengembangan");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        new GPVersionChecker.Builder(this).create();
    }
}
