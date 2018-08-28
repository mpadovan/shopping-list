CREATE TABLE if not exists productsnotification(
	id integer auto_increment,
	time datetime(1) not null,
    idlist integer not null,
    idproduct integer,
    idpublicproduct integer,
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
create function timeThreshold() returns integer deterministic # tempo in minuti nel quale se un prodotto viene aggiunto in un tempo minore (in minuti) si ignora la stima del tempo per la notifica
begin
	return 60;
end//

create function countThreshold() returns integer deterministic # numero di inserimenti prima di creare una nuova notifica (se countThreshold = 3 al terzo inserimento si notifica per la prossima volta)
begin
	return 3;
end//

CREATE FUNCTION exp_average_func(_lastinsert datetime(1),_exp_average_prec integer) returns integer deterministic# minuti di intervallo di previsione per il prossimo inserimento
BEGIN
	set @alpha = 0.5;
	set @minutes = abs(timestampdiff(MINUTE,now(1),_lastinsert));
    return if(_exp_average_prec < 0, @minutes , @minutes*@alpha + _exp_average_prec*(1-@alpha));
END//

CREATE PROCEDURE addProductOnList(IN _idproduct integer,IN _idlist integer)
BEGIN
	if exists (select 1 from productsonlists where idproduct = _idproduct and idlist = _idlist limit 1)
    then
		set @lastinsert = (select lastinsert from productsonlists where idproduct = _idproduct and idlist = _idlist);
        if abs(timestampdiff(MINUTE,now(1),@lastinsert)) >= timeThreshold() then
			UPDATE productsonlists set 	quantity = 1,
										exp_average = @exp_average := exp_average_func(lastinsert,exp_average),
										add_count = @add_count := add_count + 1,
										lastinsert = @now := now(1)
			where idproduct = _idproduct and idlist = _idlist;
			if @add_count >= countThreshold() then 
				insert into productsnotification (time,idlist,idproduct,idpublicproduct) values (timestampadd(minute,@exp_average,@now),_idlist,_idproduct,null);
			end if;
		else
			UPDATE productsonlists set quantity = 1 where idproduct = _idproduct and idlist = _idlist;
		end if;
		
        
    else
		INSERT INTO productsonlists (idproduct,idlist,quantity,add_count) VALUES (_idproduct,_idlist,1,1);
	end if;
END//

CREATE PROCEDURE addPublicProductOnList(IN _idproduct integer,IN _idlist integer)
BEGIN
	if exists (select 1 from publicproductsonlists where idpublicproduct = _idproduct and idlist = _idlist limit 1)
    then
		set @lastinsert = (select lastinsert from publicproductsonlists where idpublicproduct = _idproduct and idlist = _idlist);
        if abs(timestampdiff(MINUTE,now(1),@lastinsert)) >= timeThreshold() then
			UPDATE publicproductsonlists set 	quantity = 1,
												exp_average = @exp_average := exp_average_func(lastinsert,exp_average),
												add_count = @add_count := add_count + 1,
												lastinsert = @now := now(1)
			where idpublicproduct = _idproduct and idlist = _idlist;
			if @add_count >= countThreshold() then 
				insert into productsnotification (time,idlist,idproduct,idpublicproduct) values (timestampadd(minute,@exp_average,@now),_idlist,null,_idproduct);
			end if;
		else
			UPDATE publicproductsonlists set quantity = 1 where idpublicproduct = _idproduct and idlist = _idlist;
		end if;
    else
		INSERT INTO publicproductsonlists (idpublicproduct,idlist,quantity,add_count) VALUES (_idproduct,_idlist,1,1);
	end if;
END//

CREATE PROCEDURE deleteProductOnList(IN _idproduct integer,IN _idlist integer)
BEGIN
	UPDATE productsonlists set quantity = -1 where idproduct = _idproduct and idlist = _idlist;
END//

CREATE PROCEDURE deletePublicProductOnList(IN _idproduct integer,IN _idlist integer)
BEGIN
	UPDATE publicproductsonlists set quantity = -1 where idpublicproduct = _idproduct and idlist = _idlist;
END//
delimiter ;