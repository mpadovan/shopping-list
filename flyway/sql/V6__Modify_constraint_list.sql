ALTER TABLE lists DROP INDEX name;

Alter table lists
add constraint list_name_unique
unique (name, iduser);