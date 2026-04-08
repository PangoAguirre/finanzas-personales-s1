CREATE SCHEMA IF NOT EXISTS finanzas;

CREATE TABLE IF NOT EXISTS finanzas.usuario (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(120) NOT NULL,
    correo VARCHAR(180) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    rol VARCHAR(30) NOT NULL DEFAULT 'USUARIO_ESTANDAR',
    moneda VARCHAR(3) NOT NULL DEFAULT 'COP',
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    fecha_registro TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    ultimo_acceso TIMESTAMPTZ NULL,
    CONSTRAINT ck_usuario_rol CHECK (
        rol IN (
            'USUARIO_ESTANDAR',
            'ADMINISTRADOR'
        )
    ),
    CONSTRAINT ck_usuario_moneda CHECK (char_length(moneda) = 3)
);

CREATE UNIQUE INDEX IF NOT EXISTS uq_usuario_correo_lower ON finanzas.usuario (LOWER(correo));

CREATE TABLE IF NOT EXISTS finanzas.cuenta (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    saldo NUMERIC(14, 2) NOT NULL DEFAULT 0,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    fecha_creacion TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT ck_cuenta_tipo CHECK (
        tipo IN (
            'EFECTIVO',
            'BANCO',
            'AHORRO',
            'OTRO'
        )
    )
);

CREATE TABLE IF NOT EXISTS finanzas.usuario_cuenta (
    usuario_id BIGINT NOT NULL,
    cuenta_id BIGINT NOT NULL,
    permiso VARCHAR(20) NOT NULL DEFAULT 'PROPIETARIO',
    fecha_asignacion TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    PRIMARY KEY (usuario_id, cuenta_id),
    CONSTRAINT fk_usuario_cuenta_usuario FOREIGN KEY (usuario_id) REFERENCES finanzas.usuario (id) ON DELETE RESTRICT,
    CONSTRAINT fk_usuario_cuenta_cuenta FOREIGN KEY (cuenta_id) REFERENCES finanzas.cuenta (id) ON DELETE RESTRICT,
    CONSTRAINT ck_usuario_cuenta_permiso CHECK (
        permiso IN ('PROPIETARIO', 'COLABORADOR')
    )
);

CREATE TABLE IF NOT EXISTS finanzas.transaccion (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    cuenta_id BIGINT NOT NULL,
    categoria_id BIGINT NULL,
    monto NUMERIC(14, 2) NOT NULL,
    descripcion VARCHAR(255) NULL,
    tipo VARCHAR(20) NOT NULL,
    fecha TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_transaccion_usuario FOREIGN KEY (usuario_id) REFERENCES finanzas.usuario (id) ON DELETE RESTRICT,
    CONSTRAINT fk_transaccion_cuenta FOREIGN KEY (cuenta_id) REFERENCES finanzas.cuenta (id) ON DELETE RESTRICT,
    CONSTRAINT ck_transaccion_monto_positivo CHECK (monto > 0),
    CONSTRAINT ck_transaccion_tipo CHECK (tipo IN ('INGRESO', 'EGRESO'))
);