package bethesda.com.bethesdahospitalmobile.main.registration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import bethesda.com.bethesdahospitalmobile.R;
import bethesda.com.bethesdahospitalmobile.main.utility.DatabaseHandler;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class KlinikPickerActivity extends AppCompatActivity {
    @BindView(R.id.listKlinik)
    ListView listKlinik;
    @BindView(R.id.editKlinikSearch)
    EditText editKlinikSearch;
    private ArrayAdapter<String> adapter;
    private LinkedHashMap<String, String> klinikMap = new LinkedHashMap<>();
    private DatabaseHandler db;

    @OnItemClick(R.id.listKlinik)
    public void onItemClick(AdapterView<?> parent,
                            int position) {
        String klinik_kode = (String) klinikMap.keySet().toArray()[position];
        String klinik_name = (String) klinikMap.values().toArray()[position];
        Intent intent = new Intent();
        intent.putExtra("kodeKlinik", klinik_kode);
        intent.putExtra("namaKlinik", klinik_name);

        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_klinik_picker);
        ButterKnife.bind(this);
        db = new DatabaseHandler(KlinikPickerActivity.this);
        klinikMap = db.getKlinikFromDB();
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1, new ArrayList<String>(klinikMap.values()));
        listKlinik.setAdapter(adapter);

        editKlinikSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                KlinikPickerActivity.this.adapter.getFilter().filter(s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                KlinikPickerActivity.this.adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


}
