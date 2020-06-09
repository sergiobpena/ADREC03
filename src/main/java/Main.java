import java.io.File;


public class Main {
    public static void main(String[] args) {
        //Rutas dos arquivos
        String xml="coronavirus.xml";
        String db="coronavirus.db";
        String xml_actualizado="coronavirus_actualizado.xml";
        File database=new File((db));
        Interfaz intfaz=Interfaz.getInterfaz(database.getAbsolutePath(),xml,xml_actualizado);
        intfaz.selector();
    }
}
