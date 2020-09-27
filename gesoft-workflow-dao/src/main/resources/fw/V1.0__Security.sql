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

insert into gwf_permission values ('OBTENER_USUARIO','Permiso para crear obtener un usuario');

insert into gwf_permission values ('CREAR_CLIENTE','Permiso para crear el cliente');
insert into gwf_permission values ('MODIFICAR_CLIENTE','Permiso para actualizar el cliente');
insert into gwf_permission values ('ELIMINAR_CLIENTE','Permiso para visualizar el cliente');
insert into gwf_permission values ('OBTENER_CLIENTE','Permiso para obtener el cliente');

insert into gwf_permission values ('CREAR_PLANTILLA','Permiso para crear la plantilla');
insert into gwf_permission values ('MODIFICAR_PLANTILLA','Permiso para actualizar la plantilla');
insert into gwf_permission values ('ELIMINAR_PLANTILLA','Permiso para visualizar la plantilla');
insert into gwf_permission values ('OBTENER_PANTILLA','Permiso para obtener la plantilla');

insert into gwf_permission values ('CREAR_ACTIVIDAD','Permiso para crear la actividad');
insert into gwf_permission values ('MODIFICAR_ACTIVIDAD','Permiso para actualizar la actividad');
insert into gwf_permission values ('ELIMINAR_ACTIVIDAD','Permiso para visualizar la actividad');
insert into gwf_permission values ('OBTENER_ACTIVIDAD','Permiso para obtener la actividad');

insert into gwf_permission values ('CREAR_TIPO_PROYECTO','Permiso para crear el tipo de proyecto');
insert into gwf_permission values ('MODIFICAR_TIPO_PROYECTO','Permiso para actualizar el tipo de proyecto');
insert into gwf_permission values ('ELIMINAR_TIPO_PROYECTO','Permiso para visualizar el tipo de proyecto');
insert into gwf_permission values ('OBTENER_TIPO_PROYECTO','Permiso para obtener el tipo de proyecto');

insert into gwf_permission values ('CREAR_PROYECTO','Permiso para crear el proyecto');
insert into gwf_permission values ('MODIFICAR_PROYECTO','Permiso para actualizar el proyecto');
insert into gwf_permission values ('ELIMINAR_PROYECTO','Permiso para visualizar el proyecto');
insert into gwf_permission values ('OBTENER_PROYECTO','Permiso para obtener el proyecto');

insert into gwf_permission values ('CREAR_EJECUCION_PROYECTO','Permiso para crear la ejecución del proyecto');
insert into gwf_permission values ('APROBAR_EJECUCION_ACTIVIDAD','Permiso para aprobar la actividad de una ejecución del proyecto');
insert into gwf_permission values ('RECHAZAR_EJECUCION_ACTIVIDAD','Permiso para aprobar la actividad de una ejecución del proyecto');
insert into gwf_permission values ('MODIFICAR_EJECUCION_ACTIVIDAD','Permiso para modificar la actividad de una ejecución del proyecto');
insert into gwf_permission values ('OBTENER_EJECUCION_PROYECTO','Permiso para obtener la ejecución del proyecto');

insert into gwf_rol_permission values (nextval('gwf_rol_permission_id_seq'),'SUPER_USUARIO','OBTENER_USUARIO');

insert into gwf_rol_permission values (nextval('gwf_rol_permission_id_seq'),'SUPER_USUARIO','CREAR_CLIENTE');
insert into gwf_rol_permission values (nextval('gwf_rol_permission_id_seq'),'SUPER_USUARIO','MODIFICAR_CLIENTE');
insert into gwf_rol_permission values (nextval('gwf_rol_permission_id_seq'),'SUPER_USUARIO','ELIMINAR_CLIENTE');
insert into gwf_rol_permission values (nextval('gwf_rol_permission_id_seq'),'SUPER_USUARIO','OBTENER_CLIENTE');

insert into gwf_rol_permission values (nextval('gwf_rol_permission_id_seq'),'SUPER_USUARIO','CREAR_PLANTILLA');
insert into gwf_rol_permission values (nextval('gwf_rol_permission_id_seq'),'SUPER_USUARIO','MODIFICAR_PLANTILLA');
insert into gwf_rol_permission values (nextval('gwf_rol_permission_id_seq'),'SUPER_USUARIO','ELIMINAR_PLANTILLA');
insert into gwf_rol_permission values (nextval('gwf_rol_permission_id_seq'),'SUPER_USUARIO','OBTENER_PANTILLA');

insert into gwf_rol_permission values (nextval('gwf_rol_permission_id_seq'),'SUPER_USUARIO','CREAR_ACTIVIDAD');
insert into gwf_rol_permission values (nextval('gwf_rol_permission_id_seq'),'SUPER_USUARIO','MODIFICAR_ACTIVIDAD');
insert into gwf_rol_permission values (nextval('gwf_rol_permission_id_seq'),'SUPER_USUARIO','ELIMINAR_ACTIVIDAD');
insert into gwf_rol_permission values (nextval('gwf_rol_permission_id_seq'),'SUPER_USUARIO','OBTENER_ACTIVIDAD');

insert into gwf_rol_permission values (nextval('gwf_rol_permission_id_seq'),'SUPER_USUARIO','CREAR_TIPO_PROYECTO');
insert into gwf_rol_permission values (nextval('gwf_rol_permission_id_seq'),'SUPER_USUARIO','MODIFICAR_TIPO_PROYECTO');
insert into gwf_rol_permission values (nextval('gwf_rol_permission_id_seq'),'SUPER_USUARIO','ELIMINAR_TIPO_PROYECTO');
insert into gwf_rol_permission values (nextval('gwf_rol_permission_id_seq'),'SUPER_USUARIO','OBTENER_TIPO_PROYECTO');

insert into gwf_rol_permission values (nextval('gwf_rol_permission_id_seq'),'SUPER_USUARIO','CREAR_PROYECTO');
insert into gwf_rol_permission values (nextval('gwf_rol_permission_id_seq'),'SUPER_USUARIO','MODIFICAR_PROYECTO');
insert into gwf_rol_permission values (nextval('gwf_rol_permission_id_seq'),'SUPER_USUARIO','ELIMINAR_PROYECTO');
insert into gwf_rol_permission values (nextval('gwf_rol_permission_id_seq'),'SUPER_USUARIO','OBTENER_PROYECTO');

insert into gwf_rol_permission values (nextval('gwf_rol_permission_id_seq'),'SUPER_USUARIO','CREAR_EJECUCION_PROYECTO');
insert into gwf_rol_permission values (nextval('gwf_rol_permission_id_seq'),'SUPER_USUARIO','APROBAR_EJECUCION_ACTIVIDAD');
insert into gwf_rol_permission values (nextval('gwf_rol_permission_id_seq'),'SUPER_USUARIO','RECHAZAR_EJECUCION_ACTIVIDAD');
insert into gwf_rol_permission values (nextval('gwf_rol_permission_id_seq'),'SUPER_USUARIO','MODIFICAR_EJECUCION_ACTIVIDAD');
insert into gwf_rol_permission values (nextval('gwf_rol_permission_id_seq'),'SUPER_USUARIO','OBTENER_EJECUCION_PROYECTO');



insert into gwf_user values (nextval('gwf_user_id_seq'),'Luis Felipe Sosa','lsosa@gmail.com', '123', 'SUPER_USUARIO', 1);