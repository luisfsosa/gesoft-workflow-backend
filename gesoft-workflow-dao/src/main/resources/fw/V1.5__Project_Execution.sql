create table gwf_project_execution
(
    id BIGSERIAL primary key,
    name varchar(80) not null,
    project_since timestamp not null,
    project_until timestamp null,
    year integer null,
    period varchar(50) null,
    gwf_client_id integer not null,
    project_type_id integer not null,
    project_id integer not null,
    template_id integer not null,
    user_id integer null,
    project_advance integer not null,
    is_order boolean not null,
    cliente_id integer not null,
    CONSTRAINT gwf_project_exe_his_gwf_client_fkey FOREIGN KEY (gwf_client_id)
     REFERENCES gwf_client (id) MATCH SIMPLE
     ON UPDATE CASCADE
     ON DELETE CASCADE,
    CONSTRAINT gwf_project_exe_his_project_type_fkey FOREIGN KEY (project_type_id)
     REFERENCES gwf_project_type (id) MATCH SIMPLE
     ON UPDATE CASCADE
     ON DELETE CASCADE,
    CONSTRAINT gwf_project_his_template_fkey FOREIGN KEY (template_id)
     REFERENCES gwf_template (id) MATCH SIMPLE
     ON UPDATE CASCADE
     ON DELETE CASCADE,
    CONSTRAINT gwfproject_exe_his_project_fkey FOREIGN KEY (project_id)
     REFERENCES gwf_project (id) MATCH SIMPLE
     ON UPDATE CASCADE
     ON DELETE CASCADE
);


create table gwf_activity
(
    id varchar(80) primary key,
    name varchar(80) not null,
    description varchar(255) not null,
    weighing smallint not null,
    activity_type varchar(80) not null,
    evidence_required  boolean not null,
    authorizer integer null,
    responsable integer null,
    activity_order integer not null,
    predecessor_activities text[],
    template_id integer null,
    project_id integer null,
    status varchar(80) null,
    activity_advance integer null,
    comments boolean null,
    attached boolean null,
    project_execution_id integer null,
    cliente_id integer not null,
    CONSTRAINT gwf_activity_his_template_fkey FOREIGN KEY (template_id)
     REFERENCES gwf_template (id) MATCH SIMPLE
     ON UPDATE CASCADE
     ON DELETE CASCADE,
    CONSTRAINT gwf_activity_his_project_fkey FOREIGN KEY (project_id)
     REFERENCES gwf_project (id) MATCH SIMPLE
     ON UPDATE CASCADE
     ON DELETE CASCADE,
    CONSTRAINT gwf_activity_his_project_exec_fkey FOREIGN KEY (project_execution_id)
     REFERENCES gwf_project_execution (id) MATCH SIMPLE
     ON UPDATE CASCADE
     ON DELETE CASCADE
);


create table gwf_activity_comment
(
    id BIGSERIAL primary key,
    comment varchar(255) not null,
    user_name varchar(80) not null,
    comment_date timestamp not null,
    activity_id varchar(80) not null,
    cliente_id integer not null,
    CONSTRAINT gwf_activity_comment_his_gwf_activity_fkey FOREIGN KEY (activity_id)
     REFERENCES gwf_activity (id) MATCH SIMPLE
     ON UPDATE CASCADE
     ON DELETE CASCADE
);

create table gwf_activity_attachment
(
    id BIGSERIAL primary key,
    file_name varchar(80) not null,
    attachment text not null,
    user_name varchar(80) not null,
    attachment_date timestamp not null,
    activity_id varchar(80) not null,
    cliente_id integer not null,
    CONSTRAINT gwf_activity_attached_his_gwf_activity_fkey FOREIGN KEY (activity_id)
     REFERENCES gwf_activity (id) MATCH SIMPLE
     ON UPDATE CASCADE
     ON DELETE CASCADE
);