create table PaymentMethod
(
    id   tinyint primary key,
    name varchar(20)
);

create table [User]
(
    id              int identity (1, 1) primary key,
    name            varchar(50) not null,
    surname         varchar(50) not null,
    address         varchar(50) not null,
    ZIP             int         not null check (ZIP between 0 and 99999),
    phoneNumber     bigint      not null check (phoneNumber between 0 and 999999999),
    email           varchar(50) not null,
    admin           bit         not null,
    idPaymentMethod tinyint foreign key references PaymentMethod (id)
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
    idUser int foreign key references [User] (id),
    hash   varchar(250) unique not null,
    salt   varchar(250) default '!Pr0g3Tt01S20_20#'
);

create table Product
(
    id       int identity (1, 1) primary key,
    name     varchar(50) not null,
    brand    varchar(50) not null,
    qtyPack  smallint    not null,
    dep      varchar(20),
    qtyStock tinyint     not null,
    prize    smallmoney  not null,
    [image]  binary(70)
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
    idPaymentMethod tinyint foreign key references PaymentMethod (id)
);

create table ProductShopping
(
    id         int identity (1, 1) primary key,
    idProduct  int foreign key references Product (id),
    idShopping int foreign key references Shopping (id)
);