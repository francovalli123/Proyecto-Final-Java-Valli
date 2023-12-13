CREATE DATABASE ventas;
use ventas;

CREATE TABLE cliente (
	clienteid int primary key NOT NULL AUTO_INCREMENT,
    dni int NOT NULL,
    nombre varchar(255) NOT NULL,
    apellido varchar(255)
);

CREATE TABLE producto (
	productoid int primary key NOT NULL AUTO_INCREMENT,
    codigo int NOT NULL,
    descripcion varchar(255) NOT NULL,
    cantidad int,
    precio FLOAT(5, 2)
);

CREATE TABLE factura (
    id int primary key NOT NULL AUTO_INCREMENT,
    fecha datetime,
    cantidad int,
    total FLOAT(5, 2),
    clienteid int,
    CONSTRAINT FK_cliente FOREIGN KEY (clienteid) REFERENCES cliente(clienteid)
);

CREATE TABLE linea (
    lineaid int primary key NOT NULL AUTO_INCREMENT,
    descripcion varchar(255) NOT NULL,
    cantidad int,
    precio FLOAT(5, 2),
    facturaid int NOT NULL,
    productoid int NOT NULL,
    CONSTRAINT FK_factura FOREIGN KEY (facturaid) REFERENCES factura(facturaid),
    CONSTRAINT FK_producto FOREIGN KEY (productoid) REFERENCES producto(productoid)
);