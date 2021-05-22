alter table currency_exchanges.conversion_transaction alter column id
	set maxvalue 2147483647;

alter table currency_exchanges.conversion_transaction alter column creation_date_time drop default;
