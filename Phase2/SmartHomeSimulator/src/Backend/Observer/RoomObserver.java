package Backend.Observer;

import Backend.HouseLayout.IndoorRoom;
import Backend.HouseLayout.OutdoorRoom;
import Backend.HouseLayout.Room;
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
    Image windowCloseBlocked = new Image("/resources/windowCloseBlocked.png");
    Image windowOpenBlocked = new Image("/resources/windowOpenBlocked.png");
    Image doorCloseGarage = new Image("/resources/doorCloseGarage.png");
    Image doorOpenGarage = new Image("/Resources/doorOpenGarage.png");
    Image cooling = new Image("/Resources/cooling.png");
    Image heating = new Image("/Resources/heating.png");

    public RoomObserver(Pane pane, Room r){
        this.pane = pane;
        ((Label)pane.getChildren().get(1)).setText(r.getRoomName());
        light = ((ImageView)pane.getChildren().get(2));
        door = ((ImageView)pane.getChildren().get(3));
        window = ((ImageView)pane.getChildren().get(4));
        person = ((ImageView)pane.getChildren().get(5));
        light.setImage(lightOff);
        door.setImage(doorClose);
        if(r instanceof OutdoorRoom outroom){
            if(outroom.isGarage()){
                door.setImage(doorCloseGarage);
            }
        }
        window.setImage(windowClose);
        person.setImage(personNotInRoom);
    }

    @Override
    public void update(Observable o) {
        Room r = (Room) o;

        if(r.isDoorOpen() == true){
            if(r instanceof OutdoorRoom outroom){
                if(outroom.isGarage()){
                    door.setImage(doorOpenGarage);
                }
            }else{
                door.setImage(doorOpen);
            }

        }else{
            if(r instanceof OutdoorRoom outroom){
                if(outroom.isGarage()){
                    door.setImage(doorCloseGarage);
                }else{
                    door.setImage(doorClose);
                }
            }else{
                door.setImage(doorClose);
            }

        }

        if(r.isLightOn() == true){
            light.setImage(lightOn);
        }else{
            light.setImage(lightOff);
        }


        if(r instanceof IndoorRoom indoorRoom){
            if(indoorRoom.isWindowOpen()){
                if(indoorRoom.isWindowBlocked()){
                    window.setImage(windowOpenBlocked);
                }else{
                    window.setImage(windowOpen);
                }

            }else{
                if(indoorRoom.isWindowBlocked()){
                    window.setImage(windowCloseBlocked);
                }else{
                    window.setImage(windowClose);
                }

            }

        }

        if(r.isPersonInRoom() == true){
            person.setImage(personInRoom);
        }else{
            person.setImage(personNotInRoom);
        }

        if(r.isAutoModeEnabled() && r.isPersonInRoom()){
            light.setImage(lightOn);
            r.setLightOn(true);
        } else if (r.isAutoModeEnabled() && !r.isPersonInRoom()){
            light.setImage(lightOff);
            r.setLightOn(false);
        }

    }
}
