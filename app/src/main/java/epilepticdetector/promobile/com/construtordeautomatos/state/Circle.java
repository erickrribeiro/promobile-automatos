package epilepticdetector.promobile.com.construtordeautomatos.state;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import epilepticdetector.promobile.com.construtordeautomatos.ActionOfObject;
import epilepticdetector.promobile.com.construtordeautomatos.Coordinate;
import epilepticdetector.promobile.com.construtordeautomatos.Label;
import epilepticdetector.promobile.com.construtordeautomatos.Shape;
import epilepticdetector.promobile.com.construtordeautomatos.transition.Arrow;

public class Circle extends Shape implements ActionOfObject {

    public final static int RAIO = 40;
    public final static int DEFAULT_X = 160;
    public final static int DEFAULT_Y = 30;

	public float mRadius;
	private boolean selecionado;

    private Paint cor ;
    private boolean moveu;
    private Canvas canvas;
    private Label label;

    private boolean stateFinal;
    private boolean stateInitial;
    private Arrow arrow;

    public Circle(float x, float y, float raio, boolean stateInitial, boolean stateFinal) {
		super(x, y);
		mRadius = raio;
        selecionado = false;

        cor = new Paint();
        cor.setColor(Color.RED);
        cor.setStyle(Paint.Style.STROKE);
        cor.setAntiAlias(true);

        this.moveu = false;
        this.label = new Label("", new Coordinate(x, y));

        this.stateFinal = stateFinal;
        this.stateInitial =  stateInitial;
        this.arrow = new Arrow(new Coordinate(x, y), null, null, Arrow.HORIZONTAL);
	}

    public void setCor(int cor){
        this.cor.setColor(cor);
    }
    public Paint getCor(){
        return cor;
    }

    public void setSelecionado(boolean valor){
        this.selecionado = valor;
        if(valor) {
            this.setCor(Color.GREEN);
        }else{
            this.setCor(Color.RED);
        }
    }

    public Coordinate getCoordinate(){
        return new Coordinate(super.getCurrentX(), super.getCurrentY());
    }

    public boolean isSelecionado() {
        return this.selecionado;
    }

	public final float getRadius() {
		return mRadius;
	}

    public void setMoveu(boolean moveu) {
        this.moveu = moveu;
    }

    public boolean isMoveu() {
        return moveu;
    }

    public Label getLabel() {
        return label;
    }

    public boolean isStateFinal() {
        return stateFinal;
    }

    public boolean isStateInitial() {
        return stateInitial;
    }

    public void setStateFinal(boolean stateFinal) {
        this.stateFinal = stateFinal;
    }

    public void setStateInitial(boolean stateInitial) {
        this.stateInitial = stateInitial;
    }

    @Override
    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    @Override
    public void draw() {
        canvas.drawCircle(super.getCurrentX(), super.getCurrentY(), getRadius(), getCor());
        this.label.setCanvas(canvas);

        if (isStateFinal()){
            canvas.drawCircle(super.getCurrentX(), super.getCurrentY(), getRadius() - 5, getCor());
        }
        if (isStateFinal()){
            this.arrow.setCanvas(canvas);
            this.arrow.setPaint(getCor());

            this.arrow.changeOrigin(super.getCurrentX(), super.getCurrentY());
            this.arrow.translation(-Circle.RAIO-5, 0);
            this.arrow.draw();
        }

        this.label.setAixisX(super.getCurrentX());
        this.label.setAixisY(super.getCurrentY());
        this.label.draw();
    }

    @Override
    public void translation(float x, float y) {

    }

    @Override
    public void rotate(double angle) {

    }


}
