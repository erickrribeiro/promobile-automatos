package epilepticdetector.promobile.com.construtordeautomatos.transition;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import epilepticdetector.promobile.com.construtordeautomatos.ActionOfObject;
import epilepticdetector.promobile.com.construtordeautomatos.Coordinate;

/**
 * Created by eribeiro on 18/04/15.
 * email: erick.ribeiro.16@gmail.com
 */
public class Arrow implements ActionOfObject {
    private Coordinate origin;
    private Coordinate left;
    private Coordinate right;

    private Canvas canvas;
    private Paint paint;

    public static int HORIZONTAL = 0;
    public static int VERTICAl = 1;

    private int orientation;
    public Arrow(Coordinate origin, Canvas canvas, Paint paint, int orientation) {
        this.origin = origin;
        this.origin = origin;
        this.canvas = canvas;
        this.paint = paint;

        this.orientation = orientation;

        changeLeftRight(this.orientation);
    }

    public void translation(float x, float y) {
        this.origin.setAixisX(this.origin.getAixisX() + x);
        this.origin.setAixisY(this.origin.getAixisY() + y);

        changeLeftRight(this.orientation);
    }

    public void draw() {
        canvas.drawLine(
                origin.getAixisX(), origin.getAixisY(),
                left.getAixisX(), left.getAixisY()
                , paint);

        canvas.drawLine(
                origin.getAixisX(), origin.getAixisY(),
                right.getAixisX(), right.getAixisY()
                , paint);
    }

    public void rotate(double angle){
        float[] linePts = new float[] { left.getAixisX(), left.getAixisY(), right.getAixisX(), right.getAixisY()};

        //create the matrix
        Matrix rotateMat = new Matrix();

        //rotate the matrix around the center
        rotateMat.setRotate((float) angle, origin.getAixisX() , origin.getAixisY());
        rotateMat.mapPoints(linePts);

        left.setAixisX(linePts[0]);
        left.setAixisY(linePts[1]);

        right.setAixisX(linePts[2]);
        right.setAixisY(linePts[3]);

    }

    @Override
    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public void changeOrigin(float x, float y) {
        origin.setAixisX(x);
        origin.setAixisY(y);
        changeLeftRight(this.orientation);
    }

    private void changeLeftRight(int orientation) {
        if(orientation == VERTICAl) {
            this.left = new Coordinate(origin.getAixisX() - 5, origin.getAixisY() - 10);
            this.right = new Coordinate(origin.getAixisX() + 5, origin.getAixisY() - 10);
        }else{
            this.left = new Coordinate(origin.getAixisX() - 10, origin.getAixisY() + 5);
            this.right = new Coordinate(origin.getAixisX() - 10, origin.getAixisY() - 5);
        }
    }


}
