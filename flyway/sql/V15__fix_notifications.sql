drop function timeThreshold;

delimiter //
create function timeThreshold() returns integer deterministic # tempo in minuti nel quale se un prodotto viene aggiunto in un tempo minore (in minuti) si ignora la stima del tempo per la notifica
begin
	return 1; # minuti
end//
delimiter ;