ALTER TABLE sharedlists
drop foreign key sharedlists_ibfk_1,
drop foreign key sharedlists_ibfk_2;

alter table sharedlists
add constraint sharedlists_ibfk_1 foreign key(iduser) references users(id) on delete cascade,
add constraint sharedlists_ibfk_2 foreign key(idlist) references lists(id) on delete cascade;

ALTER TABLE productsonlists
drop foreign key productsonlists_ibfk_1,
drop foreign key productsonlists_ibfk_2;

alter table productsonlists
add constraint productsonlists_ibfk_1 foreign key(idlist) references lists(id) on delete cascade,
add constraint productsonlists_ibfk_2 foreign key(idproduct) references products(id) on delete cascade;

ALTER TABLE publicproductsonlists
drop foreign key publicproductsonlists_ibfk_1,
drop foreign key publicproductsonlists_ibfk_2;

alter table publicproductsonlists
add constraint publicproductsonlists_ibfk_1 foreign key(idlist) references lists(id) on delete cascade,
add constraint publicproductsonlists_ibfk_2 foreign key(idpublicproduct) references publicproducts(id) on delete cascade;

ALTER TABLE productsnotification
drop foreign key productsnotification_ibfk_3;

alter table productsnotification
add  constraint productsnotification_ibfk_3 foreign key(idlist) references lists(id) on delete cascade;

ALTER TABLE products
drop foreign key products_ibfk_1;

alter table products
add constraint products_ibfk_1 foreign key(iduser) references users(id) on delete cascade;

ALTER TABLE listscategoriesimages
drop foreign key listscategoriesimages_ibfk_1;

alter table listscategoriesimages
add constraint listscategoriesimages_ibfk_1 foreign key(idcategory) references listscategories(id) on delete cascade;

ALTER TABLE lists
drop foreign key lists_ibfk_1;

alter table lists
add constraint lists_ibfk_1 foreign key(iduser) references users(id) on delete cascade;

ALTER TABLE messages
drop foreign key messages_ibfk_1,
drop foreign key messages_ibfk_2;

alter table messages
add constraint messages_ibfk_1 foreign key(iduser) references users(id) on delete cascade,
add constraint messages_ibfk_2 foreign key(idlist) references lists(id) on delete cascade;

delimiter //
CREATE PROCEDURE deleteProductsCategory(IN _id integer)
BEGIN
	if exists (select 1 from productscategories where id = _id limit 1) and _id != 1 then
		update products set idproductscategory = 1 where idproductscategory = _id;
		update publicproducts set idproductscategory = 1 where idproductscategory = _id;
        update productscategories set category = 1 where category = _id;
		delete from productscategories where id = _id;
    end if;
END//

CREATE PROCEDURE deleteListsCategory(IN _id integer)
BEGIN
	if exists (select 1 from listscategories where id = _id limit 1) and _id != 1 then
		update lists set idcategory = 1 where idcategory = _id;
		delete from listscategories where id = _id;
    end if;
END//
delimiter ;
