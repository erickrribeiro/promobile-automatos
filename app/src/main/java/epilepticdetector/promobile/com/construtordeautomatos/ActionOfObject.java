package epilepticdetector.promobile.com.construtordeautomatos;

import android.graphics.Canvas;

/**
 * Created by eribeiro on 18/04/15.
 * email: erick.ribeiro.16@gmail.com
 */
public interface ActionOfObject {
    public void translation(float x, float y);
    public void draw();
    public void rotate(double angle);
    public void setCanvas(Canvas canvas);
}
