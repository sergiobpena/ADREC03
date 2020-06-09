package modelos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Reporte implements Comparable<Reporte> {
    private Date dateRep;
    private int cases;
    private int deaths;
    private String fk_countriesAndTerritories;
    private Double crecemento;

    public Date getDateRep() {
        return dateRep;
    }

    public void setDateRep(String dateRep) {
        try {
            this.dateRep = new SimpleDateFormat("dd/MM/yyyy").parse(dateRep);
        } catch (ParseException e) {
            System.out.println("Erro no formato de data");
            this.dateRep=null;
        }
    }
    public void setDateRep(Date d){
        this.dateRep=d;
    }

    public int getCases() {
        return cases;
    }

    public void setCases(int cases) {
        this.cases = cases;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public String getFk_countriesAndTerritories() {
        return fk_countriesAndTerritories;
    }

    public void setFk_countriesAndTerritories(String fk_countriesAndTerritories) {
        this.fk_countriesAndTerritories = fk_countriesAndTerritories;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Reporte)) return false;
        final Reporte r =(Reporte)obj;
        if(this.getDateRep().equals(r.getDateRep())&& this.getFk_countriesAndTerritories().equals(r.getFk_countriesAndTerritories())){
            return true;
        }else{
            return false;
        }
    }

    public int compareTo(Reporte o) {
        return this.getDateRep().compareTo(o.getDateRep());
    }

    public Double getCrecemento() {
        return crecemento;
    }

    public void setCrecemento(Double crecemento) {
        this.crecemento = crecemento;
    }

}
