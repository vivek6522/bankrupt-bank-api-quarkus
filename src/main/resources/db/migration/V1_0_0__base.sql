
create table `accounts` (
  `id` bigint not null auto_increment,
  account_number varchar(32),
  account_type tinyint,
  customer_id varchar(128),
  balance bigint default 0,
  preferred boolean default false,
  primary key (`id`)
);

create unique index idx_account_number ON `accounts`(account_number);
create index idx_customer_id ON `accounts`(customer_id);

create table `transfers` (
  `id` bigint not null auto_increment,
  payment_reference varchar(36) not null,
  source varchar(32) not null,
  amount bigint not null,
  target varchar(32) not null,
  description varchar(255),
  timestamp datetime not null default now()
);

create index idx_source ON `transfers`(source);

insert into `accounts` (account_number, account_type, customer_id, balance, preferred) values ('NL12ABNA0123456789', 0, 'auth0|5d7690b70c52c40ddb27b9b2', 10100, true);
insert into `accounts` (account_number, account_type, customer_id, balance) values ('NL34ABNA0123456789', 1, 'auth0|5d7690b70c52c40ddb27b9b2', 1000000);
insert into `accounts` (account_number, account_type, customer_id, balance, preferred) values ('NL80ABNA0419499482', 0, 'windowslive|3872ac62df4c490f', 9900, true);

insert into `transfers` (payment_reference, source, amount, target, description) values ('7ab6f947-fd4b-43fb-b01d-7a6ec5b18e34', 'NL80ABNA0419499482', -100, 'NL12ABNA0123456789', 'test');
insert into `transfers` (payment_reference, source, amount, target, description) values ('7ab6f947-fd4b-43fb-b01d-7a6ec5b18e34', 'NL12ABNA0123456789', 100, 'NL80ABNA0419499482', 'test');
