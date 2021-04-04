/*create table roles
(
    id          integer generated by default as identity,
    description varchar(150) not null,
    name        varchar(40)  not null,
    primary key (id)
);

create table users
(
    id         integer generated by default as identity,
    email      varchar(40) not null,
    enabled    boolean,
    first_name varchar(45) not null,
    last_name  varchar(45) not null,
    password   varchar(64) not null,
    photos     varchar(64),
    primary key (id)
);

create table users_roles
(
    user_id integer not null,
    role_id integer not null,
    primary key (user_id, role_id)
);

alter table roles
    add constraint UK_ofx66keruapi6vyqpv6f2or37 unique (name);

alter table users
    add constraint UK_6dotkott2kjsp8vw4d0m25fb7 unique (email);

alter table users_roles
    add constraint FKj6m8fwv7oqv74fcehir1a9ffy
        foreign key (role_id)
            references roles;

alter table users_roles
    add constraint FK2o0jvgh89lemvvo17cbqvdxaa
        foreign key (user_id)
            references users;
*/
/*INSERT INTO roles (id, name, description)
VALUES(1,  'Admin', 'Manage everything');
INSERT INTO roles (id, name, description)
VALUES(2,  'Salesperson', 'Manage everything');
INSERT INTO roles (id, name, description)
VALUES(3,  'Editor', 'Manage everything');
INSERT INTO roles (id, name, description)
VALUES(4,  'Shipper', 'Manage everything');
INSERT INTO roles (id, name, description)
VALUES(5,  'Assistant', 'Manage everything');*/
INSERT INTO `roles` (id, name, description)
VALUES (1, 'Admin', 'manage everything'),
       (2, 'Salesperson', 'manage product price, customers, shipping, orders and sales report'),
       (3, 'Editor', 'manage categories, brands, products, articles and menus'),
       (4, 'Shipper', 'view products, view orders and update order status'),
       (5, 'Assistant', 'manage questions and reviews');

-- User table
/*INSERT INTO users (id, email, enabled, first_name, last_name, password, photos)
values (1, 'thaihoc2105@gmail.com', true, 'Hoc', 'Ha', '123', null);
INSERT INTO users (id, email, enabled, first_name, last_name, password, photos)
values (2, 'thaihocdisable@gmail.com', false , 'Hoc 2', 'Ha 2', '123', null);*/
INSERT INTO `users` (id, email, enabled, first_name, last_name, password, photos)
VALUES (1,'nam@codejava.net',1,'Nam','Ha Minh','$2a$10$bDqskP9Z/y6BIZnFLgJ8HuwMYaZXD9w2jVk2pYHXgn1k6N4nckleu','namhm.png'),
       (2,'kanna.allada@gmail.com',1,'Allada','Pavan','$2a$10$zRa/rmQ8JarpYG2bNKftyelKnsUhsHwGB.xmCKTWJClsB7O9wzTnG','Allada Pavan.png')/*,
       (3,'aecllc.bnk@gmail.com',0,'Bruce','Kitchell','$2a$10$GINThwCjVZAbGnmOe9BIeuDuvDlyfuwZrg/Rsmrjs1Lsq2pnXtO/S','Bruce Kitchell.png'),
       (4,'muhammad.evran13@gmail.com',1,'Muhammad','Evran','$2a$10$UcHWHC72azPVZJb5Ky.Yy.X695WGf1ZkkGMS3WL3B9WqWf2dQD04G','Muhammad Evran.png'),
       (5,'kent.hervey8@gmail.com',1,'Kent','Hervey','$2a$10$YHXRsZ07/Btv.qCgGht.7u2PW.GLWzpVB7eabfgH1mhTVVXffDT6K','KentHervey.png'),
       (6,'alfredephraim26@gmail.com',0,'Alfred','Ephraim','$2a$10$1jl3q3r/Fh9ZBv6ziM4XhuxCi2GMFWcfHUrxsesXAEwnsiV/NJKbq','Alfred.png'),
       (7,'nathihsa@gmail.com',1,'Nathi','Sangweni','$2a$10$WyHmQiXCSYuHcGeg8eFWvOScqzSgg88MmqpajPdzSkLsvZjT3tKC.','Nathi_Sangweni.png'),
       (8,'ammanollashirisha2020@gmail.com',1,'Ammanolla','Shirisha','$2a$10$N1eE87eXFB2XQ5nmWKaTXOofnrPn8koeNvZhEpleJzO49i55e/Vk.','Ammanolla.png'),
       (9,'bfeeny238@hotmail.com',1,'Bill','Feeny','$2a$10$3sH0v..zpjwA8ux5/q.OAeu0HgmSdMj8VzMWzhwwBDkE8wOISsUyi','Bill Feeny.png'),
       (10,'redsantosph@gmail.com',1,'Frederick','delos Santos','$2a$10$KXHmKpE6YB/0sjiy3fkMv.muKyxqvOXE6jVeaPu8KEaExx62ZmmNe','Frederick Santos.png'),
       (11,'ro_anamaria@mail.ru',1,'Ana','Maria','$2a$10$sz0CHOHAY1Xjt2ajIZgnG.L2KBQ4SsQkOGsPYue.C5gr6j.KMDdqS','Anna Maria.jpg'),
       (12,'maytassatya@hotmail.com',0,'Satya','Narayana','$2a$10$R7EJcaYijjJo/IVk6c1CieBML2uP3RAKMVlCxylPAePlCfJsX7OOy','Satya Narayana.png'),
       (13,'matthewefoli@gmail.com',1,'Matthew','Efoli','$2a$10$ECNnxXSVArnwS9KCet3yguQ1qHKyBIhh2G8c4F9CYgvp/Hadl8OS6','Matthew.png'),
       (14,'davekumara2@gmail.com',1,'Dave','Kumar','$2a$10$5ebeZu18V5RheieYqpl/LORCN41E3H7yvxKqEwtq5Zq2JVw.E9dva','Dave Kumar.png'),
       (15,'jackkbruce@yahoo.com',1,'Jack','Bruce','$2a$10$a6iyIHRj8DNpu15obVHTSOGcLe4IfpBcD4iEEJesWaFpBQvRoF2da','Jack Bruce.png'),
       (16,'zirri.mohamed@gmail.com',1,'Mohamed','Zirri','$2a$10$TmvyH1AoyDqRmQ4uC8NAZOOV29CPEDGuxVsHLP1cJoHQGr78L4kjW','Mohamed Zirri.jpg'),
       (17,'mk.majumdar009@hotmail.com',0,'Mithun','Kumar Majumdar','$2a$10$Y6SEy2INN0Rk/vhLHHJUYO6IMqNW3Ar.jVe9o0W1lpBRX8xr2Itui','Mithun Kumar Majumdar.png'),
       (18,'aliraza.arain.28@gmail.com',1,'Ali','Raza','$2a$10$PISZ2KitSbhE4/Z3dtIGk.WUi2ILiDl4PzRUDEQSp5BJIxcdcPq4G','Ali Raza.png'),
       (19,'isaachenry2877@gmail.com',1,'Isaac','Henry','$2a$10$CtmhrOz/AhDoCpKbeYl8O.0ngCFMukcznNZq7.YcHrkRyKpBG8Zca','Isaac Henry.jpg'),
       (20,'s.stasovska82@hotmail.com',1,'Svetlana','Stasovska','$2a$10$fcN2cNa7vB.78QnmzfNZEeUBkrwUaM.bVK3iDB.KFQlR15DwL7QZy','Svetlana Stasovska.png'),
       (21,'mikegates2012@gmail.com',1,'Mike','Gates','$2a$10$zIO1tygsw6cB2ymiR.WX0ulr9NKdTlZHqu7w/W/LLwk8HhK7nVnH.','Mike Gates.png'),
       (22,'pedroquintero67@gmail.com',0,'Pedro','Quintero','$2a$10$UPX5EwZw0MyBvbe.7mxg2u8GOl/4KgaUU40iSjr1PLFYvhu35fMmu','Pedro Quintero.png'),
       (23,'amina.elshal2@yahoo.com',1,'Amina','Elshal','$2a$10$J1yoyqG5vWNe5N663PkgY.h53gfJtTR7Bb8E8u3sXdNbOZxhXgHu.','Amina Elshal.png')*/;

/*insert into users_roles (user_id, role_id)
values (1, 1);
insert into users_roles (user_id, role_id)
values (1, 2);*/
INSERT INTO `users_roles` (user_id, role_id)
VALUES (1,1),(2,1),(3,2)/*,(9,2),(10,2),(11,2),(12,2),(13,2),(19,2),(20,2),(4,3),(5,3),(6,3),
       (7,3),(8,3),(11,3),(15,3),(18,3),(20,3),(14,4),(15,4),(16,4),(17,4),(18,4),(5,5),(14,5),
       (19,5),(20,5),(21,5),(22,5),(23,5)*/;



