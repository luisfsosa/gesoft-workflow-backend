create table gwf_client
(
    id BIGSERIAL primary key,
    business_name varchar(120) not null,
    address json,
    active boolean not null,
    cliente_id integer not null,
    unique (business_name, cliente_id)
);

ALTER TABLE gwf_client
ADD CONSTRAINT business_name_unique unique (business_name, cliente_id)