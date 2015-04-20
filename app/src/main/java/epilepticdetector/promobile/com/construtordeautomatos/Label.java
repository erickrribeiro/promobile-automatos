package epilepticdetector.promobile.com.construtordeautomatos;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by eribeiro on 19/04/15.
 * email: erick.ribeiro.16@gmail.com
 */
public class Label implements ActionOfObject{
    private String text;
    private Coordinate origin;
    private Paint color;
    private Canvas canvas;

    public Label(String text, Coordinate origin){
        this.text =  text;
        this.origin = origin;

        this.color = new Paint();
        this.color.setColor(Color.BLACK);
        this.color.setAntiAlias(true);
        canvas = null;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setAixisX(float aixisX) {
        this.origin.setAixisX(aixisX);
    }

    public void setAixisY(float aixisY) {
        this.origin.setAixisY(aixisY);
    }

    public Coordinate getOrigin() {
        return origin;
    }

    public void setColor(Paint color) {
        this.color = color;
    }

    public Paint getColor() {
        return color;
    }

    @Override
    public void draw() {
        canvas.drawText(getText(),getOrigin().getAixisX(), getOrigin().getAixisY(), color);
    }

    @Override
    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    @Override
    public void rotate(double angle) {

    }

    @Override
    public void translation(float x, float y) {

    }
}
