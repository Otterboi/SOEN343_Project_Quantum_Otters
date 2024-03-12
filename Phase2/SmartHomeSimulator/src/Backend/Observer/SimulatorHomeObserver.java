package Backend.Observer;

import Backend.Model.DateTime;
import Backend.SimulatorMenu.SimulatorHome;
import javafx.scene.control.Label;
import Backend.Observer.Observer;

import java.time.LocalDate;

public class SimulatorHomeObserver implements Observer{

    private Label timeLabel, dateLabel, userLabel, tempLabel, roomLabel;
    private SimulatorHome menu;

    public SimulatorHomeObserver(SimulatorHome menu, Label timeLabel, Label dateLabel, Label userLabel, Label tempLabel, Label roomLabel){
        this.menu = menu;
        this.timeLabel = timeLabel;
        this.dateLabel = dateLabel;
        this.userLabel = userLabel;
        this.tempLabel = tempLabel;
        this.roomLabel = roomLabel;
    }

    @Override
    public void update(Observable o) {
        SimulatorHome menu = (SimulatorHome) o;

        this.timeLabel.setText(menu.getTime());
        this.dateLabel.setText(menu.getDate());
        this.userLabel.setText(menu.getUser());
        this.tempLabel.setText(menu.getTemp());
        this.roomLabel.setText(menu.getRoom());
    }
}
