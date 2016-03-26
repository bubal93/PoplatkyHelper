package bubal.poplatkyhelper;

import java.text.SimpleDateFormat;

public class Utils {

    public static String getDate(long date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");
        return dateFormat.format(date);
    }
}
