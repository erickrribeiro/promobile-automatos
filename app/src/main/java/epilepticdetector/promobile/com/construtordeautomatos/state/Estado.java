package epilepticdetector.promobile.com.construtordeautomatos.state;

import java.util.ArrayList;
import java.util.List;

import epilepticdetector.promobile.com.construtordeautomatos.transition.Transicao;

/**
 * Created by erickribeiro on 18/03/15.
 */

public class Estado extends Circle {
    private static int id = 0;
    private int codigo;
    private String nome;
    private boolean inicial;
    private boolean estFinal;
    private List<Transicao> transicoes;

    public Estado( String nome, boolean inicial, boolean estFinal ) {
        super(Circle.DEFAULT_X, Circle.DEFAULT_Y, Circle.RAIO, inicial, estFinal);
        this.codigo = id++;
        setNome(String.valueOf(codigo));
        this.inicial = inicial;
        this.estFinal = estFinal;
        transicoes = new ArrayList<Transicao>();
    }

    public void addTransicoes(Transicao transicao) {
        transicoes.add(transicao);
    }

    public List<Transicao> getTransicoes() {
        return transicoes;
    }

    public boolean isFinal() {
        return estFinal;
    }

    public boolean isInicial() {
        return inicial;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        super.getLabel().setText(nome);
        this.nome = nome;
    }

    public void setFinal( boolean estFinal ) {
        super.setStateFinal(estFinal);
        this.estFinal = estFinal;
    }

    public int getCodigo() {
        return codigo;
    }

    public boolean equals(Estado obj) {
        return this.codigo == obj.codigo;
    }
}
