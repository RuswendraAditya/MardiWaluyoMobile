package bethesda.com.bethesdahospitalmobile.main.schedule.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import bethesda.com.bethesdahospitalmobile.R;
import bethesda.com.bethesdahospitalmobile.main.schedule.model.DokterKlinikSchedule;

/**
 * Created by Wendra on 10/20/2017.
 */

public class KlinikDokterScheduleAdapter extends RecyclerView.Adapter<KlinikDokterScheduleAdapter.CustomViewHolder> {
    private List<DokterKlinikSchedule> dokterKlinikSchedules;
    private Context mContext;

    public KlinikDokterScheduleAdapter(Context context, List<DokterKlinikSchedule> feedItemList) {
        this.dokterKlinikSchedules = feedItemList;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.klinik_schedule_list, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        DokterKlinikSchedule dokterKlinikSchedule =dokterKlinikSchedules.get(i);
        customViewHolder.txtDokterJadwalKlinik.setText("Dokter: "+dokterKlinikSchedule.getNamaDokter());
        customViewHolder.txtHariKlinik.setText("Hari: "+dokterKlinikSchedule.getHari());
        customViewHolder.txtJamKlinik.setText("Jam: "+dokterKlinikSchedule.getJam_dari() + " - " + dokterKlinikSchedule.getJam_selesai());
    }

    @Override
    public int getItemCount() {
        return (null != dokterKlinikSchedules ? dokterKlinikSchedules.size() : 0);
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView txtDokterJadwalKlinik;
        protected TextView txtHariKlinik;
        protected TextView txtJamKlinik;


        public CustomViewHolder(View view) {
            super(view);
            this.txtDokterJadwalKlinik = (TextView) view.findViewById(R.id.txtDokterJadwalKlinik);
            this.txtHariKlinik = (TextView) view.findViewById(R.id.txtHariKlinik);
            this.txtJamKlinik = (TextView) view.findViewById(R.id.txtJamKlinik);

        }
    }
}
