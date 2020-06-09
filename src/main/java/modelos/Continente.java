package modelos;

import java.util.HashMap;

public class Continente {
    private HashMap<String,Pais> paises=new HashMap<String, Pais>();
    private String continentExp;

    public HashMap<String, Pais> getPaises() {
        return paises;
    }
    public void engadeReporte(Pais p){
        if (this.paises.containsKey(p.getCountriesAndTerritories())){
            this.paises.get(p.getCountriesAndTerritories()).engadeReporte(p);
        }else{
            Pais x= new Pais(p);
            this.paises.put(x.getCountriesAndTerritories(),x);
        }
    }
    public int casosDia(String data){
        int casos=0;
        for (Pais p : this.paises.values()){
            casos=casos+p.casosDia(data);
        }
        return casos;
    }
    public int mortesDia(String data){
        int mortes=0;
        for(Pais p:this.paises.values()){
            mortes=mortes+p.mortesDia(data);
        }
        return mortes;
    }

    public void setPaises(HashMap<String, Pais> paises) {
        this.paises = paises;
    }

    public String getContinentExp() {
        return continentExp;
    }

    public void setContinentExp(String continentExp) {
        this.continentExp = continentExp;
    }

    public boolean equals(Object o){
        if((o instanceof Continente)) return false;
        Continente c = (Continente) o;
        return this.getContinentExp().equals(((Continente) o).getContinentExp());
    }


}
