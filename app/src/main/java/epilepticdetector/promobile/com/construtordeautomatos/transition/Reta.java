package epilepticdetector.promobile.com.construtordeautomatos.transition;

import java.util.ArrayList;
import java.util.List;

import epilepticdetector.promobile.com.construtordeautomatos.Util;

/**
 * Created by erickribeiro on 29/03/15.
 */
public class Reta {
    private double a;
    private double b;
    private double c;

    private Reta retaPerpendicular;

    public Reta(double a, double b, double c){
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public Reta(){

    }

    public void calculateEquationOfLine(double x, double y, double x0, double y0){
        List<Double> equacaoDaReta =  Util.equacaoGeralDaReta(x0, y0, x, y);

        this.a = equacaoDaReta.get(0);
        this.b = equacaoDaReta.get(1);
        this.c = equacaoDaReta.get(2);

    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double getC() {
        return c;
    }

    public void setC(double c) {
        this.c = c;
    }

    /**
     * Método responsavel por calcular a distancia entre um ponto clicado ne tela, e está reta.
     * @param x
     * @param y
     * @return
     */
    public double getDistanciaEntrePontoReta(double x, double y) {
        return Util.distanciaEntrePontoReta(x, y, a, b, c);
    }

    /**
     * Método responsavél por gerar a equação da reta, sendo passada com parametro duas coordenadas.
     * @return
     */
    public List<Double> getEquacaoDaReta(){
        List<Double> retorno = new ArrayList<Double>();
        retorno.add(a);
        retorno.add(b);
        retorno.add(c);
        return retorno;
    }

    public Reta getEquacaoDaRetaPerpendicular(){
        return retaPerpendicular;
    }

    public double getValueAxisX(double valueAxisY ) {
        return ( -b * valueAxisY / a) - (c/a);
    }

    public void calculateEquationOfLinePerpendicular(double x, double y) {
        List<Double> retorno = Util.retaPerpendicular(a, b, c, x, y);
        retaPerpendicular = new Reta(retorno.get(0), retorno.get(1), retorno.get(2));
    }

}