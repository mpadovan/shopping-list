CREATE TABLE if not exists productsnotification(
	id integer auto_increment,
	time datetime(1) not null,
    idlist integer not null,
    idproduct integer,
    idpublicproduct integer,
    text varchar(255),
    isread tinyint(1) default 0,
    foreign key(idproduct) references products(id),
    foreign key(idpublicproduct) references publicproducts(id),
    foreign key(idlist) references lists(id),
    primary key(id)
);

ALTER TABLE productsonlists
ADD lastinsert datetime(1) default now(1),
ADD exp_average integer default -1,
ADD add_count mediumint unsigned default 0;

ALTER TABLE publicproductsonlists
ADD lastinsert datetime(1) default now(1),
ADD exp_average integer default -1,
ADD add_count mediumint unsigned default 0;

delimiter //
CREATE FUNCTION exp_average_func(_lastinsert datetime(1),_exp_average_prec integer) returns integer deterministic# minuti di intervallo di previsione per il prossimo inserimento
BEGIN
	set @alpha = 0.5;
	set @minutes = TIMESTAMPDIFF(MINUTE,now(1),_lastinsert);
    return if(_exp_average_prec < 0, @minutes , @minutes*@alpha + _exp_average_prec*(1-@alpha));
END//
delimiter ;

delimiter //
CREATE PROCEDURE addProductOnList(IN _idproduct integer,IN _idlist integer)
BEGIN
	if exists (select 1 from productsonlists where idproduct = _idproduct and idlist = _idlist limit 1)
    then
		UPDATE productsonlists set quantity = 1,exp_average = exp_average_func(lastinsert,exp_average),lastinsert = now(1),add_count = add_count + 1 where idproduct = _idproduct and idlist = _idlist;
    else
		INSERT INTO productsonlists (idproduct,idlist,quantity,add_count) VALUES (_idproduct,_idlist,1,1);
	end if;
END//
delimiter ;

delimiter //
CREATE PROCEDURE addPublicProductOnList(IN _idproduct integer,IN _idlist integer)
BEGIN
	if exists (select 1 from publicproductsonlists where idpublicproduct = _idproduct and idlist = _idlist limit 1)
    then
		UPDATE publicproductsonlists set quantity = 1,exp_average = exp_average_func(lastinsert,exp_average),lastinsert = now(1),add_count = add_count + 1 where idpublicproduct = _idproduct and idlist = _idlist;
    else
		INSERT INTO publicproductsonlists (idpublicproduct,idlist,quantity,add_count) VALUES (_idproduct,_idlist,1,1);
	end if;
END//
delimiter ;

delimiter //
CREATE PROCEDURE deleteProductOnList(IN _idproduct integer,IN _idlist integer)
BEGIN
	UPDATE productsonlists set quantity = -1 where idproduct = _idproduct and idlist = _idlist;
END//
delimiter ;

delimiter //
CREATE PROCEDURE deletePublicProductOnList(IN _idproduct integer,IN _idlist integer)
BEGIN
	UPDATE publicproductsonlists set quantity = -1 where idpublicproduct = _idproduct and idlist = _idlist;
END//
delimiter ;