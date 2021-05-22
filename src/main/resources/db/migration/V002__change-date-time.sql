create schema if not exists currency_exchanges;
    
create table if not exists currency_exchanges.conversion_transaction
(
    id                       bigint          not null generated always as identity
        constraint pk_conversion_transaction
            primary key,
    user_id                  bigint          not null,
    origin_currency          varchar(5)      not null,
    origin_value             numeric         not null,
    destiny_currency         varchar(5)      not null,
    conversion_rate          numeric         not null,
    creation_date_time       timestamptz     not null
);
