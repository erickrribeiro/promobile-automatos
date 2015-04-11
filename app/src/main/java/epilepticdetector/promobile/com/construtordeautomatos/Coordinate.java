package epilepticdetector.promobile.com.construtordeautomatos;

/**
 * Created by erickribeiro on 01/04/15.
 */
public class Coordinate {
    private float aixisX;
    private float aixisY;

    public Coordinate(){

    }
    public Coordinate(float x, float y){
        this.aixisX = x;
        this.aixisY = y;
    }

    public float getAixisX() {
        return aixisX;
    }

    public void setAixisX(float aixisX) {
        this.aixisX = aixisX;
    }

    public float getAixisY() {
        return aixisY;
    }

    public void setAixisY(float aixisY) {
        this.aixisY = aixisY;
    }
}
