
drop table if exists customer;
create table customer(
id serial primary key,
username text,
password text,
isEmployee bool default false
);

drop table if exists item;
create table item(
id serial primary key,
item_name text,
price float,
availability bool default true
);

drop table if exists payment;
create table payment(
customer_id int references customer(id),
item_id int references item(id),
primary key (customer_id, item_id),
balance float
);

drop table if exists offer;
create table offer(
id serial primary key,
customer_id int references customer(id),
item_id int references item(id),
amount float,
accepted text default 'pending'
);

drop table if exists owned_items;
create table owned_items(
customer_id int references customer(id),
item_id int references item(id),
primary key (customer_id, item_id)
);


















                                [ Read 0 lines ]
^G Help      ^O Write Out ^W Where Is  ^K Cut       ^T Execute   ^C Location
^X Exit      ^R Read File ^\ Replace   ^U Paste     ^J Justify   ^/ Go To Line
