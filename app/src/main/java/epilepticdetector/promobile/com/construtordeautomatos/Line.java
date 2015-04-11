package epilepticdetector.promobile.com.construtordeautomatos;

import android.graphics.Color;
import android.graphics.Paint;

import java.util.List;
/**
 * Created by erickribeiro on 18/03/15.
 */
public class Line extends Reta{
    private Coordinate inicio;
    private Coordinate fim;

    private Paint cor ;
    private boolean selecionado;

    private boolean abilitado;

    /**
     * Valor que indicadica a distancia que foi usada para dizer se a linha foi selecionada ou não.
     */

    public static  final int DISTANCIA = 40;

    public Line( Coordinate inicio, Coordinate fim ){
        super();

        this.cor = new Paint();
        this.cor.setColor(Color.BLACK);
        this.cor.setAntiAlias(true);
        this.selecionado = false;

        this.inicio = inicio;
        this.fim = fim;
        this.abilitado = false;
    }

    public void setFim(Coordinate fim) {
        this.fim = fim;
    }

    public Coordinate getFim() {
        return fim;
    }

    public void setInicio(Coordinate inicio) {
        this.inicio = inicio;
    }

    public Coordinate getInicio() {
        return inicio;
    }

    public Paint getCor() {
        return cor;
    }

    public void setCor(int cor) {
        this.cor.setColor(cor);
    }

    public void setSelecionado(boolean valor){
        this.selecionado = valor;
        if(valor) {
            this.setCor(Color.GREEN);
        }else{
            this.setCor(Color.BLACK);
        }
    }

    /**
     * Devido a funcao da reta gerar uma reta infinita é necessario abstrair um segmento de reta com
     * essa função.
     * @param x
     * @returnfloat origem  = this.origem.getCurrentX();
     */
    public Boolean pertenceAoSegmentoDeReta(float x, float y) {
        float origem, destino;

        if (Math.abs(this.fim.getAixisX() - this.inicio.getAixisX()) > 20) {
            if (this.inicio.getAixisX() < this.fim.getAixisX()) {
                origem = this.inicio.getAixisX();
                destino = this.fim.getAixisX();
            } else {
                origem = this.fim.getAixisX();
                destino = this.inicio.getAixisX();
            }
            return ((origem < x) && (x < destino));
        } else {
            if (this.inicio.getAixisY() < this.fim.getAixisY()) {
                origem = this.inicio.getAixisY();
                destino = this.fim.getAixisY();
            } else {
                origem = this.fim.getAixisY();
                destino = this.inicio.getAixisY();
            }
            return ((origem < y) && (y < destino));
        }
    }

    public void calculateEquationOfLine() {
        super.calculateEquationOfLine(inicio.getAixisX(), inicio.getAixisY(), fim.getAixisX(), fim.getAixisY());
    }

    public void setAbilitado(boolean abilitado) {
        this.abilitado = abilitado;
    }

    public boolean isAbilitado() {
        return abilitado;
    }


}
