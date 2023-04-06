
-- CREAR TABLA DE CATEGORIAS
CREATE TABLE IF NOT EXISTS categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    is_active BOOLEAN NOT NULL DEFAULT TRUE
);

--INSERTAR 3 CATEGORIAS
INSERT INTO categories (name, description, is_active) VALUES ('Categoría 1', 'Descripción de la categoría 1', TRUE);
INSERT INTO categories (name, description, is_active) VALUES ('Categoría 2', 'Descripción de la categoría 2', TRUE);
INSERT INTO categories (name, description, is_active) VALUES ('Categoría 3', 'Descripción de la categoría 3', TRUE);

-- CREAR TABLA DE PRODUCTOS
CREATE TABLE IF NOT EXISTS products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    category_id INTEGER NOT NULL,
    FOREIGN KEY (category_id) REFERENCES categories(id)
);

-- INSERTAR 3 PRODUCTOS
INSERT INTO products (name, description, price, is_active, category_id) VALUES ('Producto 1', 'Descripción del producto 1', 100.00, TRUE, 1);
INSERT INTO products (name, description, price, is_active, category_id) VALUES ('Producto 2', 'Descripción del producto 2', 200.00, TRUE, 2);
INSERT INTO products (name, description, price, is_active, category_id) VALUES ('Producto 3', 'Descripción del producto 3', 300.00, TRUE, 3);

-- CREAR TABLA DE TAGS
CREATE TABLE IF NOT EXISTS tags (
    id SERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL
);

-- CREAR TABLA DE PRODUCTOS_TAGS
CREATE TABLE IF NOT EXISTS products_tags (
    id SERIAL PRIMARY KEY,
    product_id INTEGER NOT NULL,
    tag_id INTEGER NOT NULL,
    FOREIGN KEY (product_id) REFERENCES products(id),
    FOREIGN KEY (tag_id) REFERENCES tags(id)
);
---------------------------------------------------------------------------
