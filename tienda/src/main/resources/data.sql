-- Inserción de datos de prueba
INSERT IGNORE INTO genero (id, denominacion)
VALUES (1, 'Femenino'), (2, 'Masculino'), (3, 'No binario'), (4, 'Otro');
INSERT IGNORE INTO tipo_documento_cliente (id, denominacion)
VALUES (1, 'DNI'), (2, 'NIE'), (3, 'Nº pasaporte'), (4, 'Nº Seguridad social');
INSERT IGNORE INTO tipo_documento_proveedor (id, denominacion)
VALUES (1, 'CIF'), (2, 'DNI');
INSERT IGNORE INTO tipo_via (id, denominacion)
VALUES (1, 'Avenida'), (2, 'Calle'), (3, 'Glorieta'), (4, 'Paseo'), (5, 'Plaza');
INSERT IGNORE INTO tipo_cliente (id, tipo_fidelizacion)
VALUES (1, 'Bronce'), (2, 'Plata'), (3, 'Oro'), (4, 'Platino');
INSERT IGNORE INTO clase_proveedor (id, denominacion)
VALUES (1, 'Bronce'), (2, 'Plata'), (3, 'Oro'), (4, 'Platino');
INSERT IGNORE INTO tipo_tarjeta_credito (id, denominacion)
VALUES (1, 'VISA'), (2, 'Master Card'), (3, 'American Express');
INSERT IGNORE INTO urgencia_aviso (id, denominacion)
VALUES (1, 'Baja'), (2, 'Media'), (3, 'Alta');
INSERT IGNORE INTO pais (id, siglas, nombre)
VALUES (1, 'es', 'España'), (2, 'fr', 'Francia'), (3, 'it', 'Italia'), (3, 'pt', 'Portugal');
INSERT IGNORE INTO idioma (id, siglas, idioma)
VALUES (1, 'es', 'Español'), (2, 'en', 'English');
INSERT IGNORE INTO pregunta_recuperacion (id, pregunta)
VALUES (1, '¿Cómo se llamaba tu primera mascota?'), (2, '¿Cuál es el nombre del colegio en el que estudiaste?'),
       (3, '¿Cuál es el nombre de tu hermano mayor?');
INSERT IGNORE INTO recuperacion_clave (respuesta, pregunta_id)
VALUES ('Juan', 3), ('Tortuga', 1);
INSERT IGNORE INTO concepto (id, denominacion)
VALUES (1, 'Plus nocturnidad'), (2, 'Dietas'), (3, 'Gastos de locomoción'), (4, 'IRPF');
INSERT IGNORE INTO motivo_bloqueo (id, motivo, minutos_bloqueo)
VALUES (1, 'demasiados intentos de sesión fallidos', 15), (2, 'mantenimiento de la aplicación', 120),
       (3, 'uso inadecuado de la aplicación', 240);
INSERT IGNORE INTO nomina (id, annio, ingreso_liquido, mes, usuario_empleado_cliente_id)
VALUES (1, 2014, 1111.11, 1, 1), (2, 2014, 2121.21, 2, 1), (3, 2014, 2222.22, 3, 1);
INSERT IGNORE INTO linea_nomina (id, importe, tipo_id, linea_nominas_id)
VALUES (1, 300, 1, 1), (2, 500, 2, 1), (3, 700, 3, 1), (4, 300, 4, 2), (5, 500, 2, 2), (6, 700, 4, 3);

-- Inserción de varios usuarios administrador
INSERT IGNORE INTO administrador (id, email, clave, confirmar_clave)
VALUES (1, 'admin1@poketienda.com', '1234', '1234'), (2, 'admin2@poketienda.com', '1234', '1234');

-- Inserción de varios usuarios empleado/cliente
INSERT IGNORE INTO usuario_empleado_cliente (id, email, clave, confirmar_clave, recuperacion_clave_id, baja, intentos_fallidos_login)
VALUES (1, 'emp1@gmail.com', 'Patata!53', 'Patata!53', 1, 0, 0), (2, 'emp2@gmail.com', 'Patata!53', 'Patata!53', 2, 0, 0);

-- Inserción de varios clientes
INSERT IGNORE INTO cliente (id, nombre, apellidos, telefono_movil, usuario_id, tipo_cliente_id, gasto_acumulado_cliente, fecha_nacimiento)
VALUES (1, 'Eduardo', 'Keyholder', 666666666, 1, 1, 555.32, '1972-02-15');
-- Inserción varios proveedores
