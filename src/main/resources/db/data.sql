INSERT INTO
    finanzas.usuario (
        nombre,
        correo,
        password_hash,
        rol,
        moneda,
        activo,
        fecha_registro,
        ultimo_acceso
    )
VALUES (
        'Administrador General',
        'admin@finanzas.com',
        '$2a$10$abcdefghijklmnopqrstuvabcdefghijklmnopqrstuvabcdefghijkl',
        'ADMINISTRADOR',
        'COP',
        TRUE,
        NOW(),
        NULL
    ),
    (
        'Usuario Demo',
        'usuario@finanzas.com',
        '$2a$10$zyxwvutsrqponmlkjihgfedcbazyxwvutsrqponmlkjihgfedcba',
        'USUARIO_ESTANDAR',
        'COP',
        TRUE,
        NOW(),
        NULL
    );

INSERT INTO
    finanzas.cuenta (
        nombre,
        tipo,
        saldo,
        activo,
        fecha_creacion
    )
VALUES (
        'Cuenta principal',
        'BANCO',
        1500000.00,
        TRUE,
        NOW()
    ),
    (
        'Billetera',
        'EFECTIVO',
        250000.00,
        TRUE,
        NOW()
    );

INSERT INTO
    finanzas.usuario_cuenta (
        usuario_id,
        cuenta_id,
        permiso,
        fecha_asignacion
    )
VALUES (1, 1, 'PROPIETARIO', NOW()),
    (2, 2, 'PROPIETARIO', NOW());

SELECT setval (
        pg_get_serial_sequence ('finanzas.usuario', 'id'), COALESCE(
            (
                SELECT MAX(id)
                FROM finanzas.usuario
            ), 1
        ), true
    );

SELECT setval (
        pg_get_serial_sequence ('finanzas.cuenta', 'id'), COALESCE(
            (
                SELECT MAX(id)
                FROM finanzas.cuenta
            ), 1
        ), true
    );

SELECT setval (
        pg_get_serial_sequence ('finanzas.transaccion', 'id'), COALESCE(
            (
                SELECT MAX(id)
                FROM finanzas.transaccion
            ), 1
        ), true
    );