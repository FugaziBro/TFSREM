package info;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Configs {
    public static final int DELAY = 10000;
    public static final String SELECTED_ARTICLE_COLOR = "rgba(51, 51, 51, 1)";

    public final static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    public static Date dToday = new Date();
    public static Date dTomorrow= new DateTime().plusDays(1).toDate();


    public final static String todayStr = format.format(dToday);
    public final static String tomorrowStr = format.format(dTomorrow);
}
