package Backend.Mediator;
import Backend.HouseLayout.*;
import Backend.Model.DateTime;
import Backend.Model.Log;

public class SHHComponent extends Component {
    public SHHComponent(SmartHomeMediator mediator) {
        super(mediator);
    }

    public void blockDoorsAndWindows(){
        House.setDoorBlocked(true);

        String output = "All doors and windows are blocked for SHH";
        Log.getInstance().getLogEntriesConsole().add("[" + DateTime.getInstance().getTimeAsString() + "] " + output);
        Log.getInstance().getLogEntries().add(
                "\n\n\nTimestamp: " + DateTime.getInstance().getTimeAndDateAsString()+
                        "\nEvent: Door and Window Block State Change" +
                        "\nLocation: Entire House" +
                        "\nTriggered By: " + House.getLoggedInUser().getName() +
                        "\nEvent Details: " + output
        );

    }
    public void unblockDoorsAndWindows(){
        House.setDoorBlocked(false);

        String output = "All doors and windows are unblocked for SHH";
        Log.getInstance().getLogEntriesConsole().add("[" + DateTime.getInstance().getTimeAsString() + "] " + output);
        Log.getInstance().getLogEntries().add(
                "\n\n\nTimestamp: " + DateTime.getInstance().getTimeAndDateAsString()+
                        "\nEvent: Door and Window Block State Change" +
                        "\nLocation: Entire House" +
                        "\nTriggered By: " + House.getLoggedInUser().getName() +
                        "\nEvent Details: " + output
        );

    }

}