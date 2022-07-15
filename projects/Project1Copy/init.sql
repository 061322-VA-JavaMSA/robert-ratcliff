drop table if exists user_roles;
create type user_role as enum ('ADMIN', 'BASIC_USER');
create table user_roles(
id serial primary key,
user_role user_role
);

drop table if exists reimbursement_type;
create type reimb_type as enum ('LODGING', 'TRAVEL', 'FOOD', 'OTHER')
create table reimbursement_type(
id serial primary key,
reimb_type reimb_type
);

drop table if exists reimbursement_status;
create table reimbursement_status(
id serial primary key,
status text default 'Pending'
);

drop table if exists users cascade;
create type user_role as enum ('ADMIN', 'BASIC_USER');
create table users(
id serial primary key,
username text unique not null,
user_password text not null,
user_role user_role default 'BASIC_USER',
first_name text,
last_name text,
email text
);

drop table if exists reimbursement cascade;
create table reimbursement(
id serial primary key,
amount int,
submitted timestamp default NOW(),
resolved timestamp,
description text,
receipt text,
author int references users(id),
resolver int references users(id),
status_id int references reimbursement_status(id),
type_id int references reimbursement_type(id)
);
