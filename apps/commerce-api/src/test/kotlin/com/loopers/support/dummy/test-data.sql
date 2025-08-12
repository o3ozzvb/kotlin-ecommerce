-- 초기화 필요 시 주석 해제
-- TRUNCATE TABLE product_like;
-- TRUNCATE TABLE product;
-- TRUNCATE TABLE brand;

-- 파라미터
SET @BRANDS := 200;                -- 생성할 브랜드 수
SET @PRODUCTS_PER_BRAND := 5000;    -- 브랜드당 상품 수
SET @LIKES_PER_PRODUCT := 5;       -- 상품당 좋아요 수(균등)
SET @USERS := 50000;               -- user_id 범위
SET @DELETED_RATIO := 0.05;        -- 좋아요 중 DELETED 비율

SET SESSION cte_max_recursion_depth = 2000000;

START TRANSACTION;

-- 1) BRAND 생성 (CTE를 서브쿼리로 감싸서 INSERT)
INSERT INTO brand (name, description, created_at, updated_at, deleted_at)
SELECT
    CONCAT('Brand_', s.n),
    CONCAT('Brand desc ', s.n),
    NOW(6), NOW(6), NULL
FROM (
         WITH RECURSIVE seq(n) AS (
             SELECT 1
             UNION ALL
             SELECT n+1 FROM seq WHERE n < (SELECT @BRANDS)
         )
         SELECT n FROM seq
     ) AS s;

-- 2) INVENTORY 생성 (총 상품 수만큼)
INSERT INTO inventory (total_stock, actual_stock, available_stock, created_at, updated_at, deleted_at)
SELECT
    FLOOR(50 + RAND()*450) AS total_stock,
    FLOOR(30 + RAND()*470) AS actual_stock,
    FLOOR(20 + RAND()*480) AS available_stock,
    NOW(6), NOW(6), NULL
FROM (
         WITH RECURSIVE seq(n) AS (
             SELECT 1
             UNION ALL
             SELECT n+1 FROM seq WHERE n < (@BRANDS * @PRODUCTS_PER_BRAND)
         )
         SELECT n FROM seq
     ) AS s;

-- 3) PRODUCT 생성 (각 브랜드당 균등 개수)
INSERT INTO product (brand_id, name, price, inventory_id, created_at, updated_at, deleted_at)
SELECT
    b.id,
    CONCAT('Product_', b.id, '_', ps.n),
    FLOOR(5000 + RAND()*495000),
    (@row_number := @row_number + 1),
    NOW(6), NOW(6), NULL
FROM brand b
         CROSS JOIN (
    SELECT n
    FROM (
             WITH RECURSIVE seq(n) AS (
                 SELECT 1
                 UNION ALL
                 SELECT n+1 FROM seq WHERE n < (SELECT @PRODUCTS_PER_BRAND)
             )
             SELECT n FROM seq
         ) x
) ps
CROSS JOIN (SELECT @row_number := 0) r;

-- 3) PRODUCT_LIKE 생성 (상품당 @LIKES_PER_PRODUCT개 균등 분포)
INSERT INTO `like` (product_id, member_id, created_at, updated_at, deleted_at)
SELECT
    p.id,
    1 + MOD(p.id * 131071 + ls.n * 524287, (SELECT @USERS)) AS user_id,
    NOW(6),NOW(6),
    CASE WHEN RAND() < (SELECT @DELETED_RATIO) THEN NOW(6) ELSE NULL END AS deleted_at
FROM product p
         CROSS JOIN (
    SELECT n
    FROM (
             WITH RECURSIVE seq(n) AS (
                 SELECT 1
                 UNION ALL
                 SELECT n+1 FROM seq WHERE n < (SELECT @LIKES_PER_PRODUCT)
             )
             SELECT n FROM seq
         ) x
) ls;

COMMIT;

-- 확인
SELECT COUNT(*) AS brands     FROM brand;
SELECT COUNT(*) AS inventory  FROM inventory;
SELECT COUNT(*) AS products   FROM product;
SELECT COUNT(*) AS likes      FROM `like`;