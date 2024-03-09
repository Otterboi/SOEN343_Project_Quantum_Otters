package Backend.Model;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.util.Duration;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class DateTime {

    private DoubleProperty clockSpeedMultiplier = new SimpleDoubleProperty(1);
    private Timeline clock;
    private Calendar date;
    // Observable property for date and time updates
    private SimpleDoubleProperty dateTimeProperty = new SimpleDoubleProperty();

    public DateTime() {
        date = Calendar.getInstance();
        initializeClock();
    }

    private void initializeClock() {
        clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            date.add(Calendar.SECOND, 1);
            // Notify observers (e.g., the controller) of the change
            dateTimeProperty.set(dateTimeProperty.get() + 1); // Trigger an update
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.rateProperty().bind(clockSpeedMultiplier);
    }

    public void startTime() {
        clock.play();
    }

    public void stopTime() {
        clock.pause();
    }

    public void setTime(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        this.date.set(Calendar.HOUR_OF_DAY, c.get(Calendar.HOUR_OF_DAY));
        this.date.set(Calendar.MINUTE, c.get(Calendar.MINUTE));
        this.date.set(Calendar.SECOND, c.get(Calendar.SECOND));
    }

    public boolean isRunning() {
        return clock != null && clock.getStatus() == Animation.Status.RUNNING;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date.set(Calendar.YEAR, date.getYear());
        this.date.set(Calendar.MONTH, date.getMonthValue() - 1);
        this.date.set(Calendar.DAY_OF_MONTH, date.getDayOfMonth());
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
