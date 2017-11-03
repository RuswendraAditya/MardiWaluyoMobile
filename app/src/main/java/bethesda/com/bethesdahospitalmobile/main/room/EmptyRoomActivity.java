package bethesda.com.bethesdahospitalmobile.main.room;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import bethesda.com.bethesdahospitalmobile.R;
import bethesda.com.bethesdahospitalmobile.main.room.adapter.RoomAdapter;
import bethesda.com.bethesdahospitalmobile.main.room.model.Room;
import bethesda.com.bethesdahospitalmobile.main.room.service.RoomServices;
import bethesda.com.bethesdahospitalmobile.main.utility.DateUtil;
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
