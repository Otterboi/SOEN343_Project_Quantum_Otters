package Backend.Mediator;

// Component Abstract Class
public abstract class Component {
    protected Mediator mediator;

    public Component(Mediator mediator){
        this.mediator = mediator;
    }

}
