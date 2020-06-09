package dao.interfaces;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface PatronDao <T,K> {
    void insertar(T a) throws SQLException;
    T obter (T a);
    void actualizar(T a);
    boolean existe(T a);
    ArrayList<T> obterTodos();
    T convertir(ResultSet rs);
    void crearTabla();
}