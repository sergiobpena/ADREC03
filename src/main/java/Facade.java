import dao.ControladorDAO;
import dao.xml.ConversorXML;
import modelos.Continente;

import java.io.File;
import java.util.Date;
import java.util.HashMap;

public class Facade {
    private static Facade facade;
    private String rutaDb;
    private String rutaXML;
    private String rutaXML_actualizado;

    private Facade(String rutaDb, String rutaXML, String rutaXML_actualizado) {
        this.rutaDb = rutaDb;
        this.rutaXML = rutaXML;
        this.rutaXML_actualizado = rutaXML_actualizado;
        File arq = new File(rutaDb);
        if (!arq.exists()) {
            System.out.println("Cargando os datos iniciais...");
            this.cargaEgarda();
        }
    }

    public void cargaEgarda() {
        ConversorXML conversorXML = new ConversorXML(rutaXML);
        HashMap<String, Continente> continenteHashMap = conversorXML.getContinentes();

        ControladorDAO controladorDAO = ControladorDAO.getControladorDao(rutaDb);
        controladorDAO.gardaContinentes(continenteHashMap);
        System.out.println("Datos iniciais cargados");

    }

    public static Facade getFacade(String rutadb, String rutaXML, String rutaXML_actualizado) {
        if (facade == null) {
            facade = new Facade(rutadb, rutaXML, rutaXML_actualizado);
        }
        return facade;
    }

    public void listaListaPaisesNumero(int numero) {
        ControladorDAO controladorDAO = ControladorDAO.getControladorDao(this.rutaDb);
        controladorDAO.obtenMaiorque(numero);
    }

    public void paisMaisCasosDia() {
        ControladorDAO controladorDAO = ControladorDAO.getControladorDao(this.rutaDb);
        controladorDAO.oberMaximosDia();
    }

    public void paisMaisCasosPorDia() {
        ControladorDAO.getControladorDao(this.rutaDb).obterPaisMaisCasosPorDia();
    }

    public void actualizarSerie() {
        ConversorXML conversorXML = new ConversorXML(this.rutaXML_actualizado);
        HashMap<String, Continente> continenteHashMap = conversorXML.getContinentes();
        ControladorDAO.getControladorDao(rutaDb).actualizaSerie(continenteHashMap.values());
    }

    public void maximoCrecementoPorDia(Date d) {
        ControladorDAO.getControladorDao(rutaDb).obterMaximoCrecementoPorDia(d);
    }

    public void pechaConexion() {
        ControladorDAO.getControladorDao(rutaDb).getCnnDb().desconectaBD();

    }
}