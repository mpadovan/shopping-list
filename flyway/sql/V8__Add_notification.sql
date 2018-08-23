CREATE TABLE if not exists productsnotification(
	id integer auto_increment,
	time datetime(1) not null,
    idlist integer not null,
    idproduct integer,			# id del prodotto o del prodotto pubblico
    ispublicproduct tinyint(1),	# 1 se il prodotto é un prodotto pubblico, 0 se é privato
    text varchar(255),
    isread tinyint(1) default 0,
    foreign key(idproduct) references products,
    primary key(id)
);

ALTER TABLE productsonlists
ADD lastinsert datetime(1) default now(1),
ADD exp_average float,
ADD notification_count mediumint unsigned default 0;

ALTER TABLE publicproductsonlists
ADD lastinsert datetime(1) default now(1),
ADD exp_average float,
ADD notification_count mediumint unsigned default 0;