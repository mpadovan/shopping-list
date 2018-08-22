CREATE TABLE if not exists messages(
	id integer not null AUTO_INCREMENT,
    iduser integer not null,
    idlist integer not null,
    sendtime datetime(1) not null,
    text varchar(255) not null,
    foreign key(iduser) references users(id),
    foreign key(idlist) references lists(id),
    INDEX(sendtime),
    primary key(id),
    unique(iduser,idlist,sendtime)
);

ALTER TABLE sharedlists
ADD lastchataccess datetime not null default CURRENT_TIMESTAMP;

delimiter //
CREATE PROCEDURE setLastAccess(_iduser integer,_idlist integer,lastaccess datetime)
BEGIN
	update sharedlists SET lastchataccess = lastaccess WHERE iduser = _iduser AND idlist = _idlist;
END//
delimiter ;