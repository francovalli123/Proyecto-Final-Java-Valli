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
    precio FLOAT(8, 2)
);

CREATE TABLE factura (
    facturaid int primary key NOT NULL AUTO_INCREMENT,
    fecha datetime,
    cantidad int,
    total FLOAT(8, 2),
    clienteid int,
    CONSTRAINT FKfactura_cliente FOREIGN KEY (clienteid) REFERENCES cliente(clienteid)
);

CREATE TABLE linea (
    lineaid int primary key NOT NULL AUTO_INCREMENT,
    descripcion varchar(255),
    cantidad int,
    precio FLOAT(8, 2),
    facturaid int not null,
    productoid int not null,
    CONSTRAINT FK_factura FOREIGN KEY (facturaid) REFERENCES factura(facturaid),
    CONSTRAINT FK_producto FOREIGN KEY (productoid) REFERENCES producto(productoid)
);