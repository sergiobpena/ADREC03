package dao;

import dao.interfaces.ContinenteDAO;
import dao.interfaces.PaisDAO;
import dao.interfaces.ReporteDAO;
import dao.sqlite.ContinenteDAOSQLite;
import dao.sqlite.PaisDAOSQLite;
import dao.sqlite.ReporteDAOSQLite;
import modelos.Continente;
import modelos.Pais;
import modelos.Reporte;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Clase singleton para manexo das operacions en base de datos.
 * Instancia os daos dos obxecto e crea as taboas se non existen ó instancialo
 */
public class ControladorDAO {

    private static ControladorDAO controlador;
    private ConexionDB cnnDb=null;
    private PaisDAO pais;
    private ContinenteDAO continente;
    private ReporteDAOSQLite reporte;




    private ControladorDAO(String ruta){
        this.cnnDb=ConexionDB.getConexionDB(ruta);
        this.pais=new PaisDAOSQLite(this.cnnDb.getConexion());
        this.continente=new ContinenteDAOSQLite(this.cnnDb.getConexion());
        this.reporte=new ReporteDAOSQLite(this.cnnDb.getConexion());
        this.crearTablas();

    }

    private void crearTablas(){
        this.continente.crearTabla();
        this.pais.crearTabla();
        this.reporte.crearTabla();
    }
    public static ControladorDAO getControladorDao(String ruta){
        if (controlador == null){
            controlador=new ControladorDAO(ruta);
        }
        return controlador;
    }

    public PaisDAO getPais() {

        return pais;
    }

    public ContinenteDAO getContinente() {
        return continente;
    }

    public ReporteDAO getReporte() {
        return reporte;
    }

    public ConexionDB getCnnDb() {
        return cnnDb;
    }

    public void gardaContinentes(HashMap<String, Continente> continenteHashMap) {
        try {
            for (Continente c : continenteHashMap.values()) {
                this.continente.insertar(c);
                this.cnnDb.getConexion().commit();
                for (Pais p : c.getPaises().values()) {
                    p.setFk_continentExp(c.getContinentExp());
                    this.pais.insertar(p);
                    this.cnnDb.getConexion().commit();
                    for (Reporte r : p.getReportes().values()) {
                        r.setFk_countriesAndTerritories(p.getCountriesAndTerritories());
                        this.reporte.insertar(r);
                    }
                    this.cnnDb.getConexion().commit();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void obtenMaiorque(int casos){
        PreparedStatement stmt =null;
        ArrayList<Pais> paises=new ArrayList<Pais>();
        String sql = "select countriesAndTerritories , total_casos\n" +
                "    from\n" +
                "(select paises.countriesAndTerritories ,sum(reportes.cases) as total_casos\n" +
                "from paises\n" +
                "left join reportes on paises.countriesAndTerritories = reportes.fk_countriesAndTerritories\n" +
                "group by paises.countriesAndTerritories\n" +
                "    )\n" +
                "where total_casos >?;";
        try {
            stmt=this.cnnDb.getConexion().prepareStatement(sql);
            stmt.setInt(1,casos);
            ResultSet rs=stmt.executeQuery();
            while (rs.next()){
                System.out.println("Pais: " + rs.getString(1) + "\t Total de casos :" + rs.getInt(2));
            }
            System.out.println("\n");

        } catch (SQLException e) {
            e.printStackTrace();
        }
//        return paises;
    }
    public void oberMaximosDia(){
        Statement stmt=null;

        String sql="select paises.countriesAndTerritories,max( r.deaths), r.dateRep\n" +
                "from paises\n" +
                "left join reportes r on paises.countriesAndTerritories = r.fk_countriesAndTerritories\n" +
                "group by paises.countriesAndTerritories;";
        try {
            stmt=this.cnnDb.getConexion().createStatement();

            ResultSet rs=stmt.executeQuery(sql);
            while (rs.next()){
                System.out.println("Pais : " + rs.getString(1) + "\t Numero de mortos : " + rs.getInt(2) +"\t Data : " +  rs.getString(3));
            }
            System.out.println("\n");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public void paisMaisCasosDia(){
        Statement stmt = null;
        String sql = "";
    }


    public void obterPaisMaisCasosPorDia() {
        Statement stmt=null;
        String sql= "select paises.countriesAndTerritories,max( r.cases), r.dateRep\n" +
                "                from paises\n" +
                "                left join reportes r on paises.countriesAndTerritories = r.fk_countriesAndTerritories\n" +
                "                where r.cases>0\n" +
                "                group by r.dateRep\n" +
                "                order by r.dateRep;";
        try {
            stmt=this.cnnDb.getConexion().createStatement();
            ResultSet rs=stmt.executeQuery(sql);

            while (rs.next()){
                System.out.println("Pais : " + rs.getString(1) + "\t Numero de casos : " + rs.getInt(2)
                + "\t Data : " + rs.getString(3));
            }
            System.out.println("\n");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void obterMaximoCrecementoPorDia(Date data){

        //Obtencion dos obxetos pais
        ArrayList<Pais> paises=this.pais.obterTodos();
        //Obtendo a serie temporal completa
        ArrayList<Date> datas=this.reporte.obterDiasConReporte();
        if(!datas.contains(data)){
            System.out.println("Non hai rexistros do dia especificado");
            return;
        }
        //Obtéñense todolos datos e calculase o incremento de casos por dia en tanto por un
        for (Pais p : paises){
            ArrayList<Reporte> reportes=this.reporte.obterPorPais(p);
            Collections.sort(reportes);
            int casosAnterior=0;
            double crecemento=0.0;
            Iterator<Reporte> it=reportes.iterator();
            while (it.hasNext()){
                Reporte r= it.next();
                if (Double.valueOf(r.getCases()-Double.valueOf(casosAnterior))==0){
                    crecemento = 0 ;
                }else if (casosAnterior == 0){
                    crecemento=0.0;
                }else{
                    crecemento=(Double.valueOf(r.getCases())-Double.valueOf(casosAnterior))/Double.valueOf(casosAnterior);
                }
                r.setCrecemento(crecemento);
                p.setReportes(r);
                casosAnterior=r.getCases();
            }
        }
        //Arraylist con obxectos que encapsulan o par pais , crecemento , este obxecto ten o metodo compareTo definilo para logo
        //ordenar este arraylist
        ArrayList<CrecementoPais> listado= new ArrayList<CrecementoPais>();
        for (Pais p : paises){
            Double crecemento=0.0;
            Reporte x= p.obterPorData(data);
            if(x!=null){
                crecemento=x.getCrecemento();
            }
            listado.add(new CrecementoPais(p.getCountriesAndTerritories(),crecemento));
        }
        Collections.sort(listado);

        for(CrecementoPais c : listado){
            System.out.println("- Pais : " + c.getPais() + "\t Crecemento : " + c.getCrecemento()*100 );
        }
    }

    public void actualizaSerie(Collection<Continente> values) {
        try {
            for (Continente c : values) {
                if (!continente.existe(c)) {
                    continente.insertar(c);
                    this.cnnDb.getConexion().commit();
                }
                for(Pais p : c.getPaises().values()){
                    p.setFk_continentExp(c.getContinentExp());
                    if(!pais.existe(p)){
                        pais.insertar(p);
                        this.cnnDb.getConexion().commit();
                    }
                    for (Reporte r : p.getReportes().values()){
                        r.setFk_countriesAndTerritories(p.getCountriesAndTerritories());
                        if(!reporte.existe(r)){
                            reporte.insertar(r);
                        }else{
                            reporte.actualizar(r);
                        }
                    }
                    this.cnnDb.getConexion().commit();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //Clase auxiliar para encapsular o par pais crecemento.
    public class CrecementoPais implements Comparable<CrecementoPais>{
        private String pais;
        private Double crecemento;
        public CrecementoPais(String pais, Double crecemento){
            this.pais=pais;
            this.crecemento=crecemento;
        }

        public Double getCrecemento() {
            return crecemento;
        }

        public String getPais() {
            return pais;
        }

        public int compareTo(CrecementoPais o) {
            return this.crecemento.compareTo(o.getCrecemento());
        }
    }
}
