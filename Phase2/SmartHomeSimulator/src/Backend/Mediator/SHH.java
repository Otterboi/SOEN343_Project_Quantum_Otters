package Backend.Mediator;

public class SHH extends Component{
    public SHH(Mediator mediator) {
        super(mediator);
    }

    @Override
    public void triggerEvent(String event) {
        mediator.notify(this, event);
    }
}
