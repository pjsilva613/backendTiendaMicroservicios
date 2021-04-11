INSERT INTO tbl_productos (id, nombre, descripcion, existencia, precio, estado, fecha_creacion)
VALUES (1, 'tenis adidas','tenis de cuero ultima coleccion ADIDAS',5,250000.00,'CREADO','2021-04-09');

INSERT INTO tbl_productos (id, nombre, descripcion, existencia, precio, estado, fecha_creacion)
VALUES (2, 'camiseta adidas','camiseta deportiva para realizar ejercicio al aire libre',4,50000.00,'CREADO','2021-04-09');

INSERT INTO tbl_productos (id, nombre, descripcion, existencia, precio, estado, fecha_creacion)
VALUES (3, 'sudadera adidas','sudadera deportiva para realizar ejercicios al aire libre',12,80000.00,'CREADO','2021-04-09');


INSERT INTO tbl_personas (id, numero_documento, nombres, apellidos, direccion, estado, fecha_creacion)
VALUES(1,'14012600', 'Pedro Javier', 'Silva Hernandez', 'Carrera 13 # 8-21', 'CREADO', '2021-04-09');

INSERT INTO tbl_personas (id, numero_documento, nombres, apellidos, direccion, estado, fecha_creacion)
VALUES(2,'28678134', 'Maria Gavis', 'Hernandez', 'Carrera 13 # 8-21', 'CREADO', '2021-04-09');

INSERT INTO tbl_personas (id, numero_documento, nombres, apellidos, direccion, estado, fecha_creacion)
VALUES(3,'65630147', 'Zaida Mayerly', 'Varon Devia', 'Carrera 13 # 8-21', 'CREADO', '2021-04-09');

INSERT INTO tlb_facturas (id, numero_factura, descripcion, persona_id, fecha_creacion, estado, domicilio, total_factura)
VALUES(1, '0001', 'Factura venta uniforme deportivo', 1, NOW(),'CREADO', 20000.0, 186600.0);

INSERT INTO tbl_factura_items (factura_id, producto_id, cantidad, precio, iva, sub_total)
VALUES(1, 1 , 1, 10000.00, 1900.0, 11900.0);

INSERT INTO tbl_factura_items (factura_id, producto_id, cantidad, precio, iva, sub_total)
VALUES(1, 2 , 2, 20000.00, 7600.0, 47600.0);

INSERT INTO tbl_factura_items (factura_id, producto_id, cantidad, precio, iva, sub_total)
VALUES(1, 3 , 3, 30000.00, 17100.0, 107100.0);
