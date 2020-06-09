package modelos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Pais {

    HashMap<Date, Reporte> reportes=new HashMap<Date, Reporte>();
    private String fk_continentExp;
    private String popData2018;
    private String countriesAndTerritories;
    private String countryterritoryCode;
    private Reporte r;

    public Pais(){}

    public Pais(Pais p ){
        this.reportes.put(p.getR().getDateRep(),p.getR());
        this.popData2018=p.getPopData2018();
        this.countriesAndTerritories=p.getCountriesAndTerritories();
        this.countryterritoryCode=p.getCountryterritoryCode();
    }
    public Reporte getR() {
        return r;
    }
    public Reporte obterPorData(Date d){
        return this.reportes.get(d);
    }
    public void setR(Reporte r) {
        this.r = r;
    }


    public HashMap<Date, Reporte> getReportes() {
        return reportes;
    }

    public void setReportes(Reporte r) {
        this.reportes.put(r.getDateRep(),r);
    }
    public int casosDia(String d){
        Date data= null;

        try {
            data = new SimpleDateFormat("dd/MM/yyyy").parse(d);
            return this.reportes.get(data).getCases();
        } catch (ParseException e) {
            System.out.println("Erro no formato da data");
            return 999999999;
        }
    }
    public int mortesDia(String d ){
        Date data=null;
        try {
            data=new SimpleDateFormat("dd/MM/yyyy").parse(d);
        } catch (ParseException e) {
            System.out.println("Erro no formato de data");
        }
        return this.reportes.get(data).getDeaths();
    }

    public String getPopData2018() {
        return popData2018;
    }

    public void setPopData2018(String popData2018) {
        this.popData2018 = popData2018;
    }

    public String getCountriesAndTerritories() {
        return countriesAndTerritories;
    }

    public void setCountriesAndTerritories(String countriesAndTerritories) {
        this.countriesAndTerritories = countriesAndTerritories;
    }

    public String getCountryterritoryCode() {
        return countryterritoryCode;
    }

    public void setCountryterritoryCode(String countryterritoryCode) {
        this.countryterritoryCode = countryterritoryCode;
    }

    public void engadeReporte(Pais p) {
        this.reportes.put(p.getR().getDateRep(),p.getR());
    }

    public String getFk_continentExp() {
        return fk_continentExp;
    }

    public void setFk_continentExp(String fk_continentExp) {
        this.fk_continentExp = fk_continentExp;
    }
    public boolean equals(Object o){
        if(!(o instanceof Pais)) return false;
        return this.getCountriesAndTerritories().equals(((Pais) o).getCountriesAndTerritories());
    }

}
