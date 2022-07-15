insert into user_roles (user_role)  values ('ADMIN');
insert into user_roles (user_role)  values ('BASIC_USER');

insert into users (username, user_password, user_role, first_name, last_name, email) 
values ('trey', 'pass', 'ADMIN','Trey', 'Ratcliff','rlratcl2@gmail.com');

select * from users where username = 'trey' and user_password = 'pass';

insert into users (username, user_password, user_role, first_name, last_name, email) 
values ('test', 'pass','BASIC_USER', 'Test', 'Tester','test@tester.com');

insert into reimbursement_type (reimb_type) values('LODGING');
insert into reimbursement_type (reimb_type) values('TRAVEL');
insert into reimbursement_type (reimb_type) values('FOOD');
insert into reimbursement_type (reimb_type) values('OTHER');

insert into reimbursement_status (status) values('PENDING');
insert into reimbursement_status (status) values('DENIED');
insert into reimbursement_status (status) values('ACCEPTED');

insert into reimbursement (amount,submitted, resolved, description, receipt, author, resolver, status_id, type_id) 
values('10', '07-01-2022','07-07-2022', 'Lunch', '2 Hot dogs: $10', '2', '1', '3', '3');

insert into reimbursement (amount, description, receipt, author, status_id, type_id) 
values('1', 'test', 'test', '2', '1', '4');

insert into reimbursement (amount, resolved, description, receipt, author, resolver, status_id, type_id) 
values('1', null, 'test', 'test', '2', null, '1', '4');
