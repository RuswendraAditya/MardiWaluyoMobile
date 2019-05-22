package mardiwaluyo.com.mardiwaluyomobile.main.room.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mardiwaluyo.com.mardiwaluyomobile.R;
import mardiwaluyo.com.mardiwaluyomobile.main.room.model.Room;

/**
 * Created by Wendra on 10/28/2017.
 */

public class RoomAdapter extends ArrayAdapter<Room> {

    Context context;
    int layoutResourceId;
    List<Room> roomList = new ArrayList<>();

    public RoomAdapter(Context context, int layoutResourceId, List<Room> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.roomList = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        RecordHolder holder = null;
        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
           // holder.gridView = (GridView) row.findViewById(R.id.gridDashboard);
            row.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT, 300));
           //row.setLayoutParams(new );
            holder = new RecordHolder();
            holder.txtKosong = (TextView) row.findViewById(R.id.txtKosong);
            holder.txtNamaKelas= (TextView) row.findViewById(R.id.txtNamaKelas);
            row.setTag(holder);
        } else {
            holder = (RecordHolder) row.getTag();
        }
        Room room = roomList.get(position);
        holder.txtKosong.setText("Tersedia: "+String.valueOf(room.getKamarKosong()));
      //  holder.txtNamaKelas.setGravity(Gravity.LEFT);
       // holder.txtNamaKelas.setStrokeWidth(1);
        //holder.txtNamaKelas.setStrokeColor("#87CEEB");
        //holder.txtNamaKelas.setSolidColor("#87CEEB");
        holder.txtNamaKelas.setText(room.getNamaKelas());
        return row;
    }

    static class RecordHolder {
        TextView txtKosong;
        TextView txtNamaKelas;
        GridView gridView;
    }

}
