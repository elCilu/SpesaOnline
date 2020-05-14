create table [User]
(
    id            int identity (1, 1) primary key,
    name          varchar(50) not null,
    surname       varchar(50) not null,
    address       varchar(50) not null,
    ZIP           int         not null check (ZIP between 10000 and 99999),                   --Aggiungere zerofill
    phoneNumber   bigint      not null check (phoneNumber between 1000000000 and 9999999999), --Aggiungere zerofill
    email         varchar(50) not null,
    paymentMethod tinyint,
    idPassword    int foreign key references Password (id)
);

create table LoyaltyCard
(
    id           int identity (1, 1) primary key,
    emissionDate date,
    points       int default 0,
    idUser       int foreign key references [User] (id)
);

create table Password
(
    id   int primary key,
    hash varchar(250) unique not null,
    salt varchar(250) default '!Pr0g3Tt01S20_20#'
);

create table Product
(
    id       int identity (1, 1) primary key,
    name     varchar(50) not null,
    brand    varchar(50) not null,
    qtyPack  smallint    not null,
    dep      varchar(20) not null,
    qtyStock tinyint     not null,
    prize    smallmoney  not null,
    tag tinyint
);

create table Shopping
(
    id              int identity (1, 1) primary key,
    carriedDate     datetime,
    deliveryDate    datetime,
    totalCost       smallmoney not null,
    earnedPoints    smallint   not null,
    status          tinyint    not null check (status between 0 and 3),
    idUser          int foreign key references [User] (id),
    idPaymentMethod tinyint
);

create table ProductShopping
(
    id         int identity (1, 1) primary key,
    idProduct  int foreign key references Product (id),
    idShopping int foreign key references Shopping (id),
    qty        smallint not null
);

create table Admin
(
    id         int identity (1, 1) primary key,
    name       varchar(50) not null,
    surname    varchar(50) not null,
    email      varchar(50) not null,
    idPassword int foreign key references Password (id)
    -- ruolo FK o string
)