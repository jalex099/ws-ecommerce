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
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    category_id INTEGER NOT NULL,
    company_id INTEGER NOT NULL,
    FOREIGN KEY (category_id) REFERENCES categories(id),
    FOREIGN KEY (company_id) REFERENCES companies(id)
);

-- CREAR TABLA DE TAGS
CREATE TABLE IF NOT EXISTS tags (
    id SERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    company_id INTEGER NOT NULL,
    FOREIGN KEY (company_id) REFERENCES companies(id)
);

-- CREAR TABLA DE PRODUCTOS_TAGS
CREATE TABLE IF NOT EXISTS products_tags (
    id SERIAL PRIMARY KEY,
    product_id INTEGER NOT NULL,
    tag_id INTEGER NOT NULL,
    company_id INTEGER NOT NULL,
    FOREIGN KEY (product_id) REFERENCES products(id),
    FOREIGN KEY (tag_id) REFERENCES tags(id),
    FOREIGN KEY (company_id) REFERENCES companies(id)
);


-- CREAR TABLA DE IMAGENES
CREATE TABLE IF NOT EXISTS images (
    id SERIAL PRIMARY KEY,
    type VARCHAR(3) NOT NULL,
    extension VARCHAR(8) NOT NULL,
    size VARCHAR(2),
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
    value VARCHAR(255) NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    company_id INTEGER NOT NULL,
    FOREIGN KEY (company_id) REFERENCES companies(id)
);

---------------------------------------------------------------------------
