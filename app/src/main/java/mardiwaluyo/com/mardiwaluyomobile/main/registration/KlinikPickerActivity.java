package mardiwaluyo.com.mardiwaluyomobile.main.registration;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import mardiwaluyo.com.mardiwaluyomobile.R;
import mardiwaluyo.com.mardiwaluyomobile.main.utility.DatabaseHandler;
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
    private LinkedHashMap<String, String> klinikMapTemp = new LinkedHashMap<>();
    private DatabaseHandler db;
    int textlength = 0;

    @OnItemClick(R.id.listKlinik)
    public void onItemClick(AdapterView<?> parent,
                            int position) {
        String klinik_kode = null;
        String klinik_name = null;
        if (klinikMap.size() > 0) {
            klinik_kode = (String) klinikMap.keySet().toArray()[position];
            klinik_name = (String) klinikMap.values().toArray()[position];
        }
        if (klinikMapTemp.size() > 0) {
            klinik_kode = (String) klinikMapTemp.keySet().toArray()[position];
            klinik_name = (String) klinikMapTemp.values().toArray()[position];
        }
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
        // Create a List from String Array elements
        List<String> klinik_list = new ArrayList<String>(klinikMap.values());

        // Create an ArrayAdapter from List
        adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1,  klinik_list){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                View view = super.getView(position, convertView, parent);
                TextView tv = (TextView) view.findViewById(android.R.id.text1);
                tv.setTextColor(Color.BLACK);
                return view;
            }
        };
      /*  adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1, new ArrayList<String>(klinikMap.values()));*/
        listKlinik.setAdapter(adapter);


        editKlinikSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // KlinikPickerActivity.this.adapter.getFilter().filter(s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                klinikMapTemp.clear();
                textlength = editKlinikSearch.getText().length();
                for (int i = 0; i < klinikMap.size(); i++) {
                    String value = (new ArrayList<String>(klinikMap.values())).get(i);
                    String key = (new ArrayList<String>(klinikMap.keySet())).get(i);
                    if (textlength <= value.length()) {
                        if (value.toLowerCase().trim().contains(
                                editKlinikSearch.getText().toString().toLowerCase().trim())) {
                            klinikMapTemp.put(key, value);
                        }
                    }
                }
                List<String> klinik_list = new ArrayList<String>( klinikMapTemp.values());

                // Create an ArrayAdapter from List
                adapter = new ArrayAdapter<String>
                        (getApplicationContext(), android.R.layout.simple_list_item_1,  klinik_list){
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent){
                        View view = super.getView(position, convertView, parent);
                        TextView tv = (TextView) view.findViewById(android.R.id.text1);
                        tv.setTextColor(Color.BLACK);
                        return view;
                    }
                };
            // adapter = new ArrayAdapter<String>(getApplicationContext(),
              //          android.R.layout.simple_list_item_1,
               //         android.R.id.text1, new ArrayList<String>(klinikMapTemp.values()));
                // Create an ArrayAdapter from List

                listKlinik.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


}
