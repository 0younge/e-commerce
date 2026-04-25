INSERT INTO admins (name, email, password, phone_number, role, status, created_at, modified_at)
VALUES ('관리자', 'admin@test.com', '1234', '010-9999-9999', 0, 'PENDING', NOW(), NOW());

INSERT INTO users (name, email, phone_number, status, created_at, modified_at)
VALUES ('홍길동', 'user1@test.com', '010-1111-1111', 'ACTIVE', NOW(), NOW());

INSERT INTO users (name, email, phone_number, status, created_at, modified_at)
VALUES ('김철수', 'user2@test.com', '010-2222-2222', 'ACTIVE', NOW(), NOW());

INSERT INTO products (name, category, price, quantity, status, admin_id, created_at, modified_at)
VALUES ('노트북', '전자기기', 1000000, 10, 'SELLING', NULL, NOW(), NOW());

INSERT INTO products (name, category, price, quantity, status, admin_id, created_at, modified_at)
VALUES ('마우스', '전자기기', 30000, 50, 'SELLING', NULL, NOW(), NOW());