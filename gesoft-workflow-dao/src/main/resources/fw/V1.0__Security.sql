create table gwf_rol(
  rol varchar(50) primary key,
  description varchar(255) not null
);

create table gwf_permission(
  permission varchar(50) primary key,
  description varchar(255) not null
);

create table gwf_rol_permission(
  id BIGSERIAL primary key,
  rol varchar(50) not null,
  permission varchar(50) not null,
  CONSTRAINT gwf_rol_permission_his_rol_fkey FOREIGN KEY (rol)
     REFERENCES gwf_rol (rol) MATCH SIMPLE
     ON UPDATE NO ACTION
     ON DELETE NO ACTION,
  CONSTRAINT gwf_rol_permission_his_permission_fkey FOREIGN KEY (permission)
     REFERENCES gwf_permission (permission) MATCH SIMPLE
     ON UPDATE NO ACTION
     ON DELETE NO ACTION
);


create table gwf_user(
  id BIGSERIAL primary key,
  name varchar(50) not null,
  email varchar(255) not null,
  password varchar(255) not null,
  rol varchar(255) not null,
  client_id integer not null
);

CREATE TABLE gwf_user_group_rol (
    id BIGSERIAL primary key,
    group_id integer,
    rol_id character varying(50) NOT NULL,
    user_id integer NOT NULL
);

create table gwf_sesion
(
	id BIGSERIAL primary key,
	observations varchar(255),
	user_id integer not null,
	client_id integer not null,
	valid_from TIMESTAMP not null,
	valid_until TIMESTAMP,
	last_access TIMESTAMP,
  finished_by integer,
  finished TIMESTAMP,
  create_by integer,
	active varchar not null default 'S'
);

insert into gwf_rol values ('SUPER_USUARIO','Puede acceder a todo');

insert into gwf_permission values ('CREAR_USUARIO','Permiso para crear un usuario');
insert into gwf_permission values ('OBTENER_USUARIO','Permiso para crear obtener un usuario');


insert into gwf_rol_permission values (nextval('gwf_rol_permission_id_seq'),'SUPER_USUARIO','OBTENER_USUARIO');

insert into gwf_user values (nextval('gwf_user_id_seq'),'Luis Felipe Sosa','lsosa@gmail.com', '123', 'SUPER_USUARIO', 1);