package dao.sqlite;

import dao.interfaces.PaisDAO;
import modelos.Pais;

import java.sql.*;
import java.util.ArrayList;

public class PaisDAOSQLite implements PaisDAO {

    private Connection conn;
    private final String INSERTAR="INSERT INTO paises(popData2018,countriesAndTerritories,countryterritoryCode,fk_continentExp) " +
            "VALUES(?,?,?,?)";
    private final String OBTER_TODOS="SELECT * FROM PAISES";


    public PaisDAOSQLite(Connection conn){
        this.conn=conn;
    }

    public void insertar(Pais a) throws SQLException {
        PreparedStatement stmt=null;
        try {
            stmt=this.conn.prepareStatement(INSERTAR);
            stmt.setString(1,a.getPopData2018());
            stmt.setString(2,a.getCountriesAndTerritories());
            stmt.setString(3,a.getCountryterritoryCode());
            stmt.setString(4,a.getFk_continentExp());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro insertando pais");
            System.out.println("Pais: " + a.getCountryterritoryCode() + "\t Continenente : "+  a.getFk_continentExp());

            this.conn.rollback();


        }finally {
            if(stmt!=null){
                try {
                    stmt.close();
                } catch (SQLException e) {
//                    e.printStackTrace();
                }
            }
        }

    }

    public Pais obter(Pais a) {
        return null;
    }

    public void actualizar(Pais a) {

    }

    public boolean existe(Pais a) {
        Boolean existe=false;
        String sql = "Select * from paises where countriesAndTerritories = ? ; ";
        PreparedStatement stmt=null;

        try {
            stmt=this.conn.prepareStatement(sql);
            stmt.setString(1,a.getCountriesAndTerritories());
            ResultSet rs= stmt.executeQuery();
            if(rs.next()){
                existe=true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return existe;
    }

    public ArrayList<Pais> obterTodos() {
        Statement stmt= null;
        ArrayList<Pais> paises= new ArrayList<Pais>();

        try {
            stmt=this.conn.createStatement();
            ResultSet rs=stmt.executeQuery(OBTER_TODOS);
            while (rs.next()){
                paises.add(this.convertir(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return paises;
    }

    public Pais convertir(ResultSet rs) {
        Pais p = new Pais();
        try {
            p.setPopData2018(rs.getString("popData2018"));
            p.setCountriesAndTerritories(rs.getString("countriesAndTerritories"));
            p.setCountryterritoryCode(rs.getString("countryterritoryCode"));
            p.setFk_continentExp(rs.getString("fk_continentExp"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p;
    }

    public void crearTabla(){
        Statement stmt=null;
        try{
            String sql="create table if not exists paises(\n" +
                    "  popData2018 text,\n" +
                    "  countriesAndTerritories text PRIMARY KEY,\n" +
                    "countryterritoryCode text,\n" +
                    "fk_continentExp text not null,\n" +
                    "foreign key (fk_continentExp) references continentes (continenteExp)\n" +
                    "                                 on delete cascade\n" +
                    "                                 on update cascade\n" +
                    ");";
            stmt = this.conn.createStatement();
            stmt.execute(sql);
            this.conn.commit();
        } catch (SQLException e) {
            System.out.println("Erro creando a taboa paises");
            e.printStackTrace();
            try {
                this.conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                System.out.println("Erro pechando a creacion de paises");
            }
        }


    }
}
