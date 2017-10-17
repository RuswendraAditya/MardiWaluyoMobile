package bethesda.com.bethesdahospitalmobile.main.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import bethesda.com.bethesdahospitalmobile.R;
import bethesda.com.bethesdahospitalmobile.main.utility.AutoFitGridLayoutManager;
import bethesda.com.bethesdahospitalmobile.main.utility.DialogAlert;
import bethesda.com.bethesdahospitalmobile.main.main.model.Menu;
import bethesda.com.bethesdahospitalmobile.main.main.adapter.MenuAdapter;
import bethesda.com.bethesdahospitalmobile.main.registration.RegistrationActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainMenuActivity extends AppCompatActivity implements MenuAdapter.ItemListener {

    /*  @BindView(R.id.btnRegisOnline)
      Button btnRegisOnline;

      @OnClick(R.id.btnRegisOnline)
      public void btnRegisOnlineClick(View view) {
          Intent intent = new Intent(MainMenuActivity.this, RegistrationOnlineActivity.class);
          startActivity(intent);

      }*/
    @BindView(R.id.rvMenu)
    RecyclerView recyclerView;
    ArrayList<Menu> arrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        ButterKnife.bind(this);
        arrayList = new ArrayList<>();
        initMenu(arrayList);
        MenuAdapter adapter = new MenuAdapter(this, arrayList, this);
        recyclerView.setAdapter(adapter);
        AutoFitGridLayoutManager layoutManager = new AutoFitGridLayoutManager(this, 500);
        recyclerView.setLayoutManager(layoutManager);


    }

    private void initMenu(ArrayList<Menu> arrayList) {
        arrayList.add(new Menu(1, "PENDAFTARAN ONLINE", R.drawable.registration));
        arrayList.add(new Menu(2, "RIWAYAT KUNJUNGAN", R.drawable.stetoskop));
        arrayList.add(new Menu(3, "JADWAL DOKTER", R.drawable.doctor));
        arrayList.add(new Menu(4, "INFORMASI KAMAR", R.drawable.bed));
    }


    @Override
    public void onItemClick(Menu item) {
        // menu registration
        if (item.id == 1) {
            Intent intent = new Intent(MainMenuActivity.this, RegistrationActivity.class);
            startActivity(intent);
        } else {
            DialogAlert alert = new DialogAlert();
            alert.alertValidation(MainMenuActivity.this, "Info", "Mohon Maaf,menu masih dalam tahap pengembangan");
        }
    }
}
