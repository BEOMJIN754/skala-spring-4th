-- =========================
-- 초기 고객 데이터 3개
-- =========================
INSERT INTO customers (
    customer_id,
    encoded_password,
    customer_point,
    created_at,
    role
) VALUES
('user1', 'password1', 1000000.0, CURRENT_TIMESTAMP, 'USER'),
('user2', 'password2', 2000000.0, CURRENT_TIMESTAMP, 'USER'),
('user3', 'password3', 3000000.0, CURRENT_TIMESTAMP, 'USER');


-- =========================
-- 초기 상품 데이터 5개
-- =========================
INSERT INTO product (
    product_name,
    product_price,
    quantity
) VALUES
('노트북', 1200000.0, 10),
('키보드', 120000.0, 30),
('마우스', 50000.0, 50),
('모니터', 350000.0, 20),
('헤드셋', 80000.0, 40);


-- =========================
-- 초기 주문 데이터 5개
-- customer_id → customers.id
-- product_id  → product.id
-- =========================
INSERT INTO orders (
    customer_id,
    product_id,
    quantity
) VALUES
(1, 2, 2),  -- user1: 키보드 2개
(1, 3, 1),  -- user1: 마우스 1개
(2, 4, 2),  -- user2: 모니터 2개
(2, 5, 1),  -- user2: 헤드셋 1개
(3, 1, 1);  -- user3: 노트북 1개