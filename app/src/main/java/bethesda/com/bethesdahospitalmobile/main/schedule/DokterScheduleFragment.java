package bethesda.com.bethesdahospitalmobile.main.schedule;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import bethesda.com.bethesdahospitalmobile.R;
import bethesda.com.bethesdahospitalmobile.main.registration.DokterPickerActivity;
import bethesda.com.bethesdahospitalmobile.main.schedule.adapter.DokterScheduleAdapter;
import bethesda.com.bethesdahospitalmobile.main.schedule.model.DokterKlinikSchedule;
import bethesda.com.bethesdahospitalmobile.main.schedule.service.DokterScheduleService;
import bethesda.com.bethesdahospitalmobile.main.utility.DialogAlert;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

public class DokterScheduleFragment extends Fragment {
    @BindView(R.id.editTextDokterSchedule)
    EditText editTextDokterSchedule;
    String kode_dokter;
    String nama_dokter;
    @BindView(R.id.btnCariJadwalDokter)
    Button btnCariJadwalDokter;
    List<DokterKlinikSchedule> listJadwal = new ArrayList<>();
    @BindView(R.id.rvJadwalDokter)
    RecyclerView rvJadwalDokter;

    @OnClick(R.id.editTextDokterSchedule)

    public void editTextDokterScheduleClick(View view) {
        editTextDokterSchedule.setText("");
        Intent intent = new Intent(getActivity(), DokterPickerActivity.class);
        intent.putExtra("kodeKlinik", "");
        intent.putExtra("kodeDokter", "dummy_dokter");
        this.startActivityForResult(intent, 2);

    }

    @OnClick(R.id.btnCariJadwalDokter)
    public void btnCariJadwalDokterClick(View view) {
        DokterSchduleTask dokterSchduleTask = new DokterSchduleTask();
        dokterSchduleTask.execute();
    }


    public DokterScheduleFragment() {
        // Required empty public constructor
    }

    public static DokterScheduleFragment newInstance() {

        Bundle args = new Bundle();
        DokterScheduleFragment fragment = new DokterScheduleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                kode_dokter = data.getStringExtra("nidDokter");
                nama_dokter = data.getStringExtra("namaDokter");
                editTextDokterSchedule.setText(nama_dokter);
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    private void getJadwalDokter() {
        try {
            listJadwal.clear();
            listJadwal = DokterScheduleService.getJadwalDokterByNid(kode_dokter);
            if (listJadwal.size() > 0) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        DokterScheduleAdapter dokterScheduleAdapter = new DokterScheduleAdapter(getActivity(), listJadwal);
                        dokterScheduleAdapter.notifyDataSetChanged();
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                        rvJadwalDokter.setLayoutManager(mLayoutManager);
                        rvJadwalDokter.setItemAnimator(new DefaultItemAnimator());
                        rvJadwalDokter.setAdapter(dokterScheduleAdapter);

                    }
                });
            } else {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        DialogAlert dialogAlert = new DialogAlert();
                        dialogAlert.alertValidation(getActivity(), "Peringatan", "Jadwal Tidak Ditemukan");
                    }
                });

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dokter_schedule, container, false);
        ButterKnife.bind(this, view);
        return view;

    }

    private class DokterSchduleTask extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getActivity(),
                    "Please Wait",
                    "Mengambil Jadwal Dokter");
        }

        @Override
        protected Void doInBackground(Void... params) {
            getJadwalDokter();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            progressDialog.dismiss();
        }
    }


}
