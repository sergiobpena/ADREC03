package dao.sqlite;

import dao.ConexionDB;
import dao.interfaces.ReporteDAO;
import modelos.Pais;
import modelos.Reporte;

import java.io.File;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReporteDAOSQLite implements ReporteDAO {
    private final String INSERTAR = "INSERT INTO reportes(dateRep, cases , deaths , fk_countriesAndTerritories) VALUES(?,?,?,?);";
    private final String ACTUALIZAR = "UPDATE reportes SET cases=?,deaths=?  where fk_countriesAndTerritories=? AND dateRep=? ; ";
//    private final String BORRAR = "DELETE FROM reportes where id=?";
    private final String OBTER_UN = "SELECT * FROM reportes WHERE id=?";
    private final String OBTER_TODOS = "SELECT * FROM reportes";
    private final String OBTER_TODOS_PAIS="select * from reportes where fk_countriesAndTerritories=? order by dateRep ASC";
    private final String ENGADE_COLUMNA="alter table reportes add column incremento numeric;";
    private final String OBTEN_DATAS="select DISTINCT reportes.dateRep from reportes order by dateRep ASC ;";
    private Connection conexion;

    public ReporteDAOSQLite(Connection c) {
        this.conexion = c;
    }

    public void insertar(Reporte a) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = this.conexion.prepareStatement(INSERTAR);
            Date data=a.getDateRep();
            SimpleDateFormat sdf= new SimpleDateFormat("yyyy/MM/dd");
            stmt.setString(1, sdf.format(data));
            stmt.setInt(2, a.getCases());
            stmt.setInt(3, a.getDeaths());
            stmt.setString(4, a.getFk_countriesAndTerritories());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();

            this.conexion.rollback();

        } finally {
            if(stmt!=null) {


                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public Reporte obter(Reporte r) {
        return null;
    }

    public void actualizar(Reporte a) {
        PreparedStatement stmt= null;

        try {
            stmt=this.conexion.prepareStatement(ACTUALIZAR);
            stmt.setInt(1,a.getCases());
            stmt.setInt(2,a.getDeaths());
            stmt.setString(3,a.getFk_countriesAndTerritories());
            Date data=a.getDateRep();
            SimpleDateFormat sdf= new SimpleDateFormat("yyyy/MM/dd");
            stmt.setString(4,sdf.format(data));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean existe(Reporte a) {
        Boolean existe=false;
        String sql="select * from reportes where (fk_countriesAndTerritories=? and dateRep=?);";
        PreparedStatement stmt=null;

        try {
            stmt=this.conexion.prepareStatement(sql);
            stmt.setString(1,a.getFk_countriesAndTerritories());
            Date data=a.getDateRep();
            SimpleDateFormat sdf= new SimpleDateFormat("yyyy/MM/dd");
            stmt.setString(2,sdf.format(data));
            ResultSet rs= stmt.executeQuery();
            if (rs.next()){
                existe=true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return existe;
    }

    public ArrayList<Reporte> obterTodos() {
        Statement stmt=null;
        ArrayList<Reporte> reportes=new ArrayList<Reporte>();

        try {
            stmt=this.conexion.createStatement();
            ResultSet rs =stmt.executeQuery(OBTER_TODOS);
            while (rs.next()){
                reportes.add(this.convertir(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reportes;
    }

    public Reporte convertir(ResultSet rs) {
        Reporte r = new Reporte();

        try {

            r.setCases(rs.getInt("cases"));
            r.setDeaths(rs.getInt("deaths"));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            r.setDateRep(sdf.parse(rs.getString("dateRep")));
            r.setFk_countriesAndTerritories(rs.getString("fk_countriesAndTerritories"));
        } catch (SQLException e) {
            e.printStackTrace();
        }catch (ParseException e){
            try {
                System.out.println("Erro convertindo data " + rs.getString("dateRep"));
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return r;
    }

    public void crearTabla(){
        Statement stmt=null;
        try{
            String sql="create table if not exists reportes(\n" +
                    "  dateRep TEXT not null ,\n" +
                    "  cases integer,\n" +
                    "  deaths integer,\n" +
                    "  fk_countriesAndTerritories text not null ,\n" +
                    "  foreign key (fk_countriesAndTerritories) references paises (countriesAndTerritories)\n" +
                    "                                   on delete cascade\n" +
                    "                                   on update cascade,\n" +
                    "primary key (dateRep,fk_countriesAndTerritories)" +
                    ");";
            stmt=this.conexion.createStatement();
            stmt.execute(sql);
            this.conexion.commit();
        } catch (SQLException e) {
            System.out.println("Erro creando a taboa reportes");
            e.printStackTrace();
            try {
                this.conexion.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                System.out.println("Erro pechando a creacion da taboa reportes");
            }
        }
    }


    public void engadeColumna(){
        Statement stmt=null;

        try {
            stmt=this.conexion.createStatement();
            stmt.executeUpdate(ENGADE_COLUMNA);
        } catch (SQLException e) {
            try {
                if(stmt!=null){
                    stmt.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public ArrayList<Reporte> obterPorPais(Pais p){
        PreparedStatement stmt=null;
        ArrayList<Reporte> reportes = new ArrayList<Reporte>();
        Reporte dummie=null;

        try {
            stmt=this.conexion.prepareStatement(OBTER_TODOS_PAIS);
            stmt.setString(1,p.getCountriesAndTerritories());
            ResultSet rs= stmt.executeQuery();
            while (rs.next()){
                reportes.add(this.convertir(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reportes;
    }

    public ArrayList<Date> obterDiasConReporte(){
        ArrayList<Date> datas=new ArrayList<Date>();
        String sql= "SELECT distinct (dateRep) from reportes;";
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy/MM/dd");
        try {
            Statement stmt=this.conexion.createStatement();
            ResultSet rs=stmt.executeQuery(sql);
            while (rs.next()){
                datas.add(sdf.parse(rs.getString(1)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return datas;
    }

}
