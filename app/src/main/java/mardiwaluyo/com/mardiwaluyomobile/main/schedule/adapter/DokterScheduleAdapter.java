package mardiwaluyo.com.mardiwaluyomobile.main.schedule.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import mardiwaluyo.com.mardiwaluyomobile.R;
import mardiwaluyo.com.mardiwaluyomobile.main.schedule.model.DokterKlinikSchedule;

/**
 * Created by Wendra on 10/20/2017.
 */

public class DokterScheduleAdapter extends RecyclerView.Adapter<DokterScheduleAdapter.CustomViewHolder> {
    private List<DokterKlinikSchedule> dokterKlinikSchedules;
    private Context mContext;

    public DokterScheduleAdapter(Context context, List<DokterKlinikSchedule> feedItemList) {
        this.dokterKlinikSchedules = feedItemList;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dokter_schedule_list, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        DokterKlinikSchedule dokterKlinikSchedule = dokterKlinikSchedules.get(i);
//        customViewHolder.txtDokterJadwal.setText("");
        customViewHolder.txtKlinikDokter.setText("Klinik: " + dokterKlinikSchedule.getNamaKlinik());
        customViewHolder.txtHariDokter.setText("Hari: "+dokterKlinikSchedule.getHari());
        customViewHolder.txtJamDokter.setText("Jam: "+ dokterKlinikSchedule.getJam_dari() + " - " + dokterKlinikSchedule.getJam_selesai());
    }

    @Override
    public int getItemCount() {
        return (null != dokterKlinikSchedules ? dokterKlinikSchedules.size() : 0);
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

     //   protected TextView txtDokterJadwal;
        protected TextView txtKlinikDokter;
        protected TextView txtHariDokter;
        protected TextView txtJamDokter;


        public CustomViewHolder(View view) {
            super(view);
         //   this.txtDokterJadwal = (TextView) view.findViewById(R.id.txtDokterJadwal);
            this.txtKlinikDokter = (TextView) view.findViewById(R.id.txtKlinikDokter);
            this.txtHariDokter = (TextView) view.findViewById(R.id.txtHariDokter);
            this.txtJamDokter = (TextView) view.findViewById(R.id.txtJamDokter);

        }
    }
}
