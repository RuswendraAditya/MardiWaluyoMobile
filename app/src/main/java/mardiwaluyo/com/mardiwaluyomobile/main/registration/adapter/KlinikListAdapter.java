package bethesda.com.bethesdahospitalmobile.main.registration.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import bethesda.com.bethesdahospitalmobile.R;
import bethesda.com.bethesdahospitalmobile.main.registration.model.Klinik;

/**
 * Created by Wendra on 10/16/2017.
 */

public class KlinikListAdapter extends ArrayAdapter<Klinik> {
    private static class ViewHolder {
        TextView txtNamaKlinik;
        LinearLayout llKlinik;

    }

    Context mContext;
    private List<Klinik> KlinikList;


    public KlinikListAdapter(List<Klinik> Klinik, Context context) {
        super(context, R.layout.klinik_item_list, Klinik);
        this.KlinikList = Klinik;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Klinik Klinik = KlinikList.get(position);
        ViewHolder viewHolder;
        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.klinik_item_list, parent, false);
            viewHolder.txtNamaKlinik = (TextView) convertView.findViewById(R.id.klinik_name);
            viewHolder.llKlinik = (LinearLayout) convertView.findViewById(R.id.llKlinik);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.txtNamaKlinik.setText(Klinik.getNamaKlinik());
        if (Klinik.getPraktek().equals("false")) {
            //  text_KlinikName.setTextColor(Color.RED);
            viewHolder.txtNamaKlinik.setTextColor(Color.RED);
        }
        if (Klinik.getPraktek().equals("true")) {
            //  text_KlinikName.setTextColor(Color.RED);
            viewHolder.txtNamaKlinik.setTextColor(Color.BLACK);
        }
        return convertView;
    }

}
