/*create table person
(
   id integer not null,
   name varchar(255) not null,
   location varchar(255),
   birth_date timestamp,
   primary key(id)
);*/

--INSERT INTO PERSON (ID, NAME, LOCATION, BIRTH_DATE )
--VALUES(10001,  'Ranga', 'Hyderabad',sysdate());
--INSERT INTO PERSON (ID, NAME, LOCATION, BIRTH_DATE )
--VALUES(10002,  'James', 'New York',sysdate());
--INSERT INTO PERSON (ID, NAME, LOCATION, BIRTH_DATE )
--VALUES(10003,  'Pieter', 'Amsterdam',sysdate());

INSERT INTO brand (ID, name, description)
VALUES(1,  'brand 1', 'scrip 1');
INSERT INTO brand (ID, name, description)
VALUES(2,  'brand 2', 'scrip 1');
INSERT INTO brand (ID, name, description)
VALUES(3,  'brand 3', 'scrip 1');

INSERT INTO color (ID, name)
VALUES(1,  'black');
INSERT INTO color (ID, name)
VALUES(2,  'white');
INSERT INTO color (ID, name)
VALUES(3,  'red');

INSERT INTO product (ID, name, price, brand, color, quantity, created_time, modified_time)
VALUES(1,  'TV 1', 500.0, 1, 1, 5, systimestamp, systimestamp);
INSERT INTO product (ID, name, price, brand, color, quantity, created_time, modified_time)
VALUES(2,  'Shirt 1', 50.0, 2, 2, 10, systimestamp, systimestamp);
INSERT INTO product (ID, name, price, brand, color, quantity, created_time, modified_time)
VALUES(3,  'Book 1', 15.0, 3, 3, 15, systimestamp, systimestamp);

INSERT INTO customer (ID, password_hash, role_id, full_name, phone_number, email, address )
VALUES(1,  '111', 1, 'Ha Nguyen Thai Hoc', '0773410987', 'thaihoc2105@gmail.com', 'Thich Quang Duc');
INSERT INTO customer (ID, password_hash, role_id, full_name, phone_number, email, address )
VALUES(2,  '111', 2, 'Luong Van A', '07733445566', 'lvA@gmail.com', 'Thich Quang Duc 2');
INSERT INTO customer (ID, password_hash, role_id, full_name, phone_number, email, address )
VALUES(3,  '111', 2, 'Nguyen Van B','0779999999', 'nvB@gmail.com', 'Thich Quang Duc3');
