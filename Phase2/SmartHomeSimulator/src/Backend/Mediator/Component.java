package Backend.Mediator;

// Component interface
public abstract class Component {
    protected Mediator mediator;

    public Component(Mediator mediator){
        this.mediator = mediator;
    }

    public abstract void triggerEvent(String event);

}
