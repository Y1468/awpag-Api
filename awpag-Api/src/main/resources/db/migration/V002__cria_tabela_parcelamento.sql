create table parcelamento(
    id bigint auto_increment,
    cliente_id bigint not null,
    descrisao varchar(20)not null,
    valor_total decimal(10,2)not null,
    quantidade_parcelas tinyint,
    data_criacao datetime not null,

    primary key(id)
);

alter table parcelamento add constraint fk_parcelamento_cliente
foreign key(cliente_id)references cliente(id);