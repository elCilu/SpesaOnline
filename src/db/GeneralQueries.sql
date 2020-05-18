-- Selezione tessera utente
select id, emissionDate, points
from LoyaltyCard
where idUser = "";

-- Visualizzazione prodotti
select name, brand, qtyPack, price, "image", qtyStock
from Product
where dep = "reparto?";

-- Ricerca prodotti per tipo
select name, brand, qtyPack, price, "image", qtyStock
from Product
where type = "tipo";

-- Ricerca prodotti per marca
select name, brand, qtyPack, price, "image", qtyStock
from Product
where brand = "marca";

-- Ricerca prodotti per caratteristiche
select name, brand, qtyPack, price, "image", qtyStock
from Product
where tag = "caratteristica";

-- Prodotti ordinati: una query ricerca prodotti +
order by price asc/desc; (ordinato per prezzo crescente/decrescente)
order by brand; (ordinato per marca)

-- Visualizza profilo utente
select name, surname, address, ZIP, phoneNumber, email, paymentMethod
from Client

-- Visualizza saldo punti cliente
select name, surname, id, emissionDate, points
from Client natural join LoyaltyCard
on Client.id = idUser;

-- Visualizza stato spese (utente)
select name, surname, Shopping.id, purchasedDate, deliveryDate, totalCost, status
from Client natural join Shopping
on Client.id = idUser;

-- Visualizzazione prodotti spese(solo id prodotto)
select  idShopping, idProduct, qty
from Shopping natural join ProductShopping
on Shopping.id = idShopping;

-- Visualizzazione stato spese (admin)
select *
from Shopping;
