-- Add bando and dificultad to RANURA for persistence
ALTER TABLE RANURA ADD COLUMN bando VARCHAR(50) DEFAULT 'survivientes';
ALTER TABLE RANURA ADD COLUMN dificultad VARCHAR(50) DEFAULT 'normal';
