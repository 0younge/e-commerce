/* =========================
   ADMINS
========================= */
INSERT INTO admins (name, email, password, phone_number, role, status, deleted, created_at, modified_at)
VALUES
    ('슈퍼관리자','superadmin@test.com','$2a$10$D65zesWQWcOekANz1GW0OeZ/PH3CRK/vhb3tDUrCsDSoEFSfw8z/m','010-9999-9999','SUPER_ADMIN','ACTIVE',false,NOW(),NOW()),
    ('운영관리자','operadmin@test.com','$2a$10$D65zesWQWcOekANz1GW0OeZ/PH3CRK/vhb3tDUrCsDSoEFSfw8z/m','010-8888-8888','OPERATION_ADMIN','ACTIVE',false,NOW(),NOW()),
    ('CS관리자','csadmin@test.com','$2a$10$D65zesWQWcOekANz1GW0OeZ/PH3CRK/vhb3tDUrCsDSoEFSfw8z/m','010-7777-7777','CS_ADMIN','ACTIVE',false,NOW(),NOW());

/* =========================
   USERS (15명)
========================= */
INSERT INTO users (name, email, phone_number, status, deleted, created_at, modified_at)
VALUES
    ('홍길동1','user1@test.com','010-1000-0001','ACTIVE',false,NOW(),NOW()),
    ('홍길동2','user2@test.com','010-1000-0002','ACTIVE',false,NOW(),NOW()),
    ('홍길동3','user3@test.com','010-1000-0003','ACTIVE',false,NOW(),NOW()),
    ('홍길동4','user4@test.com','010-1000-0004','ACTIVE',false,NOW(),NOW()),
    ('홍길동5','user5@test.com','010-1000-0005','ACTIVE',false,NOW(),NOW()),
    ('김철수1','user6@test.com','010-2000-0001','ACTIVE',false,NOW(),NOW()),
    ('김철수2','user7@test.com','010-2000-0002','ACTIVE',false,NOW(),NOW()),
    ('김철수3','user8@test.com','010-2000-0003','ACTIVE',false,NOW(),NOW()),
    ('이영희1','user9@test.com','010-3000-0001','ACTIVE',false,NOW(),NOW()),
    ('이영희2','user10@test.com','010-3000-0002','ACTIVE',false,NOW(),NOW()),
    ('이영희3','user11@test.com','010-3000-0003','ACTIVE',false,NOW(),NOW()),
    ('박민수1','user12@test.com','010-4000-0001','ACTIVE',false,NOW(),NOW()),
    ('박민수2','user13@test.com','010-4000-0002','ACTIVE',false,NOW(),NOW()),
    ('박민수3','user14@test.com','010-4000-0003','ACTIVE',false,NOW(),NOW()),
    ('테스트유저','user15@test.com','010-9999-9999','ACTIVE',false,NOW(),NOW());

/* =========================
   PRODUCTS (30개)
========================= */
INSERT INTO products (name, category, price, quantity, status, deleted, admin_id, created_at, modified_at)
VALUES
    ('노트북1','전자기기',1000000,10,'FOR_SALE',false,1,NOW(),NOW()),
    ('노트북2','전자기기',1200000,8,'FOR_SALE',false,1,NOW(),NOW()),
    ('노트북3','전자기기',900000,12,'FOR_SALE',false,1,NOW(),NOW()),
    ('마우스1','전자기기',30000,50,'FOR_SALE',false,1,NOW(),NOW()),
    ('마우스2','전자기기',35000,40,'FOR_SALE',false,1,NOW(),NOW()),
    ('마우스3','전자기기',28000,60,'FOR_SALE',false,1,NOW(),NOW()),
    ('키보드1','전자기기',50000,30,'FOR_SALE',false,1,NOW(),NOW()),
    ('키보드2','전자기기',70000,20,'FOR_SALE',false,1,NOW(),NOW()),
    ('키보드3','전자기기',60000,25,'FOR_SALE',false,1,NOW(),NOW()),
    ('모니터1','전자기기',200000,15,'FOR_SALE',false,1,NOW(),NOW()),
    ('모니터2','전자기기',250000,10,'FOR_SALE',false,1,NOW(),NOW()),
    ('모니터3','전자기기',220000,18,'FOR_SALE',false,1,NOW(),NOW()),
    ('스피커1','전자기기',80000,35,'FOR_SALE',false,1,NOW(),NOW()),
    ('스피커2','전자기기',90000,28,'FOR_SALE',false,1,NOW(),NOW()),
    ('스피커3','전자기기',85000,32,'FOR_SALE',false,1,NOW(),NOW()),
    ('의자1','가구',120000,20,'FOR_SALE',false,1,NOW(),NOW()),
    ('의자2','가구',150000,15,'FOR_SALE',false,1,NOW(),NOW()),
    ('의자3','가구',130000,18,'FOR_SALE',false,1,NOW(),NOW()),
    ('책상1','가구',200000,12,'FOR_SALE',false,1,NOW(),NOW()),
    ('책상2','가구',250000,10,'FOR_SALE',false,1,NOW(),NOW()),
    ('책상3','가구',220000,14,'FOR_SALE',false,1,NOW(),NOW()),
    ('노트1','문구',3000,100,'FOR_SALE',false,1,NOW(),NOW()),
    ('노트2','문구',3500,90,'FOR_SALE',false,1,NOW(),NOW()),
    ('노트3','문구',4000,80,'FOR_SALE',false,1,NOW(),NOW()),
    ('펜1','문구',1000,200,'FOR_SALE',false,1,NOW(),NOW()),
    ('펜2','문구',1500,180,'FOR_SALE',false,1,NOW(),NOW()),
    ('펜3','문구',1200,0,'FOR_SALE',false,1,NOW(),NOW()),
    ('텀블러1','생활용품',20000,40,'FOR_SALE',false,1,NOW(),NOW()),
    ('텀블러2','생활용품',25000,35,'FOR_SALE',false,1,NOW(),NOW()),
    ('텀블러3','생활용품',22000,38,'FOR_SALE',false,1,NOW(),NOW());

/* =========================
   ORDERS (15개)
========================= */
INSERT INTO orders (product_id, user_id, admin_id, quantity, total_price, status, cancel_reason, number, deleted, created_at, modified_at)
VALUES
    (1,1,1,1,1000000,'DELIVERED',NULL,'ORD-001',false,NOW(),NOW()),
    (2,2,1,2,2400000,'DELIVERED',NULL,'ORD-002',false,NOW(),NOW()),
    (3,3,1,1,900000,'SHIPPING',NULL,'ORD-003',false,NOW(),NOW()),
    (4,4,1,3,90000,'READY',NULL,'ORD-004',false,NOW(),NOW()),
    (5,5,1,1,120000,'DELIVERED',NULL,'ORD-005',false,NOW(),NOW()),
    (6,6,1,2,56000,'CANCELED','단순변심','ORD-006',false,NOW(),NOW()),
    (7,7,1,1,50000,'DELIVERED',NULL,'ORD-007',false,NOW(),NOW()),
    (8,8,1,4,280000,'SHIPPING',NULL,'ORD-008',false,NOW(),NOW()),
    (9,9,1,1,60000,'DELIVERED',NULL,'ORD-009',false,NOW(),NOW()),
    (10,10,1,2,400000,'READY',NULL,'ORD-010',false,NOW(),NOW()),
    (11,11,1,1,250000,'DELIVERED',NULL,'ORD-011',false,NOW(),NOW()),
    (12,12,1,1,220000,'DELIVERED',NULL,'ORD-012',false,NOW(),NOW()),
    (13,13,1,1,80000,'DELIVERED',NULL,'ORD-013',false,NOW(),NOW()),
    (14,14,1,1,90000,'SHIPPING',NULL,'ORD-014',false,NOW(),NOW()),
    (15,15,1,1,85000,'DELIVERED',NULL,'ORD-015',false,NOW(),NOW());

/* =========================
   REVIEWS (DELIVERED만)
========================= */
INSERT INTO reviews (order_id, product_id, user_id, rating, content, deleted, created_at, modified_at)
VALUES
    (1,1,1,5,'좋아요',false,NOW(),NOW()),
    (2,2,2,4,'괜찮음',false,NOW(),NOW()),
    (5,5,5,5,'완전 만족',false,NOW(),NOW()),
    (7,7,7,4,'무난',false,NOW(),NOW()),
    (9,9,9,5,'좋음',false,NOW(),NOW()),
    (11,11,11,5,'굿',false,NOW(),NOW()),
    (12,12,12,4,'만족',false,NOW(),NOW()),
    (13,13,13,5,'추천',false,NOW(),NOW()),
    (15,15,15,4,'좋아요',false,NOW(),NOW());