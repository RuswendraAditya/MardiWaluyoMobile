package mardiwaluyo.com.mardiwaluyomobile.main.schedule;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mardiwaluyo.com.mardiwaluyomobile.R;
import mardiwaluyo.com.mardiwaluyomobile.main.registration.KlinikPickerActivity;
import mardiwaluyo.com.mardiwaluyomobile.main.schedule.adapter.KlinikDokterScheduleAdapter;
import mardiwaluyo.com.mardiwaluyomobile.main.schedule.model.DokterKlinikSchedule;
import mardiwaluyo.com.mardiwaluyomobile.main.schedule.service.KlinikScheduleService;
import mardiwaluyo.com.mardiwaluyomobile.main.utility.DialogAlert;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;


public class KlinikScheduleFragment extends Fragment {

    @BindView(R.id.editTextKlinikSchedule)
    EditText editTextKlinikSchedule;
    @BindView(R.id.rvKlinikDokter)
    RecyclerView rvKlinikDokter;
    @BindView(R.id.btnCariJadwalKlinik)
    Button btnCariJadwalKlinik;
    String kodeKlinik, namaKlinik;
    List<DokterKlinikSchedule> listJadwal = new ArrayList<>();

    @OnClick(R.id.editTextKlinikSchedule)

    public void editTextKlinikScheduleClick(View view) {
        editTextKlinikSchedule.setText("");
        Intent intent = new Intent(getActivity(), KlinikPickerActivity.class);
        intent.putExtra("kodeDokter", "");
        this.startActivityForResult(intent, 1);

    }

    @OnClick(R.id.btnCariJadwalKlinik)

    public void btnCariJadwalKlinikClick(View view) {
        if (editTextKlinikSchedule.getText().toString().compareToIgnoreCase("") != 0) {
            KlinikScheduleTask klinikScheduleTask = new KlinikScheduleTask();
            klinikScheduleTask.execute();
        } else {
            DialogAlert dialogAlert = new DialogAlert();
            dialogAlert.alertValidation(getActivity(), "Peringatan", "Anda Belum Memilih Klinik");
        }

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                kodeKlinik = data.getStringExtra("kodeKlinik");
                namaKlinik = data.getStringExtra("namaKlinik");
                editTextKlinikSchedule.setText(namaKlinik);
            }
        }
    }

    public static KlinikScheduleFragment newInstance() {

        Bundle args = new Bundle();
        KlinikScheduleFragment fragment = new KlinikScheduleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_klinik_schedule, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    private void getKlinikSchedule() {
        try {
            listJadwal.clear();
            listJadwal = KlinikScheduleService.getJadwalDokterKlinik(kodeKlinik);
            final KlinikDokterScheduleAdapter klinikDokterScheduleAdapter = new KlinikDokterScheduleAdapter(getActivity(), listJadwal);
            klinikDokterScheduleAdapter.notifyDataSetChanged();
            if (listJadwal.size() > 0) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                        rvKlinikDokter.setLayoutManager(mLayoutManager);
                        rvKlinikDokter.setItemAnimator(new DefaultItemAnimator());
                        rvKlinikDokter.setAdapter(klinikDokterScheduleAdapter);

                    }
                });
            } else {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                        rvKlinikDokter.setLayoutManager(mLayoutManager);
                        rvKlinikDokter.setItemAnimator(new DefaultItemAnimator());
                        rvKlinikDokter.setAdapter(klinikDokterScheduleAdapter);

                        DialogAlert dialogAlert = new DialogAlert();
                        dialogAlert.alertValidation(getActivity(), "Peringatan", "Jadwal Tidak Ditemukan");
                    }
                });

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class KlinikScheduleTask extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getActivity(),
                    "Please Wait",
                    "Mengambil Jadwal Dokter");
        }

        @Override
        protected Void doInBackground(Void... params) {
            getKlinikSchedule();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            progressDialog.dismiss();
        }
    }

}
