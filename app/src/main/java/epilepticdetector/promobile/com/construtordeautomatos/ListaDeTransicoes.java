package epilepticdetector.promobile.com.construtordeautomatos;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erickribeiro on 01/04/15.
 */
public class ListaDeTransicoes {
    List<Transicao> listagem;

    public ListaDeTransicoes(){
        listagem =  new ArrayList<Transicao>();
    }

    public Transicao add(Coordinate origem, Coordinate destino, Estado inicio, Estado fim ){
        Transicao transicao =  new Transicao("", origem, destino, inicio, fim);
        listagem.add(transicao);
        return transicao;
    }

    public List<Transicao> getListagem() {
        return listagem;
    }

    public int size() {
        return listagem.size();
    }

    public Transicao get(int location) {
        return listagem.get(location);
    }
    public Transicao remove(int location) {
        return listagem.remove(location);
    }

    public void abilitarTransicao(Estado begin, Estado end) {
        int i;
        Transicao t;
        for (i = 0; i < getListagem().size(); i=i+3) {
            t = getListagem().get(i);

            if (t.equalTransition(begin, end)) {
                if (!get(i).isAbilitado() && !get(i+1).isAbilitado()) {
                    get(i).setAbilitado(true);
                }else if (!get(i + 1).isAbilitado()) {
                    get(i ).setAbilitado(false);
                    get(i + 1).setAbilitado(true);
                    get(i + 2).setAbilitado(true);
                }
            }
        }
    }

    public void desabilitaTransicao(Estado begin, Estado end) {
        int i;
        Transicao t;
        for (i = 0; i < getListagem().size(); i=i+3) {
            t = getListagem().get(i);
            if (t.equalTransition(begin, end)) {
                if (!get(i).isAbilitado() ) {
                    get(i).setAbilitado(true);
                    get(i+1).setAbilitado(false);
                    get(i+2).setAbilitado(false);
                }else{
                    get(i).setAbilitado(false);
                }
            }
        }
    }


    public boolean findTransition(Estado begin, Estado end){
        int i;
        Transicao t;
        for (i = 0; i < getListagem().size(); i=i+3) {
            t = getListagem().get(i);
            if (t.equalTransition(begin, end)) {
                Log.d("Transicao", "transicao encontrada: inicio: "+t.getOrigin().getNome()+" - fim: "+t.getEnd().getNome());
                return true;
            }
        }
        return false;
    }
}
