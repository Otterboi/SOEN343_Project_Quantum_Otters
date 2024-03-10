package Backend.Observer;

import Backend.HouseLayout.IndoorRoom;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class RoomObserver implements Observer {
    private ImageView light;
    private ImageView door;
    private ImageView window;
    private ImageView person;
    private Pane pane;

    Image lightOff = new Image("/Resources/lightOff.png");
    Image lightOn = new Image("/Resources/lightOn.png");
    Image doorClose = new Image("/Resources/doorClose.png");
    Image doorOpen = new Image("/Resources/doorOpen.png");
    Image windowClose = new Image("/Resources/windowClose.png");
    Image windowOpen = new Image("/Resources/windowOpen.png");
    Image personInRoom = new Image("/resources/person.png");
    Image personNotInRoom = new Image("/resources/blank.png");



    public RoomObserver(Pane pane, IndoorRoom r){
        this.pane = pane;
        ((Label)pane.getChildren().get(1)).setText(r.getRoomName());
        light = ((ImageView)pane.getChildren().get(2));
        door = ((ImageView)pane.getChildren().get(3));
        window = ((ImageView)pane.getChildren().get(4));
        person = ((ImageView)pane.getChildren().get(5));
        light.setImage(lightOff);
        door.setImage(doorClose);
        window.setImage(windowClose);
        person.setImage(personNotInRoom);
    }
    @Override
    public void update(Observable o) {
        IndoorRoom r = (IndoorRoom) o;

        if(r.isDoorOpen() == true){
            door.setImage(doorOpen);
        }else{
            door.setImage(doorClose);
        }

        if(r.isLightOn() == true){
            light.setImage(lightOn);
        }else{
            light.setImage(lightOff);
        }

        if(r.isWindowOpen() == true){
            window.setImage(windowOpen);
        }else{
            window.setImage(windowClose);
        }

        if(r.isPersonInRoom() == true){
            person.setImage(personInRoom);
        }else{
            person.setImage(personNotInRoom);
        }

    }
}
