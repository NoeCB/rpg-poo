package com.dbd.dao;

import com.dbd.core.Util;
import java.sql.SQLException;

/**
 * Clase de utilidades específica para la base de datos (DAO).
 * Contiene métodos comunes de ayuda como la impresión de errores SQL.
 * 
 * @author Luis Lázaro y Noelia Cantador
 * @version 1.0
 */
public class UtilDAO {

    /**
     * Imprime un mensaje de error SQL formateado y con colores para facilitar la lectura.
     * @param e Excepción SQL lanzada.
     */
    public static void imprimirErrorSQL(SQLException e) {
        System.out.println(Util.ROJO + "\n=== ERROR EN LA BASE DE DATOS ===" + Util.RESET);
        System.out.println(Util.AMARILLO + "Mensaje: " + Util.RESET + e.getMessage());
        System.out.println(Util.AMARILLO + "Código de Estado: " + Util.RESET + e.getSQLState());
        System.out.println(Util.AMARILLO + "Código de Error: " + Util.RESET + e.getErrorCode());
        System.out.println(Util.ROJO + "=================================\n" + Util.RESET);
    }
}
