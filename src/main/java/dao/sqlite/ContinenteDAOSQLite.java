package dao.sqlite;

import dao.interfaces.ContinenteDAO;
import modelos.Continente;

import java.sql.*;
import java.util.ArrayList;

public class ContinenteDAOSQLite implements ContinenteDAO {
    private final String INSERTAR="INSERT INTO continentes(continentExp) VALUES(?)";


    private Connection con;

    public ContinenteDAOSQLite(Connection con){
        this.con=con;
    }

    public void insertar(Continente a) throws SQLException {
        PreparedStatement stmt=null;
        try {
            stmt=this.con.prepareStatement(INSERTAR);
            stmt.setString(1,a.getContinentExp());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro insertando continente");
            e.printStackTrace();
            this.con.rollback();

        }

    }

    public Continente obter(Continente a) {
        return null;
    }

    public void actualizar(Continente a) {

    }

    public boolean existe(Continente a) {
        Boolean existe=false;
        String sql="select * from continentes where continentExp=?";
        PreparedStatement stmt=null;

        try {
            stmt=this.con.prepareStatement(sql);
            stmt.setString(1,a.getContinentExp());
            ResultSet rs= stmt.executeQuery();
            if(rs.next()){
                existe=true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return existe;
    }

    public ArrayList<Continente> obterTodos() {
        return null;
    }

    public Continente convertir(ResultSet rs) {
        Continente c = new Continente();
        try {
            c.setContinentExp(rs.getString("continentExp"));

        } catch (SQLException e) {
            System.out.println("Erro de conversion de continenete");
        }
        return c;
    }

    public void crearTabla(){
        Statement stmt=null;
        try{
            String sql = "create table if not exists continentes(\n" +
                    "  continentExp text primary key \n" +
                    "\n" +
                    ");";

            stmt = this.con.createStatement();
            stmt.execute(sql);
            this.con.commit();
        }
        catch(SQLException e){
            System.out.println("Erro creando a taboa continentes");;
            try {
                this.con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
