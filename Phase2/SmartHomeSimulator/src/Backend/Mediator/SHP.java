package Backend.Mediator;

public class SHP extends Component{
    public SHP(Mediator mediator) {
        super(mediator);
    }
    
    @Override
    public void triggerEvent(String event) {
        mediator.notify(this, event);
    }
}
