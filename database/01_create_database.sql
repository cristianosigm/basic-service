-- removing current database
drop user if exists sigm;
drop database if exists sigm;

-- creating database and schemas
create database sigm;

-- creating users
create user basicadm with password 'obiwan';

-- grant access
grant all privileges on database sigm to basicadm;