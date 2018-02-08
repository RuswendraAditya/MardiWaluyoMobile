package bethesda.com.bethesdahospitalmobile.main.registration;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import bethesda.com.bethesdahospitalmobile.R;
import bethesda.com.bethesdahospitalmobile.main.utility.DateUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CalendarPicker extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    @BindView(R.id.editCalendarPicker)
    EditText editCalendarPicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_picker);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.editCalendarPicker)
    public void editCalendarPickerClick(View view) {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                CalendarPicker.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );

        dpd.setVersion(DatePickerDialog.Version.VERSION_2);
        dpd.setAccentColor(Color.rgb(41, 182, 246));
       // dpd.setThemeDark(true);
        dpd.setMinDate(now);

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, DateUtil.getMaxDaysRegis()-1);
        dpd.setMaxDate(cal);
        Locale local_indonesia = new Locale("id", "ID");
        dpd.setLocale(local_indonesia);
        dpd.show(getFragmentManager(), "Datepickerdialog");

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date_selected = dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
        editCalendarPicker.setText( date_selected);
    }

    @Override
    public void onResume() {
        super.onResume();
        DatePickerDialog dpd = (DatePickerDialog) getFragmentManager().findFragmentByTag("Datepickerdialog");
        if(dpd != null) dpd.setOnDateSetListener(this);
    }
}
