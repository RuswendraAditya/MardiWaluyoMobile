package bethesda.com.bethesdahospitalmobile.main.registration;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

import bethesda.com.bethesdahospitalmobile.R;

public class KlinikPickerActivity extends AppCompatActivity {
    private ListView listKlinik;
    private ArrayAdapter<String> adapter;
    private EditText editKlinikSearch;
    private String klinikList[];
    ArrayList<HashMap<String, String>> productList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_klinik_picker);

        klinikList = initDataKlinik();
        listKlinik = (ListView) findViewById(R.id.listKlinik);
        editKlinikSearch = (EditText) findViewById(R.id.editKlinikSearch);

        // Adding items to listview
        adapter = new ArrayAdapter<String>(this, R.layout.klinik_item_list, R.id.klinik_name,  klinikList);
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

    private String[] initDataKlinik()
    {
        String klinik[] = {"Paru","Paru Sore","Mata","Gigi","Kulit Kelamin","Paru","Paru Sore"};
        return klinik;
    }
}
