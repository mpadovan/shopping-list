CREATE TABLE if not exists users (
	id integer not null AUTO_INCREMENT,
	email VARCHAR(255) not null unique,
	password VARCHAR(43) NOT NULL,
	name VARCHAR(40) NOT NULL,
    lastname VARCHAR(40) NOT NULL,
    image VARCHAR(2083),
    administrator TINYINT(1) DEFAULT 0 NOT NULL,
	PRIMARY KEY (id)
);

create table if not exists listscategories(
	id integer not null AUTO_INCREMENT,
	name varchar(40) not null unique,
    description varchar(256),
    primary key (id)
);

create table if not exists listscategoriesimages(
	id integer auto_increment not null,
    image VARCHAR(2083) not null,
    idcategory integer not null,
    foreign key(idcategory) references listscategories(id),
    primary key(id)
);

create table if not exists lists(
	id integer not null AUTO_INCREMENT,
	name varchar(40) not null unique,
    iduser integer not null,
    idcategory integer not null,
    description varchar(256),
    image VARCHAR(2083),
    foreign key(iduser) references users(id),
	foreign key(idcategory) references listscategories(id),
    primary key (id,iduser)
);

create table if not exists sharedlists(
	iduser integer not null,
	idlist integer not null,
    iduserlist integer not null,
	foreign key(iduser) references users(id),
    foreign key(idlist) references lists(id),
    primary key(iduser,idlist)
);

create table if not exists productscategories(
	id integer not null AUTO_INCREMENT,
	name varchar(40) not null unique,
    category integer,
    description varchar(256),
    logo VARCHAR(2083),
    foreign key(category) references productscategories(id),
    primary key(id)
);

create table if not exists products(
	id integer not null AUTO_INCREMENT,
	name varchar(40) not null,
    note varchar(256),
    logo VARCHAR(2083),
    photography VARCHAR(2083),
    iduser integer not null,
    idproductscategory integer not null,
	foreign key(iduser) references users(id),
	foreign key(idproductscategory) references productscategories(id),
	primary key(id)
);	

create table if not exists publicproducts(
	id integer not null AUTO_INCREMENT,
	name varchar(40) not null,
    note varchar(256),
    logo VARCHAR(2083),
    photography VARCHAR(2083),
	idproductscategory integer not null,
	foreign key(idproductscategory) references productscategories(id),
    primary key(id)
);

create table if not exists productsonlists(
	idlist integer not null,
    idproduct integer not null,
	quantity mediumint default 1 not null,
    foreign key(idlist) references lists(id),
    foreign key(idproduct) references products(id),
	primary key(idlist,idproduct)
);

create table if not exists publicproductsonlists(
	idlist integer,
	idpublicproduct integer,
	quantity mediumint default 1 not null,
    foreign key(idlist) references lists(id),
    foreign key(idpublicproduct) references publicproducts(id),
	primary key(idlist,idpublicproduct)
);