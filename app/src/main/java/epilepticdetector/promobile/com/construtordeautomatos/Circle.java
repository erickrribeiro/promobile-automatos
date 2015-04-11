package epilepticdetector.promobile.com.construtordeautomatos;

import android.graphics.Color;
import android.graphics.Paint;

public class Circle extends Shape {

    public final static int RAIO = 40;
    public final static int DEFAULT_X = 160;
    public final static int DEFAULT_Y = 30;

	public float mRadius;
	private boolean selecionado;

    private Paint cor ;
    private boolean moveu;
    public Circle(float x, float y, float raio) {
		super(x, y);
		mRadius = raio;
        selecionado = false;

        cor = new Paint();
        cor.setColor(Color.RED);
        cor.setAntiAlias(true);
        this.moveu = false;
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

	final float getRadius() {
		return mRadius;
	}

    public void setMoveu(boolean moveu) {
        this.moveu = moveu;
    }

    public boolean isMoveu() {
        return moveu;
    }
}
