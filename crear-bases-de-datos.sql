-- Ejecutar este script en MySQL antes de levantar los microservicios
-- mysql -u root -p < crear-bases-de-datos.sql

CREATE DATABASE IF NOT EXISTS cigna_usuarios CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS cigna_servicios CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS cigna_agendas CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS cigna_reservas CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS cigna_tratamientos CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

SELECT 'Bases de datos creadas exitosamente' AS resultado;
SHOW DATABASES LIKE 'cigna_%';
