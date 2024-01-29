-- xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
-- CLIMATE SERVICE
-- xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

-- dropping schema
drop schema if exists basicservice cascade;

-- creating schema and tables
create schema basicservice;

-- basic tables
-- ==================================================

-- address
create table if not exists basicservice.address (
	id serial primary key,
	street varchar(256),
	address_number varchar(16),
	complement varchar(64),
	district varchar(64),
	city varchar(64),
	address_state char(5),
	address_country char(2),
	postal_code varchar(10)
);

-- person
create table if not exists basicservice.person (
	id serial primary key,
	address_id integer,
	first_name varchar(128) not null,
	last_name varchar(128),
	display_name varchar(32),
	email varchar(128) not null unique,
	phone_mobile varchar(17),
	phone_home varchar(17),
	phone_contact varchar(17)
);

-- user
create table if not exists basicservice.csuser (
	id serial primary key,
	person_id integer not null,
	csuser_role varchar(32) not null,
	language_code varchar(5) not null,
	username varchar(128) not null unique,
	password varchar(128) not null,
	active boolean not null default true,
	locked boolean not null default false,
	failed_attempts smallint not null default 0
);

-- login_event
-- drop table if exists basicservice.login_event;
create table if not exists basicservice.login_event (
	id serial primary key,
	csuser_id integer not null,
	failed boolean not null default false,
	created timestamp not null default now()
);

-- password reset event
create table if not exists basicservice.password_reset_event (
	id serial primary key,
	user_id integer not null,
	code varchar(128) not null,
	requested timestamp not null,
	completed timestamp,
	successful boolean not null default false
);

-- application tables
-- ==================================================

-- company
-- drop table if exists basicservice.company;
create table if not exists basicservice.company (
	id serial primary key,
	address_id integer,
	name varchar(128) not null,
	onboarding varchar(10) not null,
	active boolean not null default true
);

-- equipment
create table if not exists basicservice.equipment (
	id serial primary key,
	company_id integer not null,
	name varchar(128) not null,
	identifier varchar(256),
	notes varchar(1024),
	active boolean not null default true
);

-- event
create table if not exists basicservice.event (
	id serial primary key,
	equipment_id integer not null,
	technician_id integer not null,
	scheduled timestamp not null,
	executed boolean not null default false,
	execution_date timestamp,
	notes varchar(1024)
);

-- part
create table if not exists basicservice.part (
	id serial primary key,
	event_id integer, 
	name varchar(128) not null,
	purchase_date date not null,
	purchase_value bigint not null default 0,
	available boolean not null default true
);

-- granting permissions
grant all privileges on schema basicservice to basicadm;
grant all privileges on all tables in schema basicservice to basicadm;
grant usage, select on all sequences in schema basicservice to basicadm;
