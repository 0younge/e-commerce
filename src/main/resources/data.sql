INSERT INTO admins (name, email, password, phone_number, role, status, deleted, created_at, modified_at)
VALUES ('최고관리자', 'admin@test.com', '$2a$10$8.UnVuG9HHgffUDAlk8q6uyzREX7BZ6zESXqyQg2S.vuAIv5f68FS', '010-9999-9999', 'SUPER_ADMIN', 'ACTIVE', 0, NOW(), NOW());

INSERT INTO users (name, email, phone_number, status, deleted, created_at, modified_at)
VALUES ('홍길동', 'user1@test.com', '010-1111-1111', 'ACTIVE', 1, NOW(), NOW());

INSERT INTO users (name, email, phone_number, status, deleted, created_at, modified_at)
VALUES ('김철수', 'user2@test.com', '010-2222-2222', 'ACTIVE', 1, NOW(), NOW());

INSERT INTO products (name, category, price, quantity, status, admin_id, deleted, created_at, modified_at)
VALUES ('노트북', '전자기기', 1000000, 10, 'SELLING', NULL, 1,NOW(), NOW());

INSERT INTO products (name, category, price, quantity, status, admin_id, deleted, created_at, modified_at)
VALUES ('마우스', '전자기기', 30000, 50, 'SELLING', NULL, 1, NOW(), NOW());