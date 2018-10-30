alter table products alter idproductscategory set default 1;
alter table publicproducts alter idproductscategory set default 1;
alter table lists alter idcategory set default 1;

create table anonusers(
token int unsigned not null auto_increment,
idlistcategory integer not null default 1,
lastaccess datetime default now() not null,
primary key(token),
foreign key(idlistcategory) references listscategories(id)
);

create table anonlists(
tokenid int unsigned not null,
publicproductid integer not null,
quantity mediumint not null default 1,
foreign key(tokenid) references anonusers(token) on delete cascade,
foreign key(publicproductid) references publicproducts(id) on delete cascade,
unique(tokenid,publicproductid)
);