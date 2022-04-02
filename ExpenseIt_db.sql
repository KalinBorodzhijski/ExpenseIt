drop database expenseitdb;
drop user expenseit;
create user expenseit with password '123456789';
create database expenseitdb with template=template0 owner=expenseit;
connect expenseitdb;
alter default privileges grant all on tables to expenseit;
alter default privileges grant all on sequences to expenseit;

 create table users
 (
     user_id     integer primary key not null,
     password    text                not null,
     first_name  varchar(30)         not null,
     second_name varchar(30)         not null,
     third_name  varchar(30)         not null,
     email       varchar(30)         not null
 );

create table categories(
    category_id integer primary key not null,
    user_id integer not null,
    title varchar(30) not null,
    description varchar(100) not null
);

alter table categories add constraint users_categories
foreign key (user_id) references users(user_id);

create table transactions
(
    transaction_id   integer primary key not null,
    category_id      integer             not null,
    user_id          integer             not null,
    amount           numeric(10, 2)      not null,
    description      varchar(100)        not null,
    transaction_time TIMESTAMP
);

ALTER TABLE transactions add constraint category_transaction
foreign key (category_id) references categories(category_id);
alter table users ADD constraint users_transaction
foreign key (user_id) references users(user_id);

create sequence users_seq increment 1 start 1;
create sequence categories_seq increment 1 start 1;
create sequence transactions_seq increment 1 start 1000;