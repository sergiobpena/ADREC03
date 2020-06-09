package dao.xml;
import modelos.Continente;
import modelos.Pais;
import modelos.Reporte;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.HashMap;

public class ManexadorXML extends DefaultHandler{
    public HashMap<String, Continente> getContinentes() {
        return continentes;
    }

    private HashMap<String, Continente> continentes=new HashMap<String, Continente>();
    private Reporte reporte;
    private Pais pais;
    private Continente continente;
    private String cadeaTexto;


    public ManexadorXML(){
        super();
    }
    @Override
    public void startDocument()throws SAXException {

    }
    @Override
    public void endDocument() throws SAXException {}
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (localName=="record"){
            this.reporte=new Reporte();
            this.pais=new Pais();
            this.continente=new Continente();
        }
    }
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException
    {

        if(localName == "cases"){
            this.reporte.setCases(Integer.valueOf(this.cadeaTexto));
        }
        else if(localName == "deaths"){
            this.reporte.setDeaths(Integer.valueOf(this.cadeaTexto));
        }
        else if(localName == "dateRep"){
           this.reporte.setDateRep(this.cadeaTexto);
        }else if(localName=="popData2018"){
            this.pais.setPopData2018(this.cadeaTexto);
        }else if (localName=="countriesAndTerritories"){
            this.pais.setCountriesAndTerritories(this.cadeaTexto);
        }else if(localName=="countryterritoryCode"){
            this.pais.setCountryterritoryCode(this.cadeaTexto);
        }else if(localName=="record"){

            this.pais.setR(this.reporte);
            this.reporte.setFk_countriesAndTerritories(pais.getCountriesAndTerritories());
            this.pais.setFk_continentExp(this.continente.getContinentExp());
            if(this.continentes.containsKey(this.continente.getContinentExp())){
                this.continentes.get(this.continente.getContinentExp()).engadeReporte(this.pais);
            }else{
                this.continente.engadeReporte(this.pais);
                this.continentes.put(this.continente.getContinentExp(),this.continente);
            }
        }else if(localName=="continentExp"){
            this.continente.setContinentExp(this.cadeaTexto);

        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        //Gardamos todo o texto entre caracteres na cadea de texto auxiliar
        this.cadeaTexto = new String(ch,start,length);
    }
}
