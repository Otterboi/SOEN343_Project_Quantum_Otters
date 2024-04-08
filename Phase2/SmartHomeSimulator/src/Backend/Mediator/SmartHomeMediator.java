package Backend.Mediator;

// Concrete Mediator
public class SmartHomeMediator implements Mediator{
    SHP shp;
    SHH shh;

    public void setSHH(SHH shh){
        this.shh = shh;
    }

    public void setSHP(SHP shp){
        this.shp = shp;
    }

    @Override
    public void notify(Component sender, String event) {
        if (sender instanceof SHH) {

        } else if (sender instanceof SHP) {

        }
    }
}
