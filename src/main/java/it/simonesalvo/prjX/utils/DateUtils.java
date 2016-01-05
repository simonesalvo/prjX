package it.simonesalvo.prjX.utils;

import com.google.common.collect.ImmutableList;
import org.joda.time.DateTime;

import javax.annotation.Nullable;
import java.util.logging.Logger;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by Simone Salvo on 04/08/2015.
 * www.simonesalvo.it
 */

public class DateUtils {

    private final static Logger LOGGER = Logger.getLogger(DateUtils.class.getName());

    public enum DateTimeFormat{
        HOUR,
        DAY,
        WEEK,
        MONTH,
        YEAR
    }

    private static final List<String> DATE_PATTERNS = ImmutableList.of(
            "EEE, dd MMM yyyy HH:mm:ss Z",
            "dd MMM yyyy HH:mm:ss Z"
    );

    public static Long parseTimestamp(@Nullable String timestamp) {
        for (String datePattern : DATE_PATTERNS) {
            LOGGER.info("Trying to use the pattern " + datePattern + " to parse timestamp " + timestamp);
            try {
                long time = new SimpleDateFormat(datePattern, Locale.ENGLISH).parse(timestamp).getTime();
                LOGGER.info("Pattern " + datePattern + " matched the timestamp " + timestamp);
                return time;
            } catch (Exception e) {
                LOGGER.info("Pattern " + datePattern + " did not match the timestamp " + timestamp);
            }
        }
        LOGGER.warning("Unable to retrieve AODocs date from message with timestamp " + timestamp +
                ". Please, retrieve the format and add it inside the available patterns.");
        return null;
    }

    public static DateTime roundTo(DateTimeFormat dateTimeFormat, DateTime dateTime){

        switch (dateTimeFormat){
            case HOUR:{
                return dateTime.withMinuteOfHour(0)
                        .withSecondOfMinute(0)
                        .withMillisOfSecond(0);
            }
            case DAY:{
                return dateTime.withMillisOfDay(0);
            }
            case WEEK:{
                return dateTime.withMillisOfDay(0)
                        .withDayOfWeek(1);
            }
            case MONTH:{
                return dateTime.withMillisOfDay(0)
                        .withDayOfMonth(1);
            }
            case YEAR:{
                return dateTime.withMonthOfYear(1)
                        .withMillisOfDay(0)
                        .withDayOfMonth(1);
            }
            default:{
                throw new IllegalStateException("The round format " + dateTimeFormat + " it's not supported ");
            }
        }
    }
}
