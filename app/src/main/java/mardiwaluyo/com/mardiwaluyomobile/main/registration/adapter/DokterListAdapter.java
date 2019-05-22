package mardiwaluyo.com.mardiwaluyomobile.main.registration.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import mardiwaluyo.com.mardiwaluyomobile.R;
import mardiwaluyo.com.mardiwaluyomobile.main.registration.model.Dokter;

/**
 * Created by Wendra on 10/16/2017.
 */

public class DokterListAdapter extends ArrayAdapter<Dokter> {
    private static class ViewHolder {
        TextView txtNamaDokter;
        LinearLayout llDokter;

    }

    Context mContext;
    private List<Dokter> dokterList;


    public DokterListAdapter(List<Dokter> dokter, Context context) {
        super(context, R.layout.dokter_item_list, dokter);
        this.dokterList = dokter;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Dokter dokter = dokterList.get(position);
        ViewHolder viewHolder;
        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.dokter_item_list, parent, false);
            viewHolder.txtNamaDokter = (TextView) convertView.findViewById(R.id.dokter_name);
            viewHolder.llDokter = (LinearLayout) convertView.findViewById(R.id.llDokter);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.txtNamaDokter.setText(dokter.getNamaDokter());
        if (dokter.getPraktek().equals("false")) {
            //  text_dokterName.setTextColor(Color.RED);
            viewHolder.txtNamaDokter.setTextColor(Color.RED);
        }
        if (dokter.getPraktek().equals("true")) {
            //  text_dokterName.setTextColor(Color.RED);
            viewHolder.txtNamaDokter.setTextColor(Color.BLACK);
        }
        return convertView;
    }

}
