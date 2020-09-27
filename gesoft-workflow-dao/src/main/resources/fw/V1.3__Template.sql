create table gwf_project_type
(
    id BIGSERIAL primary key,
    name varchar(80) not null,
    duration smallint not null
);


create table gwf_template
(
    id BIGSERIAL primary key,
    name varchar(80) not null,
    description varchar(255) not null,
    cutting_day integer not null,
    project_type_id integer not null,
    is_order boolean not null,
    cliente_id integer not null,
    CONSTRAINT gwf_template_his_project_type_fkey FOREIGN KEY (project_type_id)
     REFERENCES gwf_project_type (id) MATCH SIMPLE
     ON UPDATE CASCADE
     ON DELETE CASCADE
);


-- Típos de Proyectos ---------------------------------------------
insert into gwf_project_type values (nextval('gwf_project_type_id_seq'), 'ANUAL', 12);
insert into gwf_project_type values (nextval('gwf_project_type_id_seq'), 'SEMESTRAL', 6);
insert into gwf_project_type values (nextval('gwf_project_type_id_seq'), 'TRIMESTRAL', 3);
insert into gwf_project_type values (nextval('gwf_project_type_id_seq'), 'BIMESTRAL', 2);
insert into gwf_project_type values (nextval('gwf_project_type_id_seq'), 'MENSUAL', 1);
