package Backend.Observer;

import Backend.HouseLayout.Zone;
import Backend.SimulatorMenu.SimulatorHome;
import javafx.scene.layout.Pane;

public class SHHObserver implements Observer{

    private SimulatorHome menu = SimulatorHome.getInstance();
    private Pane pane;
    private Zone zone;

    public SHHObserver(Pane pane, Zone zone){
        this.pane = pane;
        this.zone = zone;
    }


    @Override
    public void update(Observable o) {
        Zone zone = (Zone) o;
    }
}
