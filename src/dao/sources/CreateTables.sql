-- Check if table credentials exists, if not creates it
IF (NOT EXISTS(SELECT 1
               FROM INFORMATION_SCHEMA.TABLES
               WHERE TABLE_SCHEMA = 'dbo'
                 AND TABLE_NAME = 'credentials'))
    BEGIN
        CREATE TABLE credentials
        (
            id    INT IDENTITY (1, 1) PRIMARY KEY,
            email VARCHAR(50) UNIQUE NOT NULL,
            hash  VARCHAR(64) UNIQUE NOT NULL,
            salt  BINARY(25)         NOT NULL
        )
    END

-- Check if table admins exists, if not creates it
IF (NOT EXISTS(SELECT 1
               FROM INFORMATION_SCHEMA.TABLES
               WHERE TABLE_SCHEMA = 'dbo'
                 AND TABLE_NAME = 'admins'))
    BEGIN
        create table admins
        (
            id      INT IDENTITY (1, 1) PRIMARY KEY,
            name    VARCHAR(50)        NOT NULL,
            surname VARCHAR(50)        NOT NULL,
            role    INT, -- ??????
            email   VARCHAR(50) UNIQUE NOT NULL,
            -- ruolo FK o string
        )
    END

-- Check if table clients exists, if not creates it
IF (NOT EXISTS(SELECT 1
               FROM INFORMATION_SCHEMA.TABLES
               WHERE TABLE_SCHEMA = 'dbo'
                 AND TABLE_NAME = 'clients'))
    BEGIN
        CREATE TABLE clients
        (
            id            INT IDENTITY (1, 1) PRIMARY KEY,
            name          VARCHAR(50)        NOT NULL,
            surname       VARCHAR(50)        NOT NULL,
            address       VARCHAR(50)        NOT NULL,
            ZIP           VARCHAR(5)         NOT NULL,
            phoneNumber   VARCHAR(10)        NOT NULL,
            email         VARCHAR(50) UNIQUE NOT NULL,
            paymentMethod TINYINT
        )
    END

-- Check if table loyaltycards exists, if not creates it
IF (NOT EXISTS(SELECT 1
               FROM INFORMATION_SCHEMA.TABLES
               WHERE TABLE_SCHEMA = 'dbo'
                 AND TABLE_NAME = 'loyaltyCards'))
    BEGIN
        CREATE TABLE loyaltyCards
        (
            id           INT IDENTITY (1, 1) PRIMARY KEY,
            emissionDate DATE,
            points       INT DEFAULT 0,
            idUser       INT FOREIGN KEY REFERENCES clients (id)
        )
    END

-- Check if table products exists, if not creates it
IF (NOT EXISTS(SELECT 1
               FROM INFORMATION_SCHEMA.TABLES
               WHERE TABLE_SCHEMA = 'dbo'
                 AND TABLE_NAME = 'products'))
    BEGIN
        CREATE TABLE products
        (
            id       INT IDENTITY (1, 1) PRIMARY KEY,
            name     VARCHAR(50) NOT NULL,
            brand    VARCHAR(50) NOT NULL,
            qtyPack  SMALLINT    NOT NULL,
            dep      VARCHAR(24) NOT NULL,
            qtyStock TINYINT     NOT NULL,
            price    SMALLMONEY  not null,
            tag      TINYINT
        )
    END

-- Check if table shopping exists, if not creates it
IF (NOT EXISTS(SELECT 1
               FROM INFORMATION_SCHEMA.TABLES
               WHERE TABLE_SCHEMA = 'dbo'
                 AND TABLE_NAME = 'shopping'))
    BEGIN
        CREATE TABLE shopping
        (
            id              INT IDENTITY (1, 1) PRIMARY KEY,
            purchasedDate   DATETIME,
            deliveryDate    DATETIME,
            totalCost       SMALLMONEY NOT NULL,
            earnedPoints    SMALLINT   NOT NULL,
            status          TINYINT    NOT NULL,
            idUser          INT FOREIGN KEY REFERENCES clients (id),
            idPaymentMethod TINYINT
        )
    END

-- Check if table productsShopping exists, if not creates it
IF (NOT EXISTS(SELECT 1
               FROM INFORMATION_SCHEMA.TABLES
               WHERE TABLE_SCHEMA = 'dbo'
                 AND TABLE_NAME = 'productsShopping'))
    BEGIN
        CREATE TABLE productsShopping
        (
            id         INT IDENTITY (1, 1) PRIMARY KEY,
            idProduct  INT FOREIGN KEY REFERENCES products (id),
            idShopping INT FOREIGN KEY REFERENCES shopping (id),
            qty        SMALLINT NOT NULL
        )
    END