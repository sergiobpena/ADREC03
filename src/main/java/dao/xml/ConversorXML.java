package dao.xml;

import modelos.Continente;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;
import java.util.HashMap;

/**
 * Clase que convirte os XML en mapas de continentes
 */
public class ConversorXML {
    private String rutaXML;
    public ConversorXML(String rutaXML){
        this.rutaXML=rutaXML;
    }

    public HashMap<String, Continente> getContinentes () {
        XMLReader procedadorXML = null;
        HashMap<String, Continente> continentes = new HashMap<String, Continente>();
        try {
            procedadorXML = XMLReaderFactory.createXMLReader();
            ManexadorXML parseadorXML = new ManexadorXML();
            procedadorXML.setContentHandler(parseadorXML);
            InputSource arquivo = new InputSource(rutaXML);
            procedadorXML.parse(arquivo);
            continentes = parseadorXML.getContinentes();

        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return continentes;
    }

}
