INSERT INTO books (id, title, author, isbn, price, is_deleted)
VALUES (1, 'Title 1', 'Author 1', '978-0307743657', 100, false);

INSERT INTO shopping_carts (id, user_id, is_deleted)
VALUES (1, 1, false);

INSERT INTO cart_items (id, book_id, quantity, shopping_cart_id, is_deleted)
VALUES (1, 1, 100, 1, false);
