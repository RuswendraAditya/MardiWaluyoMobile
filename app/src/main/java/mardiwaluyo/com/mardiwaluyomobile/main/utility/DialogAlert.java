package mardiwaluyo.com.mardiwaluyomobile.main.utility;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;

import mardiwaluyo.com.mardiwaluyomobile.R;
import mardiwaluyo.com.mardiwaluyomobile.main.schedule.ScheduleActivity;


/**
 * Created by Wendra on 10/6/2017.
 */

public class DialogAlert {

    public void alertValidation(Context context, String title, String message) {
        new MaterialStyledDialog.Builder(context)
                .setTitle(title)
                .setCancelable(Boolean.FALSE)
                .setDescription(message)
                .setPositiveText("OK")
                .setIcon(R.drawable.logo_rsmw)
                .withIconAnimation(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    public void alertValidationWithJadwal(final Context context, String title, String message,final String nid, final String namaDokter) {

        new MaterialStyledDialog.Builder(context)
                .setTitle(title)
                .setCancelable(Boolean.FALSE)
                .setDescription(message)
                .setPositiveText("OK")
                .setNegativeText("Lihat Jadwal Dokter")
                .setIcon(R.drawable.logo_rsmw)
                .withIconAnimation(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Intent intent = new Intent(context, ScheduleActivity.class);
                        intent.putExtra("viewpager_position", 1);
                        intent.putExtra("source", "dialog");
                        intent.putExtra("nid", nid);
                        intent.putExtra("namaDokter", namaDokter);
                        context.startActivity(intent);

                    }
                })
                .show();
    }
}
