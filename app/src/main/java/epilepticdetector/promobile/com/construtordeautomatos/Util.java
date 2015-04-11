package epilepticdetector.promobile.com.construtordeautomatos;

import android.graphics.Matrix;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erickribeiro on 19/03/15.
 */
public class Util {
    public static double euclidDist(double x1, double y1, double x2, double y2) {
        return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1)
                * (y2 - y1));
    }

    /**
     * Método responsavél por gerar a equação da reta, sendo passada com parametro duas coordenadas.
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    public static List<Double> equacaoGeralDaReta(double x1, double y1, double x2, double y2){
        List<Double> x = new ArrayList<Double>();
        List<Double> y = new ArrayList<Double>();
        List<Double> resultado = new ArrayList<Double>();

        double c;
        //| -1 2 1 |
        //| -2 5 1 |
        //|  x y 1 |

        c = x1*y2*1;
        y.add(x2*1);
        x.add(1*y1);

        x.add(1*y2*-1);
        y.add(1*x1*-1);
        c -= 1 * x2 * y1;

        double a = 0;
        for(double f : x){
            a += f;
        }

        double b = 0;
        for(double f: y){
            b+= f;
        }
        resultado.add(a);
        resultado.add(b);
        resultado.add(c);

        return resultado;
    }

    public static double distanciaEntrePontoReta(double x, double y, double a, double b, double c){
        return Math.abs(a*x+b*y+c)/ (double) Math.sqrt(a*a + b*b);
    }


    public static List<Double> retaPerpendicular(double a,double b,double  c,double x, double y){
        List<Double>  resultado = new ArrayList<Double>();
        resultado.add(-b);
        resultado.add(a);
        resultado.add((-y * -a) + (x*b));

        return resultado;
    }

    public static float gerarHipotenusa(
            double x1, double y1,
            double x2, double y2 ) {

        double x = Math.abs( x1 ) - Math.abs( x2 );
        double y = Math.abs( y1 ) - Math.abs( y2 );

        return (float) Math.sqrt( Math.pow( x, 2 ) + Math.pow( y, 2 ) );
    }

    public static double obtemGrauRelativoJava(
            double x1, double y1,
            double x2, double y2 ) {

        double x = 0;
        double y = 0;

        if ( x2 > x1 )
            x = x2 - x1;
        else
            x = x1 - x2;

        if ( y2 > y1 )
            y = y2 - y1;
        else
            y = y1 - y2;

        int incr = gerarIncrementoAngulo( x1, y1, x2, y2 );
        double ang = (double) Math.toDegrees( Math.atan2( y, x ) );

        if ( incr == 90 || incr == 270 )
            ang = 90 - ang;

        return incr + ang;

    }

    private static int gerarIncrementoAngulo(
            double x1, double y1,
            double x2, double y2 ) {

        int q = detectarQuadrante( x1, y1, x2, y2 );

        if ( q == 1 ) {
            return 0;
        } else if ( q == 2 ) {
            return 90;
        } else if ( q == 3 ) {
            return 180;
        } else {
            return 270;
        }

    }

    // para sistema carteziano normal
    private static int detectarQuadrante(
            double x1, double y1,
            double x2, double y2 ) {

        if ( ( x2 > x1 && y2 > y1 ) ||
                ( x2 > x1 && y2 == y1 ) ||
                ( x2 == x1 && y2 > y1 ) ||
                ( x2 == x1 && y2 == y1 ) )
            return 1;

        if ( ( x2 < x1 && y2 > y1 ) )
            return 2;

        if ( ( x2 < x1 && y2 < y1 ) ||
                ( x2 == x1 && y2 < y1 ) ||
                ( x2 < x1 && y2 == y1 ) )
            return 3;

        return 4;

    }

    public static float [] pontosDaRetaTangenteACircunferencia(float xo, float yo, float xd, float yd) {

        // gera a hipotenusa
        double h = Util.gerarHipotenusa( xo , yo, xd, yd);

        // gera o grau relativo entre os estados
        double gr = Util.obtemGrauRelativoJava( xo, yo, xd, yd);

        // calcula o x e y do início da flecha
        // sendo que h deve ser dubtraido do raio do estado que no caso
        // é 25, pois a flecha deve ser desenhada na borda do estado

        float x = (float) (( h - Circle.RAIO ) * Math.cos( Math.toRadians( gr ) ));
        float y =  (float) (( h - Circle.RAIO ) * Math.sin( Math.toRadians( gr ) ));

        float xc = (float) (( h + Circle.RAIO ) * Math.cos( Math.toRadians( gr ) ));
        float yc =  (float) (( h + Circle.RAIO ) * Math.sin( Math.toRadians( gr ) ));

        x += xo;
        y += yo;

        xc += xo;
        yc += yo;

        float centerX = Math.abs((x+xc)/2);
        float centerY = Math.abs(y+yc)/2;

        //set the angle
        double angle = 90;

        //put the lines in an array
        float[] linePts = new float[] { xc, yc, x, y};

        //create the matrix
        Matrix rotateMat = new Matrix();

        //rotate the matrix around the center
        rotateMat.setRotate((float) angle, centerX, centerY);
        rotateMat.mapPoints(linePts);

        //draw the line
        return linePts;
    }
}
