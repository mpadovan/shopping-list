CREATE TABLE if not exists users (
	email VARCHAR(255),
	password VARCHAR(40) NOT NULL,
	name VARCHAR(40) NOT NULL,
    lastname VARCHAR(40) NOT NULL,
    image VARCHAR(2083),
    administrator TINYINT(1) DEFAULT 0 NOT NULL,
	PRIMARY KEY (email)
);

create table if not exists listscategories(
	name varchar(40) not null,
    description varchar(256),
    primary key (name)
);

create table if not exists listscategoriesimages(
	id integer auto_increment not null,
    image VARCHAR(2083) not null,
    category varchar(40) not null,
    foreign key(category) references listscategories(name),
    primary key(id)
);

create table if not exists lists(
	name varchar(40) not null,
    email VARCHAR(255) not null,
    category varchar(40) not null,
    description varchar(256),
    image VARCHAR(2083),
    foreign key(email) references users(email),
	foreign key(category) references listscategories(name),
    primary key (email,name)
);

create table if not exists sharedlists(
	email varchar(255) not null,
	emaillist varchar(255) not null,
    namelist varchar(32) not null,
	foreign key(email) references users(email),
    foreign key(emaillist, namelist) references lists(email,name),
    primary key(email,emaillist,namelist)
);

create table if not exists productscategories(
	name varchar(40) not null,
    category varchar(40),
    description varchar(256),
    logo VARCHAR(2083),
    foreign key(category) references productscategories(name),
    primary key(name)
);

create table if not exists products(
	name varchar(40) not null,
    note varchar(256),
    logo VARCHAR(2083),
    photography VARCHAR(2083),
    email varchar(255) not null,
    category varchar(40) not null,
	foreign key(email) references users(email),
	foreign key(category) references productscategories(name),
	primary key(email,name)
);	

create table if not exists publicproducts(
	name varchar(40) not null,
    note varchar(256),
    logo VARCHAR(2083),
    photography VARCHAR(2083),
	category varchar(40) not null,
	foreign key(category) references productscategories(name),
    primary key(name)
);

create table if not exists productsonlists(
	emaillist varchar(255),
    namelist varchar(40),
    emailprod varchar(255),
    nameprod varchar(40),
	quantity mediumint default 1 not null,
    foreign key(emaillist,namelist) references lists(email,name),
    foreign key(emailprod,nameprod) references products(email,name),
	primary key(emaillist,namelist,emailprod,nameprod)
);

create table if not exists publicproductsonlists(
	emaillist varchar(255),
    namelist varchar(40),
    nameprod varchar(40),
	quantity mediumint default 1 not null,
    foreign key(emaillist,namelist) references lists(email,name),
    foreign key(nameprod) references publicproducts(name),
	primary key(emaillist,namelist,nameprod)
);