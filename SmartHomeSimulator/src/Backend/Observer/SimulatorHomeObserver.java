package Backend.Observer;

import Backend.SimulatorMenu.SimulatorHome;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;

public class SimulatorHomeObserver implements Observer{

    private Label timeLabel, dateLabel, userLabel, tempLabel, roomLabel;
    private ToggleButton awayMode;
    private SimulatorHome menu;

    public SimulatorHomeObserver(SimulatorHome menu, Label timeLabel, Label dateLabel, Label userLabel, Label tempLabel, Label roomLabel, ToggleButton awayMode){
        this.menu = menu;
        this.timeLabel = timeLabel;
        this.dateLabel = dateLabel;
        this.userLabel = userLabel;
        this.tempLabel = tempLabel;
        this.roomLabel = roomLabel;
        this.awayMode = awayMode;
    }

    @Override
    public void update(Observable o) {
        SimulatorHome menu = (SimulatorHome) o;

        this.timeLabel.setText(menu.getTime());
        this.dateLabel.setText(menu.getDate());
        this.userLabel.setText(menu.getUser());
        this.tempLabel.setText(menu.getTemp() + "°C");
        this.roomLabel.setText(menu.getRoom());

        if (!menu.isAwayMode()){
            awayMode.setSelected(false);
            awayMode.setText("Away Mode ON");
        }
    }
}
