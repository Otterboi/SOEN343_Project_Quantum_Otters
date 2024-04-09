package Backend.Mediator;

// Concrete Mediator
public class SmartHomeMediator implements Mediator{
    private SHPComponent shp;
    private SHHComponent shh;

    public void setSHH(SHHComponent shh){
        this.shh = shh;
    }

    public void setSHP(SHPComponent shp){
        this.shp = shp;
    }

    @Override
    public void notify(boolean isAway) {
        if(isAway){
            shh.blockDoorsAndWindows();
        } else {
            shh.unblockDoorsAndWindows();
        }
    }
}
