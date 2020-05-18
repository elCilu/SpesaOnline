create table Credentials
(
    id   int identity (1, 1) primary key,
    email varchar(50) unique not null,
    hash varchar(64) unique not null,
    salt binary(25)         not null
);

create table Admin
(
    id         int identity (1, 1) primary key,
    name       varchar(50)        not null,
    surname    varchar(50)        not null,
    role       int, -- ??????
    email      varchar(50) unique not null,
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
    paymentMethod tinyint
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
    price    smallmoney  not null,
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