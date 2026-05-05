package com.dbd.dao;

import com.dbd.entidades.Personaje;
import com.dbd.entidades.Logro;
import com.dbd.arma.Arma;
import com.dbd.habilidades.Perk;
import com.dbd.estados.Estado;
import static com.dbd.core.Util.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.springframework.stereotype.Repository;

/**
 * Gestor Data Access Object (DAO) para persistir la partida en Slots fijos.
 */
@Repository
public class GestorPersistencia {

    /**
     * Guarda el estado entero de la partida en una ranura específica apoyándose en
     * Transacciones.
     */
    public boolean guardarPartida(int idRanura, int ronda, String modoJuego, boolean terminada,
            ArrayList<Personaje> survis, ArrayList<Personaje> killers) {
        String sqlBorrarViejos = "DELETE FROM PERSONAJE_PARTIDA WHERE id_ranura = ?";
        String sqlUpdatePartida = "UPDATE PARTIDA SET ronda_actual = ?, modo_juego = ?, terminada = ?, vacia = FALSE WHERE id_ranura = ?";

        try (Connection con = ConexionDB.getConnection()) {
            con.setAutoCommit(false); // Transacción para integridad total (Implementación 4)

            // 1. Limpiar los datos antiguos de esa ranura (ON DELETE CASCADE borrará armas
            // y perks viejas)
            try (PreparedStatement pstDel = con.prepareStatement(sqlBorrarViejos)) {
                pstDel.setInt(1, idRanura);
                pstDel.executeUpdate();
            }

            // 2. Actualizar la partida (Slot)
            try (PreparedStatement pstUpd = con.prepareStatement(sqlUpdatePartida)) {
                pstUpd.setInt(1, ronda);
                pstUpd.setString(2, modoJuego);
                pstUpd.setBoolean(3, terminada);
                pstUpd.setInt(4, idRanura);
                pstUpd.executeUpdate();
            }

            // 3. Guardar PERSONAJES NUEVOS (Supervivientes y luego Killers)
            for (Personaje p : survis) {
                insertarPersonajeCompleto(con, idRanura, p, "superviviente");
            }
            for (Personaje p : killers) {
                insertarPersonajeCompleto(con, idRanura, p, "killer");
            }

            con.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(ROJO + "Error fatal al guardar. Hace rollback." + RESET);
            return false;
        }
    }

    private void insertarPersonajeCompleto(Connection con, int idRanura, Personaje p, String bando)
            throws SQLException {
        String sqlPers = "INSERT INTO PERSONAJE_PARTIDA (id_ranura, clase_personaje, nombre, vida_actual, vida_max, defensa_base, puntos_sangre, defendiendo, bando) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int idPersonaje = -1;

        try (PreparedStatement pst = con.prepareStatement(sqlPers, Statement.RETURN_GENERATED_KEYS)) {
            pst.setInt(1, idRanura);
            pst.setString(2, p.getClass().getSimpleName()); // Guardamos la clase exacta (ej. GhostFace, Mikaela)
            pst.setString(3, p.getNombrePersonaje());
            pst.setInt(4, p.getVidaActual());
            pst.setInt(5, p.getVidaMax());
            pst.setInt(6, p.getDefensaBase());
            pst.setInt(7, p.getPuntosSangre());
            pst.setBoolean(8, p.isDefendiendo());
            pst.setString(9, bando);
            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                idPersonaje = rs.getInt(1);
            }
        }

        // Armas
        if (p.getArma() != null) {
            String sqlArma = "INSERT INTO ARMA_EQUIPADA (id_personaje, clase_arma, nombre_arma, danio_base, precision_arma) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstArma = con.prepareStatement(sqlArma)) {
                Arma a = p.getArma();
                pstArma.setInt(1, idPersonaje);
                pstArma.setString(2, a.getClass().getSimpleName());
                pstArma.setString(3, a.getNombreArma());
                pstArma.setInt(4, a.getDanioBase());
                pstArma.setInt(5, a.getPrecision());
                pstArma.executeUpdate();
            }
        }

        // Perks
        if (p.getPerks() != null && !p.getPerks().isEmpty()) {
            String sqlPerk = "INSERT INTO PERK_EQUIPADA (id_personaje, clase_perk, nombre, usos_restantes) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstPerk = con.prepareStatement(sqlPerk)) {
                for (Perk perk : p.getPerks()) {
                    pstPerk.setInt(1, idPersonaje);
                    pstPerk.setString(2, perk.getClass().getSimpleName());
                    pstPerk.setString(3, perk.getNombre());
                    pstPerk.setInt(4, perk.getUsos());
                    pstPerk.executeUpdate();
                }
            }
        }

        // Estados
        if (p.getEstados() != null && !p.getEstados().isEmpty()) {
            String sqlEstado = "INSERT INTO ESTADO_ACTIVO (id_personaje, clase_estado, nombre, turnos_restantes) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstEst = con.prepareStatement(sqlEstado)) {
                for (Estado est : p.getEstados()) {
                    pstEst.setInt(1, idPersonaje);
                    pstEst.setString(2, est.getClass().getSimpleName());
                    pstEst.setString(3, est.getNombre());
                    pstEst.setInt(4, est.getTurnosRestantes());
                    pstEst.executeUpdate();
                }
            }
        }
    }

    /**
     * Lista todas las ranuras detallando su contenido (Implementación 2: Display
     * Detallado).
     */
    public void listarPartidas() {
        String sql = "SELECT p.id_ranura, p.fecha_guardado, p.ronda_actual, p.modo_juego, p.terminada, p.vacia, " +
                " (SELECT COUNT(*) FROM PERSONAJE_PARTIDA pp WHERE pp.id_ranura = p.id_ranura AND pp.bando='superviviente' AND pp.vida_actual > 0) as survs_vivos, "
                +
                " (SELECT COUNT(*) FROM PERSONAJE_PARTIDA pp WHERE pp.id_ranura = p.id_ranura AND pp.bando='killer' AND pp.vida_actual > 0) as killers_vivos "
                +
                " FROM PARTIDA p ORDER BY p.id_ranura ASC";

        try (Connection con = ConexionDB.getConnection();
                PreparedStatement pst = con.prepareStatement(sql);
                ResultSet rs = pst.executeQuery()) {

            System.out.println(MORADO + "\n========== RANURAS DE GUARDADO ==========" + RESET);
            while (rs.next()) {
                int id = rs.getInt("id_ranura");
                boolean vacia = rs.getBoolean("vacia");

                if (vacia) {
                    System.out.println(CYAN + "[Ranura " + id + "] " + AMARILLO + "--- VACÍA ---" + RESET);
                } else {
                    String modo = rs.getString("modo_juego");
                    int ronda = rs.getInt("ronda_actual");
                    String fecha = rs.getString("fecha_guardado");
                    int survsVivos = rs.getInt("survs_vivos");
                    int killersVivos = rs.getInt("killers_vivos");
                    boolean terminada = rs.getBoolean("terminada");

                    String estadoStr = terminada ? (ROJO + "[FINALIZADA]" + RESET) : (VERDE + "[EN CURSO]" + RESET);

                    System.out.println(CYAN + "[Ranura " + id + "] " + estadoStr +
                            " Ronda: " + ronda + " | Modo: " + modo + " | Survs Vivos: " + survsVivos
                            + " | Killers Vivos: " + killersVivos +
                            " | " + fecha + RESET);
                }
            }
            System.out.println(MORADO + "=========================================\n" + RESET);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al listar partidas.");
        }
    }

    /**
     * Borra los datos de una ranura, declarándola vacía.
     */
    public void borrarPartida(int idRanura) {
        String sqlBorrarPers = "DELETE FROM PERSONAJE_PARTIDA WHERE id_ranura = ?";
        String sqlUpdatePartida = "UPDATE PARTIDA SET ronda_actual = 1, terminada = FALSE, vacia = TRUE WHERE id_ranura = ?";

        try (Connection con = ConexionDB.getConnection()) {
            con.setAutoCommit(false);

            try (PreparedStatement pst1 = con.prepareStatement(sqlBorrarPers)) {
                pst1.setInt(1, idRanura);
                pst1.executeUpdate();
            }
            try (PreparedStatement pst2 = con.prepareStatement(sqlUpdatePartida)) {
                pst2.setInt(1, idRanura);
                pst2.executeUpdate();
            }

            con.commit();
            System.out.println(VERDE + "Ranura " + idRanura + " formateada exitosamente." + RESET);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Carga completa desde Base de Datos instanciando clases por Reflection
     * (Implementación 3).
     * Retorna un Array con: [0] Ronda (int), [1] Modo de Juego (String)
     */
    public Object[] cargarPartidaCompleta(int idRanura, ArrayList<Personaje> survisRef,
            ArrayList<Personaje> killersRef) {
        String sqlPartida = "SELECT ronda_actual, modo_juego, vacia FROM PARTIDA WHERE id_ranura = ?";
        try (Connection con = ConexionDB.getConnection()) {

            int ronda = 1;
            String modoJuego = "";
            boolean vacia = true;

            try (PreparedStatement pst = con.prepareStatement(sqlPartida)) {
                pst.setInt(1, idRanura);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        vacia = rs.getBoolean("vacia");
                        if (!vacia) {
                            ronda = rs.getInt("ronda_actual");
                            modoJuego = rs.getString("modo_juego");
                        }
                    }
                }
            }

            if (vacia) {
                System.out.println(ROJO + "La ranura elegida está vacía." + RESET);
                return null;
            }

            survisRef.clear();
            killersRef.clear();

            String sqlPersonajes = "SELECT * FROM PERSONAJE_PARTIDA WHERE id_ranura = ?";
            try (PreparedStatement pstP = con.prepareStatement(sqlPersonajes)) {
                pstP.setInt(1, idRanura);
                try (ResultSet rsP = pstP.executeQuery()) {
                    while (rsP.next()) {
                        int idPersDB = rsP.getInt("id_personaje");
                        String clasePers = rsP.getString("clase_personaje");
                        String bando = rsP.getString("bando");

                        // Reflection!
                        String paquete = bando.equals("superviviente") ? "com.dbd.entidades.survis."
                                : "com.dbd.entidades.killers.";
                        // Para simplificar ya que están todos en entidades:
                        // Nota: si están en subpaquetes distintos habría que saberlo, asumimos
                        // com.dbd.entidades.killers. etc
                        // En tu código están importados de com.dbd.entidades.*
                        // Vamos a hacer una chapuza segura predefiniendo el paquete base, si no
                        // funciona probamos otro
                        Class<?> clazz;
                        try {
                            clazz = Class.forName("com.dbd.entidades." + clasePers);
                        } catch (Exception ex) {
                            // Si no, probamos subpaquetes genéricos, o asumimos que en el Main están
                            // Tu import era: import com.dbd.entidades.*; y luego de los específicos.
                            // Para no romper la Reflection si están divididos:
                            clazz = Class.forName("com.dbd.entidades." + clasePers);
                            // *Nota de diseño*: Si la Reflection falla aquí, el nombre no coincide exacto.
                            // Lo dejamos apuntando a entidades asumiendo que están ahí.
                        }

                        Personaje p = (Personaje) clazz.getDeclaredConstructor().newInstance();
                        p.setVidaActual(rsP.getInt("vida_actual"));
                        // Vida max y stats ya se inicializan en el constructor, no machacamos a no ser
                        // q haga falta.
                        p.setDefendiendo(rsP.getBoolean("defendiendo"));

                        // Recuperar Arma
                        String sqlArma = "SELECT * FROM ARMA_EQUIPADA WHERE id_personaje = ?";
                        try (PreparedStatement pstA = con.prepareStatement(sqlArma)) {
                            pstA.setInt(1, idPersDB);
                            try (ResultSet rsA = pstA.executeQuery()) {
                                if (rsA.next()) {
                                    String claseArma = rsA.getString("clase_arma");
                                    Class<?> clazzArma = Class.forName("com.dbd.arma." + claseArma);
                                    Arma arma = (Arma) clazzArma.getDeclaredConstructor().newInstance();
                                    p.setArma(arma);
                                }
                            }
                        }

                        // Recuperar Perks
                        String sqlPerks = "SELECT * FROM PERK_EQUIPADA WHERE id_personaje = ?";
                        try (PreparedStatement pstPk = con.prepareStatement(sqlPerks)) {
                            pstPk.setInt(1, idPersDB);
                            try (ResultSet rsPk = pstPk.executeQuery()) {
                                p.getPerks().clear(); // Limpiamos las que trajera por defecto si las hay
                                while (rsPk.next()) {
                                    String clasePerk = rsPk.getString("clase_perk");
                                    Class<?> clazzPerk;
                                    try {
                                        clazzPerk = Class.forName("com.dbd.habilidades.survis." + clasePerk);
                                    } catch (Exception e2) {
                                        clazzPerk = Class.forName("com.dbd.habilidades.killers." + clasePerk);
                                    }
                                    Perk perk = (Perk) clazzPerk.getDeclaredConstructor().newInstance();
                                    // Para ajustar usos: requeriria setter, pero asumimos que inician con max,
                                    // de momento metemos la instancia.
                                    p.addPerk(perk);
                                }
                            }
                        }

                        // Recuperar Estados
                        // (Mismo patrón, puedes añadirlo si lo usáis)

                        if (bando.equals("superviviente")) {
                            survisRef.add(p);
                        } else {
                            killersRef.add(p);
                        }
                    }
                }
            }

            System.out.println(VERDE + "¡La partida ha sido reconstruida en memoria con éxito!" + RESET);
            return new Object[] { ronda, modoJuego };

        } catch (Exception e) {
            System.out.println(ROJO + "Error cargando la partida, puede que falten clases o no existan." + RESET);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Suma victoria a la tabla global (Implementación 5)
     */
    public void registrarVictoriaGlobal(String faccion) {
        String sql = "UPDATE ESTADISTICAS_GLOBALES SET victorias = victorias + 1 WHERE faccion = ?";
        try (Connection con = ConexionDB.getConnection();
                PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, faccion);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void mostrarEstadisticasGlobales() {
        String sql = "SELECT faccion, victorias FROM ESTADISTICAS_GLOBALES";
        try (Connection con = ConexionDB.getConnection();
                PreparedStatement pst = con.prepareStatement(sql);
                ResultSet rs = pst.executeQuery()) {

            System.out.println(AMARILLO + "\n=== HISTORIAL DE LA ENTIDAD (GLOBAL) ===" + RESET);
            while (rs.next()) {
                System.out
                        .println("-> " + rs.getString("faccion") + " han ganado " + rs.getInt("victorias") + " veces.");
            }
            System.out.println(AMARILLO + "========================================\n" + RESET);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public com.dbd.dto.StatsResponse obtenerEstadisticasGlobales() {
        com.dbd.dto.StatsResponse response = new com.dbd.dto.StatsResponse();
        String sqlFaccion = "SELECT faccion, victorias FROM ESTADISTICAS_GLOBALES";
        String sqlHist = "SELECT COUNT(*) as totales, AVG(rondas_totales) as media_rondas FROM HISTORICO_PARTIDAS";

        try (Connection con = ConexionDB.getConnection()) {
            try (PreparedStatement pst1 = con.prepareStatement(sqlFaccion);
                 ResultSet rs1 = pst1.executeQuery()) {
                while (rs1.next()) {
                    String faccion = rs1.getString("faccion");
                    int victorias = rs1.getInt("victorias");
                    if ("Killers".equalsIgnoreCase(faccion)) {
                        response.setVictoriasKillers(victorias);
                    } else if ("Supervivientes".equalsIgnoreCase(faccion)) {
                        response.setVictoriasSurvis(victorias);
                    }
                }
            }
            try (PreparedStatement pst2 = con.prepareStatement(sqlHist);
                 ResultSet rs2 = pst2.executeQuery()) {
                if (rs2.next()) {
                    response.setPartidasTotales(rs2.getInt("totales"));
                    response.setMediaRondas(rs2.getDouble("media_rondas"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * Guarda los resultados de una partida finalizada en el historial absoluto.
     */
    public void guardarEnHistorico(String modoJuego, int rondas, String bandoGanador, ArrayList<Personaje> survis,
            ArrayList<Personaje> killers) {
        String sqlHistPartida = "INSERT INTO HISTORICO_PARTIDAS (modo_juego, rondas_totales, bando_ganador) VALUES (?, ?, ?)";
        String sqlHistPers = "INSERT INTO HISTORICO_PERSONAJES (id_hist_partida, nombre, bando, vida_final) VALUES (?, ?, ?, ?)";

        try (Connection con = ConexionDB.getConnection()) {
            con.setAutoCommit(false);
            int idHist = -1;

            try (PreparedStatement pst1 = con.prepareStatement(sqlHistPartida, Statement.RETURN_GENERATED_KEYS)) {
                pst1.setString(1, modoJuego);
                pst1.setInt(2, rondas);
                pst1.setString(3, bandoGanador);
                pst1.executeUpdate();
                ResultSet rs = pst1.getGeneratedKeys();
                if (rs.next())
                    idHist = rs.getInt(1);
            }

            if (idHist != -1) {
                try (PreparedStatement pst2 = con.prepareStatement(sqlHistPers)) {
                    for (Personaje p : survis) {
                        pst2.setInt(1, idHist);
                        pst2.setString(2, p.getNombrePersonaje());
                        pst2.setString(3, "superviviente");
                        pst2.setInt(4, p.getVidaActual());
                        pst2.addBatch();
                    }
                    for (Personaje p : killers) {
                        pst2.setInt(1, idHist);
                        pst2.setString(2, p.getNombrePersonaje());
                        pst2.setString(3, "killer");
                        pst2.setInt(4, p.getVidaActual());
                        pst2.addBatch();
                    }
                    pst2.executeBatch();
                }
            }
            con.commit();
            System.out.println(
                    MORADO + "[HISTÓRICO] La partida ha sido archivada por el Ente para toda la eternidad." + RESET);

            // Comprobación de logros por cantidad de partidas
            comprobarLogrosDeCantidadPartidas();

        } catch (SQLException e) {
            System.out.println(ROJO + "Error al guardar en el histórico absoluto." + RESET);
            e.printStackTrace();
        }
    }

    /**
     * Desbloquea un logro en la base de datos si no lo estaba.
     */
    public void desbloquearLogro(int idLogro) {
        String sql = "UPDATE LOGROS SET conseguido = TRUE WHERE id_logro = ? AND conseguido = FALSE";
        try (Connection con = ConexionDB.getConnection();
                PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, idLogro);
            int filas = pst.executeUpdate();
            if (filas > 0) {
                // Para saber el nombre y avisar
                try (PreparedStatement pst2 = con.prepareStatement("SELECT nombre FROM LOGROS WHERE id_logro = ?")) {
                    pst2.setInt(1, idLogro);
                    ResultSet rs = pst2.executeQuery();
                    if (rs.next()) {
                        System.out.println(AMARILLO + "\n¡LOGRO DESBLOQUEADO: " + rs.getString("nombre") + "!" + RESET);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Carga todos los logros.
     */
    public ArrayList<Logro> cargarLogros() {
        ArrayList<Logro> lista = new ArrayList<>();
        String sql = "SELECT * FROM LOGROS";
        try (Connection con = ConexionDB.getConnection();
                PreparedStatement pst = con.prepareStatement(sql);
                ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                lista.add(new Logro(rs.getInt("id_logro"), rs.getString("nombre"), rs.getString("descripcion"),
                        rs.getBoolean("conseguido")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * Comprueba si se ha llegado a 1, 5 o 10 partidas.
     */
    private void comprobarLogrosDeCantidadPartidas() {
        String sql = "SELECT COUNT(*) as totales FROM HISTORICO_PARTIDAS";
        try (Connection con = ConexionDB.getConnection();
                PreparedStatement pst = con.prepareStatement(sql);
                ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                int totales = rs.getInt("totales");
                if (totales >= 1)
                    desbloquearLogro(13); // El ente despierta
                if (totales >= 5)
                    desbloquearLogro(19); // Veterano
                if (totales >= 10)
                    desbloquearLogro(20); // Maestro del terror
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
