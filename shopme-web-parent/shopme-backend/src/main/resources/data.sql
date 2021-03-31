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
INSERT INTO roles (id, name, description)
VALUES(1,  'Admin', 'Manage everything');
INSERT INTO roles (id, name, description)
VALUES(2,  'Salesperson', 'Manage everything');
INSERT INTO roles (id, name, description)
VALUES(3,  'Editor', 'Manage everything');
INSERT INTO roles (id, name, description)
VALUES(4,  'Shipper', 'Manage everything');
INSERT INTO roles (id, name, description)
VALUES(5,  'Assistant', 'Manage everything');

-- User table
INSERT INTO users (id, email, enabled, first_name, last_name, password, photos)
values (1, 'thaihoc2105@gmail.com', true, 'Hoc', 'Ha', '123', null);
INSERT INTO users (id, email, enabled, first_name, last_name, password, photos)
values (2, 'thaihocdisable@gmail.com', false , 'Hoc 2', 'Ha 2', '123', null);

insert into users_roles (user_id, role_id)
values (1, 1);
insert into users_roles (user_id, role_id)
values (1, 2);



