package com.dbd.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Gestiona la conexión a la base de datos MySQL para hacer el CRUD del juego.
 */
public class ConexionDB {

    private static final String URL = "jdbc:mysql://localhost:3306/dbd_rpg";
    private static final String USER = "root";
    private static final String PASS = "";

    /**
     * Establece la conexión con la base de datos.
     * @return Connection activa
     * @throws SQLException Si falla la conexión
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
