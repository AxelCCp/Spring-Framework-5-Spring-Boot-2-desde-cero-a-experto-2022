INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES(1, 'AAJDHF', 'ASDA', 'ASDAS1D@JHG.COM', '2022-08-28', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES(2, 'AAJDHF', 'ASDA', 'ASDASD2@JHG.COM', '2022-08-28', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES(3, 'AAJDHF', 'ASDA', 'ASDAeSD@JHG.COM', '2022-08-28', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES(4, 'AAJDHF', 'ASDA', 'ASDASdD@JHG.COM', '2022-08-28', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES(5, 'AAJDHF', 'ASDA', 'ASDASfD@JHG.COM', '2022-08-28', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES(6, 'AAJDHF', 'ASDA', 'ASDASgD@JHG.COM', '2022-08-28', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES(7, 'AAJDHF', 'ASDA', 'ASDAShD@JHG.COM', '2022-08-28', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES(8, 'AAJDHF', 'ASDA', 'ASDASkD@JHG.COM', '2022-08-28', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES(9, 'AAJDHF', 'ASDA', 'ASDAlSD@JHG.COM', '2022-08-28', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES(10, 'AAJDHF', 'ASDA', 'ASgDASD@JHG.COM', '2022-08-28', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES(11, 'AAJDHF', 'ASDA', 'ASDfASD@JHG.COM', '2022-08-28', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES(12, 'AAJDHF', 'ASDA', 'ASDAwSD@JHG.COM', '2022-08-28', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES(13, 'AAJDHF', 'ASDA', 'ASDAS4D@JHG.COM', '2022-08-28', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES(14, 'AAJDHF', 'ASDA', 'ASDAS6D@JHG.COM', '2022-08-28', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES(15, 'AAJDHF', 'ASDA', 'ASDAS7D@JHG.COM', '2022-08-28', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES(16, 'AAJDHF', 'ASDA', 'ASDASD@JHG.COM', '2022-08-28', '');


INSERT INTO productos (nombre, precio, create_at) VALUES('Mica comoda', 259000, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Panasonic hd', 253000, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Silla escritorio', 39000, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Escritorio mesa', 559000, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Pc lenovo', 250000, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Sofa cama', 29000, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Mueble noseque', 9000, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Otro producto', 20000, NOW());


INSERT INTO facturas (descripcion, observacion, cliente_id, create_at) VALUES ('Factura equipos de oficina', null, 1, NOW()); 
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES (1,1,1);
INSERT INTO facturas_items (cantidad, facturas_id, producto_id) VALUES (2,1,4);
INSERT INTO facturas_items (cantidad, facturas_id, producto_id) VALUES (3,1,5);
INSERT INTO facturas_items (cantidad, facturas_id, producto_id) VALUES (4,1,2);

INSERT INTO facturas (descripcion, observacion, cliente_id, create_at) VALUES ('Factura bicicleta', 'alguna nota importante!', 1, NOW()); 
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES (3,2,6);


INSERT INTO `users` (username, password, enabled) VALUES ('andres', '$2a$10$u4lpzY4FvCf9ri/AsE9GneoQtUSCExI0/VLyZucgy6/KsRqMR3zwy', 1);
INSERT INTO `users` (username, password, enabled) VALUES ('admin', '$2a$10$hQtTidgzvx.DpVxh1le44.APdGIgmqfj3jVtU6kpSz8gIRPV/ch/i', 1);

INSERT INTO `authorities` (user_id ,authority) VALUES (1, 'ROLE_USER');
INSERT INTO `authorities` (user_id ,authority) VALUES (2, 'ROLE_ADMIN');
INSERT INTO `authorities` (user_id ,authority) VALUES (2, 'ROLE_USER');
