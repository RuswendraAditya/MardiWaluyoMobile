package bethesda.com.bethesdahospitalmobile.main.utility;

import android.content.Context;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;

import bethesda.com.bethesdahospitalmobile.R;


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
                .setIcon(R.drawable.logo_bethesda)
                .withIconAnimation(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
}
