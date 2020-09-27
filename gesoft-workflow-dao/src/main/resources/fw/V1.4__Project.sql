create table gwf_project
(
    id BIGSERIAL primary key,
    name varchar(80) not null,
    comments text not null,
    project_since timestamp not null,
    project_until timestamp null,
    year_since integer null,
    year_until integer null,
    period_since varchar(20) null,
    period_until varchar(20) null,
    gwf_client_id integer not null,
    project_type_id integer not null,
    template_id integer not null,
    user_id integer null,
    cutting_day integer not null,
    is_order boolean not null,
    cliente_id integer not null,
     CONSTRAINT gwf_project_his_gwf_client_fkey FOREIGN KEY (gwf_client_id)
     REFERENCES gwf_client (id) MATCH SIMPLE
     ON UPDATE CASCADE
     ON DELETE CASCADE,
    CONSTRAINT gwf_project_his_project_type_fkey FOREIGN KEY (project_type_id)
     REFERENCES gwf_project_type (id) MATCH SIMPLE
     ON UPDATE CASCADE
     ON DELETE CASCADE,
    CONSTRAINT gwf_project_his_template_fkey FOREIGN KEY (template_id)
     REFERENCES gwf_template (id) MATCH SIMPLE
     ON UPDATE CASCADE
     ON DELETE CASCADE
);