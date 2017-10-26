package bethesda.com.bethesdahospitalmobile.main.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Wendra on 10/21/2017.
 */

public class DateUtil {

    public static String changeFormatDate(String date_input,String format_old,String format_new)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(format_old);

        Date parsedDate = null;
        try {
            parsedDate = sdf.parse(date_input);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sdf= new SimpleDateFormat(format_new);
        String newFormatDate = sdf.format(parsedDate);
        return  newFormatDate;
    }
}
