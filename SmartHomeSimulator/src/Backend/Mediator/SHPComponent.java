package Backend.Mediator;

public class SHPComponent extends Component {
    public SHPComponent(Mediator mediator) {
        super(mediator);
    }

    public void setAwayMode(boolean isAway) {
        // Ensure that mediator is initialized before using it
        if (mediator != null) {
            mediator.notify(isAway);
        } else {
            // Handle the case when mediator is null
            System.err.println("Mediator is not initialized.");
        }
    }
}
