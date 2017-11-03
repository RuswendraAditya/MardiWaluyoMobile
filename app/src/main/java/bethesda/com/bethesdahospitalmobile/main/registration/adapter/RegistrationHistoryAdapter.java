package bethesda.com.bethesdahospitalmobile.main.registration.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import bethesda.com.bethesdahospitalmobile.R;
import bethesda.com.bethesdahospitalmobile.main.registration.model.RegistrationResult;
import bethesda.com.bethesdahospitalmobile.main.utility.CircularTextView;
import bethesda.com.bethesdahospitalmobile.main.utility.DateUtil;

/**
 * Created by Wendra on 10/20/2017.
 */

public class RegistrationHistoryAdapter extends RecyclerView.Adapter<RegistrationHistoryAdapter.CustomViewHolder> {
    private List<RegistrationResult> registrationResultList;
    private Context mContext;

    public RegistrationHistoryAdapter(Context context, List<RegistrationResult> feedItemList) {
        this.registrationResultList = feedItemList;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.registration_history_list, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        RegistrationResult registrationResult = registrationResultList.get(i);
        customViewHolder.txtAntrianHistory.setText(registrationResult.getNoUrutdokter());
        customViewHolder.txtAntrianHistory.setStrokeWidth(1);
        customViewHolder.txtAntrianHistory.setStrokeColor("#87CEEB");
        customViewHolder.txtAntrianHistory.setSolidColor("#87CEEB");
        customViewHolder.txtKlinikHistory.setText("Klinik: " + registrationResult.getNamaKlinik());
        customViewHolder.txtNamaPasienHistory.setText("Nama: " + registrationResult.getNamaPasien());
        customViewHolder.txtTanggalHistory.setText("Tgl: " + DateUtil.changeFormatDate(registrationResult.getTglReg(),"dd/MM/yyyy","dd-MMM-yyyy"));
        customViewHolder.txtDokterHistory.setText("Dokter: " + registrationResult.getNamaDokter());

    }

    @Override
    public int getItemCount() {
        return (null != registrationResultList ? registrationResultList.size() : 0);
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView txtKlinikHistory;
        protected TextView txtTanggalHistory;
        protected TextView txtNamaPasienHistory;
        protected CircularTextView txtAntrianHistory;
        protected TextView txtDokterHistory;

        public CustomViewHolder(View view) {
            super(view);
            this.txtAntrianHistory = (CircularTextView) view.findViewById(R.id.txtAntrianHistory);
            this.txtTanggalHistory = (TextView) view.findViewById(R.id.txtTanggalHistory);
            this.txtKlinikHistory = (TextView) view.findViewById(R.id.txtKlinikHistory);
            this.txtNamaPasienHistory = (TextView) view.findViewById(R.id.txtNamaPasienHistory);
            this.txtDokterHistory = (TextView) view.findViewById(R.id.txtDokterHistory);
        }
    }
}
