create table Password
(
    id   int identity (1, 1) primary key,
    hash varchar(64) unique not null,
    salt binary(25)         not null
);

create table Admin
(
    id         int identity (1, 1) primary key,
    name       varchar(50)        not null,
    surname    varchar(50)        not null,
    email      varchar(50) unique not null,
    idPassword int foreign key references Password (id)
    -- ruolo FK o string
);

create table Client
(
    id            int identity (1, 1) primary key,
    name          varchar(50)        not null,
    surname       varchar(50)        not null,
    address       varchar(50)        not null,
    ZIP           varchar(5)         not null,
    phoneNumber   varchar(10)        not null,
    email         varchar(50) unique not null,
    paymentMethod tinyint,
    idPassword    int foreign key references Password (id)
);

create table LoyaltyCard
(
    id           int identity (1, 1) primary key,
    emissionDate date,
    points       int default 0,
    idUser int foreign key references Client (id)
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
    purchasedDate   datetime,
    deliveryDate    datetime,
    totalCost       smallmoney not null,
    earnedPoints    smallint   not null,
    status          tinyint    not null check (status between 0 and 3),
    idUser          int foreign key references Client (id),
    idPaymentMethod tinyint
);

create table ProductShopping
(
    id         int identity (1, 1) primary key,
    idProduct  int foreign key references Product (id),
    idShopping int foreign key references Shopping (id),
    qty        smallint not null
);