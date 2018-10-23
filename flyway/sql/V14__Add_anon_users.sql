alter table products alter idproductscategory set default 1;
alter table publicproducts alter idproductscategory set default 1;
alter table lists alter idcategory set default 1;

create table anonusers(
token int unsigned not null auto_increment,
lastaccess datetime default now() not null,
primary key(token)
);

create table anonlists(
tokenid int unsigned not null,
publicproductid integer not null,
foreign key(tokenid) references anonusers(token) on delete cascade,
foreign key(publicproductid) references publicproducts(id) on delete cascade
);