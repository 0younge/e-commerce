INSERT INTO admins (name, email, password, phone_number, role, status, deleted, created_at, modified_at)
VALUES ('관리자', 'admin@test.com', '$2a$10$D65zesWQWcOekANz1GW0OeZ/PH3CRK/vhb3tDUrCsDSoEFSfw8z/m', '010-9999-9999', 'SUPER_ADMIN', 'ACTIVE', false, NOW(), NOW());

INSERT INTO users (name, email, phone_number, status, deleted, created_at, modified_at)
VALUES ('홍길동', 'user1@test.com', '010-1111-1111', 'ACTIVE', false, NOW(), NOW());

INSERT INTO users (name, email, phone_number, status, deleted, created_at, modified_at)
VALUES ('김철수', 'user2@test.com', '010-2222-2222', 'ACTIVE', false, NOW(), NOW());

INSERT INTO products (name, category, price, quantity, status, deleted, admin_id, created_at, modified_at)
VALUES ('노트북', '전자기기', 1000000, 10, 'SELLING', false, NULL, NOW(), NOW());

INSERT INTO products (name, category, price, quantity, status, deleted, admin_id, created_at, modified_at)
VALUES ('마우스', '전자기기', 30000, 50, 'SELLING', false, NULL, NOW(), NOW());