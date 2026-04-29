INSERT INTO admins (name, email, password, phone_number, role, status, deleted, created_at, modified_at)
VALUES ('슈퍼관리자', 'SuperAdmin@test.com', '$2a$10$D65zesWQWcOekANz1GW0OeZ/PH3CRK/vhb3tDUrCsDSoEFSfw8z/m', '010-9999-9999', 'SUPER_ADMIN', 'ACTIVE', false, NOW(), NOW());

INSERT INTO admins (name, email, password, phone_number, role, status, deleted, created_at, modified_at)
VALUES ('운영관리자', 'OperAdmin@test.com', '$2a$10$D65zesWQWcOekANz1GW0OeZ/PH3CRK/vhb3tDUrCsDSoEFSfw8z/m', '010-9999-9999', 'OPERATION_ADMIN', 'PENDING', false, NOW(), NOW());

INSERT INTO admins (name, email, password, phone_number, role, status, deleted, created_at, modified_at)
VALUES ('CS관리자', 'CsAdmin@test.com', '$2a$10$D65zesWQWcOekANz1GW0OeZ/PH3CRK/vhb3tDUrCsDSoEFSfw8z/m', '010-9999-9999', 'CS_ADMIN', 'ACTIVE', false, NOW(), NOW());

INSERT INTO users (name, email, phone_number, status, deleted, created_at, modified_at)
VALUES ('홍길동1', 'user1@test.com', '010-1000-0001', 'ACTIVE', false, NOW(), NOW());

INSERT INTO users (name, email, phone_number, status, deleted, created_at, modified_at)
VALUES ('홍길동2', 'user2@test.com', '010-1000-0002', 'ACTIVE', false, NOW(), NOW());

INSERT INTO users (name, email, phone_number, status, deleted, created_at, modified_at)
VALUES ('홍길동3', 'user3@test.com', '010-1000-0003', 'ACTIVE', false, NOW(), NOW());

INSERT INTO users (name, email, phone_number, status, deleted, created_at, modified_at)
VALUES ('홍길동4', 'user4@test.com', '010-1000-0004', 'ACTIVE', false, NOW(), NOW());

INSERT INTO users (name, email, phone_number, status, deleted, created_at, modified_at)
VALUES ('홍길동5', 'user5@test.com', '010-1000-0005', 'ACTIVE', false, NOW(), NOW());

INSERT INTO users (name, email, phone_number, status, deleted, created_at, modified_at)
VALUES ('김철수1', 'user6@test.com', '010-2000-0001', 'ACTIVE', false, NOW(), NOW());

INSERT INTO users (name, email, phone_number, status, deleted, created_at, modified_at)
VALUES ('김철수2', 'user7@test.com', '010-2000-0002', 'ACTIVE', false, NOW(), NOW());

INSERT INTO users (name, email, phone_number, status, deleted, created_at, modified_at)
VALUES ('김철수3', 'user8@test.com', '010-2000-0003', 'ACTIVE', false, NOW(), NOW());

INSERT INTO users (name, email, phone_number, status, deleted, created_at, modified_at)
VALUES ('이영희1', 'user9@test.com', '010-3000-0001', 'ACTIVE', false, NOW(), NOW());

INSERT INTO users (name, email, phone_number, status, deleted, created_at, modified_at)
VALUES ('이영희2', 'user10@test.com', '010-3000-0002', 'ACTIVE', false, NOW(), NOW());

INSERT INTO users (name, email, phone_number, status, deleted, created_at, modified_at)
VALUES ('이영희3', 'user11@test.com', '010-3000-0003', 'ACTIVE', false, NOW(), NOW());

INSERT INTO users (name, email, phone_number, status, deleted, created_at, modified_at)
VALUES ('박민수1', 'user12@test.com', '010-4000-0001', 'ACTIVE', false, NOW(), NOW());

INSERT INTO users (name, email, phone_number, status, deleted, created_at, modified_at)
VALUES ('박민수2', 'user13@test.com', '010-4000-0002', 'ACTIVE', false, NOW(), NOW());

INSERT INTO users (name, email, phone_number, status, deleted, created_at, modified_at)
VALUES ('박민수3', 'user14@test.com', '010-4000-0003', 'ACTIVE', false, NOW(), NOW());

INSERT INTO users (name, email, phone_number, status, deleted, created_at, modified_at)
VALUES ('테스트유저', 'user15@test.com', '010-9999-9999', 'ACTIVE', false, NOW(), NOW());

INSERT INTO products (name, category, price, quantity, status, deleted, admin_id, created_at, modified_at)
VALUES ('노트북1', '전자기기', 1000000, 10, 'FOR_SALE', false, 1, NOW(), NOW());

INSERT INTO products (name, category, price, quantity, status, deleted, admin_id, created_at, modified_at)
VALUES ('노트북2', '전자기기', 1200000, 8, 'FOR_SALE', false, 1, NOW(), NOW());

INSERT INTO products (name, category, price, quantity, status, deleted, admin_id, created_at, modified_at)
VALUES ('노트북3', '전자기기', 900000, 12, 'FOR_SALE', false, 1, NOW(), NOW());

INSERT INTO products (name, category, price, quantity, status, deleted, admin_id, created_at, modified_at)
VALUES ('마우스1', '전자기기', 30000, 50, 'FOR_SALE', false, 1, NOW(), NOW());

INSERT INTO products (name, category, price, quantity, status, deleted, admin_id, created_at, modified_at)
VALUES ('마우스2', '전자기기', 35000, 40, 'FOR_SALE', false, 1, NOW(), NOW());

INSERT INTO products (name, category, price, quantity, status, deleted, admin_id, created_at, modified_at)
VALUES ('마우스3', '전자기기', 28000, 60, 'FOR_SALE', false, 1, NOW(), NOW());

INSERT INTO products (name, category, price, quantity, status, deleted, admin_id, created_at, modified_at)
VALUES ('키보드1', '전자기기', 50000, 30, 'FOR_SALE', false, 1, NOW(), NOW());

INSERT INTO products (name, category, price, quantity, status, deleted, admin_id, created_at, modified_at)
VALUES ('키보드2', '전자기기', 70000, 20, 'FOR_SALE', false, 1, NOW(), NOW());

INSERT INTO products (name, category, price, quantity, status, deleted, admin_id, created_at, modified_at)
VALUES ('키보드3', '전자기기', 60000, 25, 'FOR_SALE', false, 1, NOW(), NOW());

INSERT INTO products (name, category, price, quantity, status, deleted, admin_id, created_at, modified_at)
VALUES ('모니터1', '전자기기', 200000, 15, 'FOR_SALE', false, 1, NOW(), NOW());

INSERT INTO products (name, category, price, quantity, status, deleted, admin_id, created_at, modified_at)
VALUES ('모니터2', '전자기기', 250000, 10, 'FOR_SALE', false, 1, NOW(), NOW());

INSERT INTO products (name, category, price, quantity, status, deleted, admin_id, created_at, modified_at)
VALUES ('모니터3', '전자기기', 220000, 18, 'FOR_SALE', false, 1, NOW(), NOW());

INSERT INTO products (name, category, price, quantity, status, deleted, admin_id, created_at, modified_at)
VALUES ('스피커1', '전자기기', 80000, 35, 'FOR_SALE', false, 1, NOW(), NOW());

INSERT INTO products (name, category, price, quantity, status, deleted, admin_id, created_at, modified_at)
VALUES ('스피커2', '전자기기', 90000, 28, 'FOR_SALE', false, 1, NOW(), NOW());

INSERT INTO products (name, category, price, quantity, status, deleted, admin_id, created_at, modified_at)
VALUES ('스피커3', '전자기기', 85000, 32, 'FOR_SALE', false, 1, NOW(), NOW());

INSERT INTO products (name, category, price, quantity, status, deleted, admin_id, created_at, modified_at)
VALUES ('의자1', '가구', 120000, 20, 'FOR_SALE', false, 1, NOW(), NOW());

INSERT INTO products (name, category, price, quantity, status, deleted, admin_id, created_at, modified_at)
VALUES ('의자2', '가구', 150000, 15, 'FOR_SALE', false, 1, NOW(), NOW());

INSERT INTO products (name, category, price, quantity, status, deleted, admin_id, created_at, modified_at)
VALUES ('의자3', '가구', 130000, 18, 'FOR_SALE', false, 1, NOW(), NOW());

INSERT INTO products (name, category, price, quantity, status, deleted, admin_id, created_at, modified_at)
VALUES ('책상1', '가구', 200000, 12, 'FOR_SALE', false, 1, NOW(), NOW());

INSERT INTO products (name, category, price, quantity, status, deleted, admin_id, created_at, modified_at)
VALUES ('책상2', '가구', 250000, 10, 'FOR_SALE', false, 1, NOW(), NOW());

INSERT INTO products (name, category, price, quantity, status, deleted, admin_id, created_at, modified_at)
VALUES ('책상3', '가구', 220000, 14, 'FOR_SALE', false, 1, NOW(), NOW());

INSERT INTO products (name, category, price, quantity, status, deleted, admin_id, created_at, modified_at)
VALUES ('노트1', '문구', 3000, 100, 'FOR_SALE', false, 1, NOW(), NOW());

INSERT INTO products (name, category, price, quantity, status, deleted, admin_id, created_at, modified_at)
VALUES ('노트2', '문구', 3500, 90, 'FOR_SALE', false, 1, NOW(), NOW());

INSERT INTO products (name, category, price, quantity, status, deleted, admin_id, created_at, modified_at)
VALUES ('노트3', '문구', 4000, 80, 'FOR_SALE', false, 1, NOW(), NOW());

INSERT INTO products (name, category, price, quantity, status, deleted, admin_id, created_at, modified_at)
VALUES ('펜1', '문구', 1000, 200, 'FOR_SALE', false, 1, NOW(), NOW());

INSERT INTO products (name, category, price, quantity, status, deleted, admin_id, created_at, modified_at)
VALUES ('펜2', '문구', 1500, 180, 'FOR_SALE', false, 1, NOW(), NOW());

INSERT INTO products (name, category, price, quantity, status, deleted, admin_id, created_at, modified_at)
VALUES ('펜3', '문구', 1200, 190, 'FOR_SALE', false, 1, NOW(), NOW());

INSERT INTO products (name, category, price, quantity, status, deleted, admin_id, created_at, modified_at)
VALUES ('텀블러1', '생활용품', 20000, 40, 'FOR_SALE', false, 1, NOW(), NOW());

INSERT INTO products (name, category, price, quantity, status, deleted, admin_id, created_at, modified_at)
VALUES ('텀블러2', '생활용품', 25000, 35, 'FOR_SALE', false, 1, NOW(), NOW());

INSERT INTO products (name, category, price, quantity, status, deleted, admin_id, created_at, modified_at)
VALUES ('텀블러3', '생활용품', 22000, 38, 'FOR_SALE', false, 1, NOW(), NOW());