
package epilepticdetector.promobile.com.construtordeautomatos.transition;

import epilepticdetector.promobile.com.construtordeautomatos.Coordinate;
import epilepticdetector.promobile.com.construtordeautomatos.state.Estado;

public class Transicao extends Line {
    private static int id = 0;
    private String simbolo;
    private Estado inicio;
    private Estado fim;
    private int codigo;

    public Transicao(String simbolo, Coordinate origem, Coordinate destino, Estado inicio, Estado fim) {
        super(origem, destino);
        this.simbolo = simbolo;
        this.inicio = inicio;
        this.fim = fim;
        this.codigo = id++;
    }


    public boolean equalsBegin(Estado estado) {
        return inicio.equals(estado);
    }

    public Estado diferenteBegin (Estado estado) {
        if (inicio.getCodigo() != estado.getCodigo()) {
            return inicio;
        }
        return fim;
    }

    public boolean equalTransition(Estado begin, Estado end) {
        return inicio.getNome().equals(begin.getNome()) & fim.getNome().equals(end.getNome()) |
                inicio.getNome().equals(end.getNome()) & fim.getNome().equals(begin.getNome()) ;
    }

    public Estado getOrigin() {
        return inicio;
    }

    public Estado getEnd() {
        return fim;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    public String getSimbolo() {
        return simbolo;
    }
}

