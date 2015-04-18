package epilepticdetector.promobile.com.construtordeautomatos;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class TicTacToeView extends View {
	final Paint mBackgroundPaint;
	final Paint mForeBoardPaint;
	final BoardGeometry mBoardGeometry;

    private Context context;
    private ArrayList<Estado> mCirclesSelected;
    private Transicao mLinesSelected;

	ArrayList<Estado> mCircles;

    ListaDeTransicoes listaDeTransicoes;

    public boolean eventAddTransition;

    private boolean mDrawingEnabled = true;

	public TicTacToeView(Context context, AttributeSet atrs) {
		super(context, atrs);

        this.context = context;

		mBoardGeometry = new BoardGeometry(50, 100, 260, 310, 70, 70);
		mBackgroundPaint = new Paint();
		mBackgroundPaint.setColor(Color.WHITE);

		mForeBoardPaint = new Paint();
		mForeBoardPaint.setColor(Color.BLACK);
		mForeBoardPaint.setAntiAlias(true);

        mCirclesSelected =  new ArrayList<Estado>();
        mLinesSelected = null;

        createCircles();
        createLines();

        eventAddTransition = false;
	}

    private void createLines(){
        listaDeTransicoes = new ListaDeTransicoes();
        //listaDeTransicoes.add(mCircles.get(0), mCircles.get(1));
    }

    private Transicao addLines(Coordinate origin, Coordinate destination, Estado begin, Estado end){
        //Transicao line1 = new Transicao("1", origin, destination);
        //mLines.add(line1);
        return listaDeTransicoes.add(origin, destination, begin, end);
    }

    /**
     * Cria todos os circulos na tela
     */
	private void createCircles() {
        mCircles = new ArrayList<Estado>();
        Estado estado1 = new Estado("q"+Math.random(), true, false);
        estado1.setCurrentX(90);
        estado1.setCurrentY(100);


        Estado estado2 = new Estado("q"+Math.random(), true, false);
        estado2.setCurrentX(300);
        estado2.setCurrentY(200);

        mCircles.add(estado2);
        mCircles.add(estado1);
	}

    public void addCircle(){
        mCircles.add(new Estado("q"+Math.random(), true, false));
    }

    public void removeAllCircles(){
        mCircles.clear();
    }
    /*
        Redesenha a tela inteira
     */
	public void draw(Canvas canvas) {
		if ( mDrawingEnabled ) {
			final int width = canvas.getWidth();
			final int height = canvas.getHeight();

            //desenha um retângulo de fundo que cobre toda a tela
			canvas.drawRect(0, 0, width, height, mBackgroundPaint);

			// 3. draw red circles on canvas
			drawCirclesOnCanvas(canvas);
            //if(mLines.size() > 0) {
            if(listaDeTransicoes.size() > 0){
                drawLinesOnCanvas(canvas);
            }

			// 5. force redraw
			invalidate();
		}
	}

    public boolean onTouchEvent(MotionEvent event) {
		// 1. get the x and y of MotionEvent
		float x = event.getX();
		float y = event.getY();


        if(!eventAddTransition) {
            // 2. find circle closest to x and y
            Circle cr = findCircleClosestToTouchEvent(x, y);

            if (cr != null) {
                handleTouchedCircle(event, cr);
            }
        }else{
            if(mCirclesSelected.size() < 2){
                findCircleClosestToTouchEvent(x, y);
            }else{
                Estado estInicio = mCirclesSelected.get(0);
                Estado estFim = mCirclesSelected.get(1);

                Log.d("Transicoes", String.format("Inicio: %s -  fim: %s ", estInicio.getNome(),estFim.getNome()));

                if(!listaDeTransicoes.findTransition(estInicio, estFim)) {
                    Log.d("Transicao", "Linha não existe");
                    Transicao t = addLines(estInicio.getCoordinate(), estFim.getCoordinate(), estInicio, estFim);

                    Log.d("Distancia da linha: ","Distancia: "+Util.euclidDist(estInicio.getCoordinate().getAixisX(),
                            estInicio.getCoordinate().getAixisY(),
                            estFim.getCoordinate().getAixisX(),
                            estFim.getCoordinate().getAixisY()));

                    float[] linePts = Util.pontosDaRetaTangenteACircunferencia(
                            estInicio.getCurrentX(), estInicio.getCurrentY(),
                            estFim.getCurrentX(), estFim.getCurrentY()
                    );

                    float[] linePts2 = Util.pontosDaRetaTangenteACircunferencia(
                            estFim.getCurrentX(), estFim.getCurrentY(),
                            estInicio.getCurrentX(), estInicio.getCurrentY()
                    );


                    Coordinate tgSuperiorX = new Coordinate(linePts[0], linePts[1]);
                    Coordinate tgSuperiorY = new Coordinate(linePts2[2], linePts2[3]);

                    Coordinate tgInferiorX = new Coordinate(linePts[2], linePts[3]);
                    Coordinate tgInferiorY = new Coordinate(linePts2[0], linePts2[1]);

                    Transicao t2 = addLines(tgSuperiorX, tgSuperiorY, estInicio, estFim);
                    Transicao t3 = addLines(tgInferiorX, tgInferiorY, estInicio, estFim);

                    estInicio.addTransicoes(t);
                    estInicio.addTransicoes(t2);
                    estInicio.addTransicoes(t3);

                    estFim.addTransicoes(t);
                    estFim.addTransicoes(t2);
                    estFim.addTransicoes(t3);

                    listaDeTransicoes.abilitarTransicao(estInicio, estFim);

                }else{
                    Log.d("TRANSICAO", "já existe [abilita]");
                    listaDeTransicoes.abilitarTransicao(estInicio, estFim);
                }

                mCirclesSelected.get(0).setSelecionado(false);
                mCirclesSelected.get(1).setSelecionado(false);
                mCirclesSelected.remove(0);
                mCirclesSelected.remove(0);
                mCirclesSelected.clear();
                eventAddTransition = false;
            }
        }

        return true;
	}

    public static void delocaPontosParaBorda(Transicao transicao, Estado estado, Canvas canvas){
        // gera a hipotenusa

        float h = Util.gerarHipotenusa(
                transicao.getOrigin().getCurrentX(), transicao.getOrigin().getCurrentY(),
                transicao.getEnd().getCurrentX(), transicao.getEnd().getCurrentY()
        );

        // gera o grau relativo entre os estados
        double gr = Util.obtemGrauRelativoJava(
                transicao.getOrigin().getCurrentX(),transicao.getOrigin().getCurrentY(),
                transicao.getEnd().getCurrentX(), transicao.getEnd().getCurrentY()
        );

        // calcula o x e y do início da flecha
        // sendo que h deve ser dubtraido do raio do estado que no caso
        // é 25, pois a flecha deve ser desenhada na borda do estado

        int distanciaDaBorda = 10;


        float inicioX = (float) (( h - Circle.RAIO - distanciaDaBorda) * Math.cos( Math.toRadians( gr ) ));
        float inicioY = (float) (( h - Circle.RAIO - distanciaDaBorda) * Math.sin( Math.toRadians( gr ) ));

        transicao.getInicio().setAixisX(inicioX+estado.getCurrentX());
        transicao.getInicio().setAixisY(inicioY+estado.getCurrentY());

        float x= transicao.getInicio().getAixisX();
        float y=transicao.getInicio().getAixisY();

        //put the lines in an array
        float[] linePts = new float[] { x-10, y+5, x-10, y-5};

        //create the matrix
        Matrix rotateMat = new Matrix();

        //rotate the matrix around the center
        rotateMat.setRotate((float) gr, x, y);
        rotateMat.mapPoints(linePts);

        canvas.drawLine(x, y, linePts[0], linePts[1], transicao.getCor());
        canvas.drawLine(x, y, linePts[2], linePts[3], transicao.getCor());

        ///
        // gera a hipotenusa
        h = Util.gerarHipotenusa(
                transicao.getEnd().getCurrentX(), transicao.getEnd().getCurrentY(),
                transicao.getOrigin().getCurrentX(), transicao.getOrigin().getCurrentY()
        );

        // gera o grau relativo entre os estados
        gr = Util.obtemGrauRelativoJava(
                transicao.getEnd().getCurrentX(), transicao.getEnd().getCurrentY(),
                transicao.getOrigin().getCurrentX(),transicao.getOrigin().getCurrentY()
        );

        // calcula o x e y do início da flecha
        // sendo que h deve ser dubtraido do raio do estado que no caso
        // é 25, pois a flecha deve ser desenhada na borda do estado

        inicioX = (float) (( h - Circle.RAIO - distanciaDaBorda) * Math.cos( Math.toRadians( gr ) ));
        inicioY = (float) (( h - Circle.RAIO - distanciaDaBorda) * Math.sin( Math.toRadians( gr ) ));

        transicao.getFim().setAixisX(inicioX+ transicao.diferenteBegin(estado).getCurrentX());
        transicao.getFim().setAixisY(inicioY+ transicao.diferenteBegin(estado).getCurrentY());

        x = transicao.getFim().getAixisX();
        y = transicao.getFim().getAixisY();

        //put the lines in an array
        linePts = new float[] { x-10, y+5, x-10, y-5};

        //create the matrix
        rotateMat = new Matrix();

        //rotate the matrix around the center
        rotateMat.setRotate((float) gr, x, y);
        rotateMat.mapPoints(linePts);

        canvas.drawLine(x, y, linePts[0], linePts[1], transicao.getCor());
        canvas.drawLine(x, y, linePts[2], linePts[3], transicao.getCor());


    }
    //Desenha todos os circulos na tela do celular
	private void drawCirclesOnCanvas(Canvas canvas) {
		for (Estado c : mCircles) {

//            estado2.setCurrentX(300);
//            estado2.setCurrentY(200);


            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);

            paint.setTextSize(20);

            //                      X1   Y1    X2   Y2
            RectF oval1 = new RectF(c.getCurrentX()-30, c.getCurrentY()-70, c.getCurrentX()+30, c.getCurrentY());
            //                       270                 150     330                   200
            canvas.drawArc(oval1, 0, -180, false, paint);

            float arrowCentroX = c.getCurrentX() - Circle.RAIO+10;
            float arrowCentroY = c.getCurrentY() - Circle.RAIO+5;

            canvas.drawCircle(c.getCurrentX(), c.getCurrentY(), c.getRadius(), c.getCor());
            canvas.drawLine(arrowCentroX, arrowCentroY, arrowCentroX-5, arrowCentroY-10, paint);
            canvas.drawLine(arrowCentroX, arrowCentroY, arrowCentroX+5, arrowCentroY-10, paint);
            canvas.drawText(c.getNome(),c.getCurrentX(), c.getCurrentY()-73, paint);


            canvas.drawText(c.getNome(),c.getCurrentX(), c.getCurrentY(), paint);


            if (c.isMoveu()) {
                int i;
                List<Transicao> t = c.getTransicoes();
                for (i = 0; i < c.getTransicoes().size(); i = i+3){
                    //Circulo é a origem
                    if (t.get(i).equalsBegin(c)) {
                        delocaPontosParaBorda(t.get(i), c, canvas);


                        //linha tg

                        float[] linePts = Util.pontosDaRetaTangenteACircunferencia(
                                c.getCurrentX(), c.getCurrentY(),
                                t.get(i+1).diferenteBegin(c).getCurrentX(), t.get(i+1).diferenteBegin(c).getCurrentY()
                        );

                        float[] linePts2 = Util.pontosDaRetaTangenteACircunferencia(
                                t.get(i+2).diferenteBegin(c).getCurrentX(), t.get(i+2).diferenteBegin(c).getCurrentY(),
                                c.getCurrentX(), c.getCurrentY()
                        );

                        Coordinate tgSuperiorX = new Coordinate(linePts [0], linePts [1]);
                        Coordinate tgSuperiorY = new Coordinate(linePts2 [2], linePts2 [3]);

                        Coordinate tgInferiorX = new Coordinate(linePts [2], linePts [3]);
                        Coordinate tgInferiorY = new Coordinate(linePts2 [0], linePts2 [1]);

                        t.get(i+1).setInicio(tgSuperiorX);
                        t.get(i+1).setFim(tgSuperiorY);

                        t.get(i+2).setInicio(tgInferiorX);
                        t.get(i+2).setFim(tgInferiorY);
                    }
                }
            }
        }
	}

    //Desenha todos as linhas na tela do celular
	private void drawLinesOnCanvas(Canvas canvas) {

		for (Transicao l : listaDeTransicoes.getListagem()) {
            float inicioX = l.getInicio().getAixisX();
            float inicioY = l.getInicio().getAixisY();

            float fimX = l.getFim().getAixisX();
            float fimY = l.getFim().getAixisY();

            if(l.isAbilitado()) {
                canvas.drawLine(inicioX, inicioY, fimX, fimY, l.getCor());
                Paint paint =  l.getCor();
                paint.setTextSize(20);
                canvas.drawText(l.getSimbolo(), (fimX + inicioX)/2, (fimY + inicioY)/2, paint);
            }
        }
	}

    /**
     * Este método localiza o circulo que foi clicado.
     * @param x
     * @param y
     * @return
     */
	private Estado findCircleClosestToTouchEvent(float x, float y) {
		Estado c = null;

		double tempdist = 0;

        if(eventAddTransition){
            for (Estado cr : mCircles) {
                tempdist = Util.euclidDist(cr.getCurrentX(), cr.getCurrentY(), x, y);

                if (tempdist < Circle.RAIO * 3) {
                    c = cr;
                    c.setSelecionado(true);
                    break;
                }
            }
            if(c == null & mCirclesSelected.size() > 0){
                //Toast.makeText(context, "Por favor, selecione um estado", Toast.LENGTH_LONG).show();
                //Log.d("Secionado", "size:"+mCirclesSelected.size());
                // Log.d("Secionado", "Por favor selecione em estado ");
            }else{
                if(mCirclesSelected.get(0).getCodigo() != c.getCodigo()) {
                    mCirclesSelected.add(c);
                    if (mLinesSelected != null) mLinesSelected.setSelecionado(false);
                }else{
                    Log.d("Selecionar transicao", "Transicao não pode ter origem e destino iguais");
                }
            }
        }else {
            if (!mCirclesSelected.isEmpty()) {
                mCirclesSelected.get(0).setSelecionado(false);
                mCirclesSelected.clear();
            }

            for (Estado cr : mCircles) {
                tempdist = Util.euclidDist(cr.getCurrentX(), cr.getCurrentY(), x, y);

                if (tempdist < Circle.RAIO * 3) {
                    c = cr;
                    c.setSelecionado(true);
                    break;
                }
            }
            if(c !=null) {
                mCirclesSelected.add(c);
                if(mLinesSelected != null) mLinesSelected.setSelecionado(false);
            }else{
                findLineClosestToTouchEvent(x, y);
            }
        }

        return c;
	}


    private Line findLineClosestToTouchEvent(float x, float y) {
        float tempdistOne = 0;
        float tempdisttwo = 0;
        float tempdist = 0;
        float sizeLine = 0;
        Transicao selectedLine = null;

        if(mLinesSelected != null){
                mLinesSelected.setSelecionado(false);
        }

        float tmpDistancia = Float.MAX_VALUE;

        for(Transicao line: listaDeTransicoes.getListagem()){
            line.calculateEquationOfLine();
            double distancia =  line.getDistanciaEntrePontoReta(x, y);

            if (distancia < Line.DISTANCIA && distancia < tmpDistancia && line.pertenceAoSegmentoDeReta(x, y)){
                selectedLine = line;
                break;
            }

        }

        if (selectedLine != null) {
            selectedLine.setSelecionado(true);
            mLinesSelected = selectedLine;
        }

        return selectedLine;
    }

	private void handleTouchedCircle(MotionEvent me, Circle c) {
		final float me_x = me.getX();
		final float me_y = me.getY();
		final int action = me.getAction();

        c.setMoveu(true);

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			c.setActionDownX(c.getCurrentX());
			c.setActionDownY(c.getCurrentY());
			c.setActionMoveOffsetX(me_x);
			c.setActionMoveOffsetY(me_y);
			break;
		case MotionEvent.ACTION_MOVE:
		case MotionEvent.ACTION_UP:
			c.setCurrentX(c.getActionDownX() + me_x - c.getActionMoveOffsetX());
			c.setCurrentY(c.getActionDownY() + me_y - c.getActionMoveOffsetY());
			break;
		case MotionEvent.ACTION_CANCEL:
			c.restoreStartPosition();
			break;
		}
	}

	final void enableDrawing() { mDrawingEnabled = true; }
	
	final void disableDrawing() { mDrawingEnabled = false; }

    public void deleteLine(){
        if(mLinesSelected!= null)
            listaDeTransicoes.desabilitaTransicao(mLinesSelected.getOrigin(), mLinesSelected.getEnd());
    }

    public void alteraEstado() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        final EditText editText = new EditText(context);

        mBuilder.setMessage("Informe o novo nome:");
        mBuilder.setTitle("Alterar nome");
        mBuilder.setView(editText);

        mBuilder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int whichButton) {
                mCirclesSelected.get(0).setNome(editText.getText().toString());
            }
        });

        mBuilder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int whichButton) {

            }
        });

        mBuilder.show();
    }
    public void alteraTrasicao() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        final EditText editText = new EditText(context);
        editText.setText(mLinesSelected.getSimbolo());

        mBuilder.setMessage("Altere a transição:");
        mBuilder.setTitle("Informe os símbolos");
        mBuilder.setView(editText);

        mBuilder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int whichButton) {
                mLinesSelected.setSimbolo(editText.getText().toString());
            }
        });

        mBuilder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int whichButton) {

            }
        });

        mBuilder.show();
    }
}
