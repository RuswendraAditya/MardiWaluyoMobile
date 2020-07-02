package mardiwaluyo.com.mardiwaluyomobile.main.room;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.GridView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mardiwaluyo.com.mardiwaluyomobile.R;
import mardiwaluyo.com.mardiwaluyomobile.main.room.adapter.RoomAdapter;
import mardiwaluyo.com.mardiwaluyomobile.main.room.model.Room;
import mardiwaluyo.com.mardiwaluyomobile.main.room.service.RoomServices;
import mardiwaluyo.com.mardiwaluyomobile.main.utility.DateUtil;
import butterknife.BindView;
import butterknife.ButterKnife;

public class EmptyRoomActivity extends AppCompatActivity {
    @BindView(R.id.gridDashboard)
    GridView gridDashboard;
    List<Room> roomList = new ArrayList<>();
    RoomAdapter adapter;
    @BindView(R.id.txtTime)
    TextView txtTime;

    ///  android:background="#e5e5e5"
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_room);
        ButterKnife.bind(this);
        initTaskRoom();

    }

    private void getDataAvailableRoom() {

        try {
            roomList = RoomServices.getAvailableRoom();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initTaskRoom() {
        EmptyRoomTask emptyRoomTask = new EmptyRoomTask();
        emptyRoomTask.execute();
    }

    private class EmptyRoomTask extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(EmptyRoomActivity.this,
                    "Please Wait",
                    "Sedang Mengambil Data Ketersediaan Ruang");
        }

        @Override
        protected Void doInBackground(Void... params) {
            getDataAvailableRoom();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            progressDialog.dismiss();
            adapter = new RoomAdapter(EmptyRoomActivity.this, R.layout.room_item_list, roomList);
            adapter.setNotifyOnChange(true);
            gridDashboard.setAdapter(adapter);
            txtTime.setText("* Info update per: " + DateUtil.getCurrentDateTime("dd-MM-yyyy HH:mm"));
        }
    }


}
