ALTER TABLE sharedlists
ADD modifylist tinyint(1) default 0 not null,
ADD deletelist tinyint(1) default 0 not null,
ADD adddelete tinyint(1) default 1 not null;