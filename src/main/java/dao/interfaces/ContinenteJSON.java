package dao.interfaces;

import modelos.Continente;

public class ContinenteJSON {
    private String nome;
    private int numero_casos;
    private int numero_falecementos;

    public ContinenteJSON(){}

    public ContinenteJSON(Continente c, String dia){
        this.numero_casos=c.casosDia(dia);
        this.numero_falecementos=c.mortesDia(dia);
        this.nome=c.getContinentExp();
    }

    public int getNumero_casos() {
        return numero_casos;
    }

    public void setNumero_casos(int numero_casos) {
        this.numero_casos = numero_casos;
    }

    public int getNumero_falecementos() {
        return numero_falecementos;
    }

    public void setNumero_falecementos(int numero_falecementos) {
        this.numero_falecementos = numero_falecementos;
    }
}
