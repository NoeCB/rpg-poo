-- 1. Crear tabla de relación para almacenar logros por usuario
CREATE TABLE IF NOT EXISTS USUARIOS_LOGROS (
    usuario_id BIGINT NOT NULL,
    id_logro INT NOT NULL,
    PRIMARY KEY (usuario_id, id_logro),
    CONSTRAINT fk_logros_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    CONSTRAINT fk_logros_id FOREIGN KEY (id_logro) REFERENCES LOGROS(id_logro) ON DELETE CASCADE
);

-- 2. Migrar los logros globales previamente conseguidos al usuario por defecto (ID 1)
INSERT IGNORE INTO USUARIOS_LOGROS (usuario_id, id_logro)
SELECT 1, id_logro FROM LOGROS WHERE conseguido = TRUE;
