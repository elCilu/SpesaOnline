-- Selezione tessera utente
select id, emissionDate, points
from LoyaltyCards
where idUser = ?;

-- Visualizzazione prodotti
select name, brand, qtyPack, price, qtyStock
from Products
where dep = ?;

-- Ricerca prodotti per tipo
select name, brand, qtyPack, price, qtyStock
from Products
where tag = ?;

-- Ricerca prodotti per marca
select name, brand, qtyPack, price, qtyStock
from Products
where brand = ?;

-- Ricerca prodotti per caratteristiche
select name, brand, qtyPack, price, qtyStock
from Products
where tag = ?;

-- Prodotti ordinati: una query ricerca prodotti +
--order by price asc/desc; --(ordinato per prezzo crescente/decrescente)
--order by brand;
--(ordinato per marca)

-- Visualizza profilo utente
select name, surname, address, ZIP, phoneNumber, email, paymentMethod
from Clients

-- Visualizza saldo punti cliente
select name, surname, loyaltyCards.id, emissionDate, points
from Clients natural
         join LoyaltyCards
              on natural.id = idUser;

-- Visualizza stato spese (utente)
select name, surname, Shopping.id, purchasedDate, deliveryDate, totalCost, status
from Clients natural
         join Shopping
              on natural.id = idUser;

-- Visualizzazione prodotti spese(solo id prodotto)
select idShopping, idProduct, qty
from Shopping natural
         join ProductsShopping
              on natural.id = idShopping;

-- Visualizzazione stato spese (admin)
select *
from Shopping;
