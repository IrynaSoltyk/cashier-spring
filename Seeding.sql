insert into users (enabled, login, password, username)
values (true, 'commodityexpert', '$2a$12$K701OtpwkwkMQdUqCbXRRen0YqNii/uSP9LxtIhBjqiY8FQdS46NK', 'Василь Петрович Пупкін');
insert into users (enabled, login, password, username)
values (true, 'cashier', '$2a$12$K701OtpwkwkMQdUqCbXRRen0YqNii/uSP9LxtIhBjqiY8FQdS46NK', 'Людмила Іванівна Горобець');
insert into users (enabled, login, password, username)
values (true, 'cashiermanager', '$2a$12$K701OtpwkwkMQdUqCbXRRen0YqNii/uSP9LxtIhBjqiY8FQdS46NK', 'Василіса Йосипівна Жук');
insert into roles (name) values ('COMMODITYEXPERT');
insert into roles (name) values ('CASHIER');
insert into roles (name) values ('CASHIERMANAGER');
insert into users_roles (user_id, role_id) values (1,1);
insert into users_roles (user_id, role_id) values (2,2);
insert into users_roles (user_id, role_id) values (3,3);