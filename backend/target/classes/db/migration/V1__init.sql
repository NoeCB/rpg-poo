-- 1. Tabla de Estadísticas Globales
CREATE TABLE IF NOT EXISTS ESTADISTICAS_GLOBALES (
    faccion ENUM('superviviente', 'killer') PRIMARY KEY,
    victorias INT UNSIGNED DEFAULT 0
);

INSERT IGNORE INTO ESTADISTICAS_GLOBALES (faccion, victorias) VALUES ('superviviente', 0), ('killer', 0);

-- 2. Tabla de Ranuras de Guardado
CREATE TABLE IF NOT EXISTS RANURA (
    id_ranura INT PRIMARY KEY,
    fecha_guardado DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    ronda_actual INT UNSIGNED NOT NULL DEFAULT 1,
    modo_juego ENUM('manual', 'automatico'),
    terminada BOOLEAN DEFAULT FALSE, 
    vacia BOOLEAN DEFAULT TRUE
);

INSERT IGNORE INTO RANURA (id_ranura, vacia) VALUES (1, TRUE), (2, TRUE), (3, TRUE);

-- 3. Tabla de los Personajes
CREATE TABLE IF NOT EXISTS PERSONAJE_PARTIDA (
    id_personaje INT AUTO_INCREMENT PRIMARY KEY,
    id_ranura INT NOT NULL,
    clase_personaje VARCHAR(100) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    vida_actual INT UNSIGNED NOT NULL,
    vida_max INT UNSIGNED NOT NULL,
    defensa_base INT UNSIGNED NOT NULL,
    defendiendo BOOLEAN NOT NULL,
    bando ENUM('superviviente', 'killer') NOT NULL, 
    FOREIGN KEY (id_ranura) REFERENCES RANURA(id_ranura) ON DELETE CASCADE
);

-- 4. Tabla de armas portadas por los personajes
CREATE TABLE IF NOT EXISTS ARMA_EQUIPADA (
    id_arma INT AUTO_INCREMENT PRIMARY KEY,
    id_personaje INT NOT NULL,
    clase_arma VARCHAR(100) NOT NULL,
    nombre_arma VARCHAR(100) NOT NULL,
    danio_base INT UNSIGNED NOT NULL,
    precision_arma INT UNSIGNED NOT NULL,
    FOREIGN KEY (id_personaje) REFERENCES PERSONAJE_PARTIDA(id_personaje) ON DELETE CASCADE
);

-- 5. Tabla de habilidades/perks pertenecientes a un personaje
CREATE TABLE IF NOT EXISTS PERK_EQUIPADA (
    id_perk_equipado INT AUTO_INCREMENT PRIMARY KEY,
    id_personaje INT NOT NULL,
    clase_perk VARCHAR(100) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    usos_restantes INT UNSIGNED NOT NULL,
    FOREIGN KEY (id_personaje) REFERENCES PERSONAJE_PARTIDA(id_personaje) ON DELETE CASCADE
);

-- 6. Tabla de los estados activos
CREATE TABLE IF NOT EXISTS ESTADO_ACTIVO (
    id_estado INT AUTO_INCREMENT PRIMARY KEY,
    id_personaje INT NOT NULL,
    clase_estado VARCHAR(100) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    turnos_restantes INT UNSIGNED NOT NULL,
    FOREIGN KEY (id_personaje) REFERENCES PERSONAJE_PARTIDA(id_personaje) ON DELETE CASCADE
);

-- 7. Tabla de Logros Desbloqueables
CREATE TABLE IF NOT EXISTS LOGROS (
    id_logro INT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255) NOT NULL,
    conseguido BOOLEAN DEFAULT FALSE
);

INSERT IGNORE INTO LOGROS (id_logro, nombre, descripcion, conseguido) VALUES 
(1, 'Primera Sangre', 'Haz daño a un rival por primera vez', FALSE),
(2, 'Intocable', 'Gana una partida sin recibir daño', FALSE),
(3, 'Carnicero', 'Un Killer elimina a todos los supervivientes', FALSE),
(4, 'Superviviente Nato', 'Escapa (gana) como superviviente', FALSE),
(5, 'Trabajo en Equipo', 'Gana una partida con todos los supervivientes vivos', FALSE),
(6, 'Golpe Crítico', 'Asesta un golpe crítico con o sin arma', FALSE),
(7, 'Estratega', 'Usa una Perk con éxito', FALSE),
(8, 'Tanque', 'Bloquea un ataque con éxito usando la defensa', FALSE),
(9, 'Esquiva Matrix', 'Esquiva un ataque por completo', FALSE),
(10, 'Ataque Letal', 'Haz más de 40 de daño en un solo golpe', FALSE),
(11, 'Jugador Automático', 'Completa una partida en modo automático', FALSE),
(12, 'Jugador Manual', 'Completa una partida en modo manual', FALSE),
(13, 'El Ente Despierta', 'Juega tu primera partida', FALSE),
(14, 'Pacifista', 'Gana una partida sin realizar ningún ataque básico (solo Perks)', FALSE),
(15, 'Sobrevivir al Límite', 'Gana una partida con 1 de vida', FALSE),
(16, 'Armado y Peligroso', 'Usa un arma para atacar', FALSE),
(17, 'Mano Limpia', 'Derrota a un rival atacando sin arma', FALSE),
(18, 'Resistencia Férrea', 'Sobrevive a 10 rondas en una misma partida', FALSE),
(19, 'Veterano', 'Juega 5 partidas en total', FALSE),
(20, 'Maestro del Terror', 'Juega 10 partidas en total', FALSE);

-- 8. Tabla Histórico de Partidas
CREATE TABLE IF NOT EXISTS HISTORICO_PARTIDAS (
    id_hist INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
    modo_juego ENUM('manual', 'automatico'),
    rondas_totales INT UNSIGNED NOT NULL,
    bando_ganador ENUM('superviviente', 'killer')
);

-- 9. Tabla Histórico de Personajes
CREATE TABLE IF NOT EXISTS HISTORICO_PERSONAJES (
    id_hist_personaje INT AUTO_INCREMENT PRIMARY KEY,
    id_hist_partida INT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    bando ENUM('superviviente', 'killer') NOT NULL,
    vida_final INT UNSIGNED NOT NULL,
    FOREIGN KEY (id_hist_partida) REFERENCES HISTORICO_PARTIDAS(id_hist) ON DELETE CASCADE
);
