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
            matriculation INT IDENTITY (1, 1) PRIMARY KEY,
            name          VARCHAR(50)        NOT NULL,
            surname       VARCHAR(50)        NOT NULL,
            role          INT                NOT NULL,
            email         VARCHAR(50) UNIQUE NOT NULL
        )
    END

-- Check if table stockmans exists, if not creates it
IF (NOT EXISTS(SELECT 1
               FROM INFORMATION_SCHEMA.TABLES
               WHERE TABLE_SCHEMA = 'dbo'
                 AND TABLE_NAME = 'stockmans'))
    BEGIN
        create table stockmans
        (
            matriculation INT IDENTITY (1, 1) PRIMARY KEY,
            name          VARCHAR(50)        NOT NULL,
            surname       VARCHAR(50)        NOT NULL,
            email         VARCHAR(50) UNIQUE NOT NULL
        )
    END
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
            matriculation INT IDENTITY (1, 1) PRIMARY KEY,
            name          VARCHAR(50)        NOT NULL,
            surname       VARCHAR(50)        NOT NULL,
            role          INT                NOT NULL,
            email         VARCHAR(50) UNIQUE NOT NULL
        )
    END

-- Check if table stockmans exists, if not creates it
IF (NOT EXISTS(SELECT 1
               FROM INFORMATION_SCHEMA.TABLES
               WHERE TABLE_SCHEMA = 'dbo'
                 AND TABLE_NAME = 'stockmans'))
    BEGIN
        create table stockmans
        (
            matriculation INT IDENTITY (1, 1) PRIMARY KEY,
            name          VARCHAR(50)        NOT NULL,
            surname       VARCHAR(50)        NOT NULL,
            email         VARCHAR(50) UNIQUE NOT NULL
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
            id         INT IDENTITY (1, 1) PRIMARY KEY,
            name       VARCHAR(50)         NOT NULL,
            brand      VARCHAR(50)         NOT NULL,
            qtyPack    SMALLINT            NOT NULL,
            dep        VARCHAR(24)         NOT NULL,
            qtyStock   TINYINT             NOT NULL,
            price      SMALLMONEY          NOT NULL,
            tag        TINYINT DEFAULT (0),
            bio        BIT     DEFAULT (0) NOT NULL,
            glutenFree BIT     DEFAULT (0) NOT NULL,
            dairyFree  BIT     DEFAULT (0) NOT NULL
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
            deliveryH       VARCHAR(50) NOT NULL,
            totalCost       SMALLMONEY  NOT NULL,
            earnedPoints    SMALLINT    NOT NULL,
            status          TINYINT     NOT NULL,
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

-- Check if table warehouse exists, if not creates it
IF (NOT EXISTS(SELECT 1
               FROM INFORMATION_SCHEMA.TABLES
               WHERE TABLE_SCHEMA = 'dbo'
                 AND TABLE_NAME = 'warehouse'))
    BEGIN
        CREATE TABLE warehouse
        (
            idProduct INT FOREIGN KEY REFERENCES products (id) PRIMARY KEY,
            qty       SMALLINT NOT NULL,
            qtyMin    SMALLINT NOT NULL,
            qtyMax    SMALLINT NOT NULL
        )
    END

-- Check if table suppliers exists, if not creates it
IF (NOT EXISTS(SELECT 1
               FROM INFORMATION_SCHEMA.TABLES
               WHERE TABLE_SCHEMA = 'dbo'
                 AND TABLE_NAME = 'suppliers'))
    BEGIN
        CREATE TABLE suppliers
        (
            pIva        INT IDENTITY (1, 1) PRIMARY KEY,
            companyName VARCHAR(50) NOT NULL,
            dep         SMALLINT    NOT NULL
        )
    END


-- Check if table orders exists, if not creates it
IF (NOT EXISTS(SELECT 1
               FROM INFORMATION_SCHEMA.TABLES
               WHERE TABLE_SCHEMA = 'dbo'
                 AND TABLE_NAME = 'orders'))
    BEGIN
        CREATE TABLE orders
        (
            id           INT IDENTITY (1, 1) PRIMARY KEY,
            pIvaSupplier INT FOREIGN KEY references suppliers (pIva),
            matrStockMan INT FOREIGN KEY references stockmans (matriculation)
        )
    END

-- Check if table productsOrder exists, if not creates it
IF (NOT EXISTS(SELECT 1
               FROM INFORMATION_SCHEMA.TABLES
               WHERE TABLE_SCHEMA = 'dbo'
                 AND TABLE_NAME = 'productsOrder'))
    BEGIN
        CREATE TABLE productsOrder
        (
            id        INT IDENTITY (1, 1) PRIMARY KEY,
            idProduct INT FOREIGN KEY REFERENCES products (id),
            idOrders  INT FOREIGN KEY REFERENCES orders (id),
            qty       SMALLINT NOT NULL
        )
    END

-- Check if table express exists, if not creates it
IF (NOT EXISTS(SELECT 1
               FROM INFORMATION_SCHEMA.TABLES
               WHERE TABLE_SCHEMA = 'dbo'
                 AND TABLE_NAME = 'express'))
    BEGIN
        CREATE TABLE express
        (
            pIva        INT IDENTITY (1, 1) PRIMARY KEY,
            companyName VARCHAR(50) NOT NULL
        )
    END

-- Check if table warehouse exists, if not creates it
IF (NOT EXISTS(SELECT 1
               FROM INFORMATION_SCHEMA.TABLES
               WHERE TABLE_SCHEMA = 'dbo'
                 AND TABLE_NAME = 'warehouse'))
    BEGIN
        CREATE TABLE warehouse
        (
            idProduct INT FOREIGN KEY REFERENCES products (id) PRIMARY KEY,
            qty       SMALLINT NOT NULL,
            qtyMin    SMALLINT NOT NULL,
            qtyMax    SMALLINT NOT NULL
        )
    END

-- Check if table suppliers exists, if not creates it
IF (NOT EXISTS(SELECT 1
               FROM INFORMATION_SCHEMA.TABLES
               WHERE TABLE_SCHEMA = 'dbo'
                 AND TABLE_NAME = 'suppliers'))
    BEGIN
        CREATE TABLE suppliers
        (
            pIva        INT IDENTITY (1, 1) PRIMARY KEY,
            companyName VARCHAR(50) NOT NULL,
            dep         SMALLINT    NOT NULL
        )
    END


-- Check if table orders exists, if not creates it
IF (NOT EXISTS(SELECT 1
               FROM INFORMATION_SCHEMA.TABLES
               WHERE TABLE_SCHEMA = 'dbo'
                 AND TABLE_NAME = 'orders'))
    BEGIN
        CREATE TABLE orders
        (
            id           INT IDENTITY (1, 1) PRIMARY KEY,
            pIvaSupplier INT FOREIGN KEY references suppliers (pIva),
            matrStockMan INT FOREIGN KEY references stockmans (matriculation)
        )
    END

-- Check if table productsOrder exists, if not creates it
IF (NOT EXISTS(SELECT 1
               FROM INFORMATION_SCHEMA.TABLES
               WHERE TABLE_SCHEMA = 'dbo'
                 AND TABLE_NAME = 'productsOrder'))
    BEGIN
        CREATE TABLE productsOrder
        (
            id        INT IDENTITY (1, 1) PRIMARY KEY,
            idProduct INT FOREIGN KEY REFERENCES products (id),
            idOrders  INT FOREIGN KEY REFERENCES orders (id),
            qty       SMALLINT NOT NULL
        )
    END

-- Check if table express exists, if not creates it
IF (NOT EXISTS(SELECT 1
               FROM INFORMATION_SCHEMA.TABLES
               WHERE TABLE_SCHEMA = 'dbo'
                 AND TABLE_NAME = 'express'))
    BEGIN
        CREATE TABLE express
        (
            pIva        INT IDENTITY (1, 1) PRIMARY KEY,
            companyName VARCHAR(50) NOT NULL
        )
    END