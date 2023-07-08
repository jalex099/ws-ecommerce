-- CREAR TABLA DE COMPANIAS
CREATE TABLE IF NOT EXISTS companies (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    is_active BOOLEAN NOT NULL DEFAULT TRUE
);

-- CREAR TABLA DE CATEGORIAS
CREATE TABLE IF NOT EXISTS categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    company_id INTEGER NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    FOREIGN KEY (company_id) REFERENCES companies(id)
);

-- CREAR TABLA DE PRODUCTOS
CREATE TABLE IF NOT EXISTS products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    order_pos INTEGER DEFAULT -1,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    category_id INTEGER NOT NULL,
    company_id INTEGER NOT NULL,
    FOREIGN KEY (category_id) REFERENCES categories(id),
    FOREIGN KEY (company_id) REFERENCES companies(id)
);

-- CREAR TABLA OPCIONES DE PRODUCTO
CREATE TABLE IF NOT EXISTS products_options (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    aditional_price DECIMAL(10,2) NOT NULL,
    order_pos INTEGER DEFAULT -1,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    product_id INTEGER NOT NULL,
    company_id INTEGER NOT NULL,
    FOREIGN KEY (product_id) REFERENCES products(id),
    FOREIGN KEY (company_id) REFERENCES companies(id)
);

-- CREAR TABLA SUB OPCIONES DE PRODUCTO
CREATE TABLE IF NOT EXISTS products_sub_options (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    aditional_price DECIMAL(10,2) NOT NULL,
    order_pos INTEGER DEFAULT -1,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    product_option_id INTEGER NOT NULL,
    company_id INTEGER NOT NULL,
    FOREIGN KEY (product_option_id) REFERENCES products_options(id),
    FOREIGN KEY (company_id) REFERENCES companies(id)
);


-- CREAR TABLA DE IMAGENES
CREATE TABLE IF NOT EXISTS images (
    id SERIAL PRIMARY KEY,
    type VARCHAR(3) NOT NULL,
    extension VARCHAR(8) NOT NULL,
    size VARCHAR(24),
    reference INTEGER NOT NULL,
    url VARCHAR(255) NOT NULL,
    key VARCHAR(120) NOT NULL,
    weight DECIMAL(10,4),
    height INTEGER,
    width INTEGER,
    company_id INTEGER NOT NULL,
    FOREIGN KEY (company_id) REFERENCES companies(id)
);

-- CREAR TABLA DE PARAMETROS
CREATE TABLE IF NOT EXISTS params (
    id SERIAL PRIMARY KEY,
    key VARCHAR(30) NOT NULL,
	parent_key VARCHAR(30),
    value VARCHAR(255) NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    company_id INTEGER NOT NULL,
    FOREIGN KEY (company_id) REFERENCES companies(id)
);

---------------------------------------------------------------------------

-- CREAR TABLA DE USUARIOS (permitir login con google y facebook)
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255),
    platform VARCHAR(255),
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    company_id INTEGER NOT NULL,
    FOREIGN KEY (company_id) REFERENCES companies(id)
);

-- CREAR ENUM DE VISIBILIDAD DE CARRITO
CREATE TYPE visibility AS ENUM ('PUBLIC', 'PRIVATE');

-- CREAR TABLA CARRITO
CREATE TABLE IF NOT EXISTS carts (
    id SERIAL PRIMARY KEY,
    code VARCHAR(255) NOT NULL,
    date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(3) NOT NULL,
    visibility visibility NOT NULL DEFAULT 'PRIVATE',
    user_id INTEGER,
    company_id INTEGER NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (company_id) REFERENCES companies(id)
);

-- CREAR TABLA CARRITO DETALES
CREATE TABLE IF NOT EXISTS carts_details (
    id SERIAL PRIMARY KEY,
    cart_id INTEGER NOT NULL,
    product_id INTEGER NOT NULL,
    quantity INTEGER NOT NULL,
    order_pos INTEGER,
    FOREIGN KEY (cart_id) REFERENCES carts(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);

-- CREAR TABLA DE CARRITO DETALLES OPCIONES
CREATE TABLE IF NOT EXISTS carts_details_options (
    id SERIAL PRIMARY KEY,
    cart_detail_id INTEGER NOT NULL,
    product_option_id INTEGER NOT NULL,
    product_sub_option_id INTEGER NOT NULL,
    FOREIGN KEY (cart_detail_id) REFERENCES carts_details(id),
    FOREIGN KEY (product_option_id) REFERENCES products_options(id),
    FOREIGN KEY (product_sub_option_id) REFERENCES products_sub_options(id)
);
