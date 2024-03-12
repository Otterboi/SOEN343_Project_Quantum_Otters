package Backend.Model;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.util.Duration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTime {

    private DoubleProperty clockSpeedMultiplier = new SimpleDoubleProperty(1);
    private Timeline clock;
    private Calendar date;
    // Observable property for date and time updates
    private SimpleDoubleProperty dateTimeProperty = new SimpleDoubleProperty();
    private static DateTime instance;


    public static DateTime getInstance() {
        if (instance == null) {
            instance = new DateTime();
        }

        return instance;
    }

    private DateTime() {
        date = Calendar.getInstance();
        initializeClock();
    }

    private void initializeClock() {
        clock = new Timeline(new KeyFrame(Duration.seconds(1), e -> {

            int baseMultiplier = 3700;
            int secondsToAdd = (int) Math.pow(baseMultiplier, clockSpeedMultiplier.get() - 1);

            date.add(Calendar.SECOND, secondsToAdd);

            dateTimeProperty.set(System.currentTimeMillis());
        }));
        clock.setCycleCount(Animation.INDEFINITE);

    }

    public void startTime() {
        clock.play();
    }

    public void stopTime() {
        clock.pause();
    }

    // In DateTime class
    public DoubleProperty dateTimeProperty() {
        return dateTimeProperty;
    }

    public void setTime(String time) {
        String[] timeSplit = time.split(":");
        this.date.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeSplit[0]));
        this.date.set(Calendar.MINUTE, Integer.parseInt(timeSplit[1]));
        this.date.set(Calendar.SECOND, Integer.parseInt(timeSplit[2]));
    }

    public boolean isRunning() {
        return clock != null && clock.getStatus() == Animation.Status.RUNNING;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        formatter = formatter.withLocale(Locale.ENGLISH);
        LocalDate localDate = LocalDate.parse(date, formatter);

        this.date.set(Calendar.YEAR, localDate.getYear());
        this.date.set(Calendar.MONTH, localDate.getMonthValue() - 1);
        this.date.set(Calendar.DAY_OF_MONTH, localDate.getDayOfMonth());
    }

    public DoubleProperty clockSpeedMultiplierProperty() {
        return clockSpeedMultiplier;
    }

    public double getClockSpeedMultiplier() {
        return clockSpeedMultiplier.get();
    }

    public void setClockSpeedMultiplier(double clockSpeedMultiplier) {
        this.clockSpeedMultiplier.set(clockSpeedMultiplier);
    }

}
