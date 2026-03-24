BEGIN;

WITH target_users(email, username, password) AS (
    VALUES
        ('ilgiz@example.com', 'ilgiz', 'ilgiz'),
        ('asya@example.com', 'asya', 'asya123'),
        ('timur@example.com', 'timur', 'timur123'),
        ('elina@example.com', 'elina', 'elina123')
)
INSERT INTO users (email, username, password)
SELECT email, username, password
FROM target_users
ON CONFLICT (email) DO UPDATE
SET username = EXCLUDED.username,
    password = EXCLUDED.password;

WITH base_products(sku, title, description, price, weight, created_at, updated_at) AS (
    VALUES
        ('SKU-IPHONE-15', 'iPhone 15', 'Apple smartphone 128GB', 899.99::numeric, 0.171::numeric, timestamp '2026-03-16 10:00:00', timestamp '2026-03-16 10:00:00'),
        ('SKU-GALAXY-S24', 'Samsung Galaxy S24', 'Samsung flagship smartphone', 849.99::numeric, 0.168::numeric, timestamp '2026-03-16 10:05:00', timestamp '2026-03-16 10:05:00'),
        ('SKU-MACBOOK-AIR-M3', 'MacBook Air M3', 'Apple laptop 13-inch', 1299.00::numeric, 1.240::numeric, timestamp '2026-03-16 10:10:00', timestamp '2026-03-16 10:10:00'),
        ('SKU-DELL-XPS-13', 'Dell XPS 13', 'Compact ultrabook for work', 1199.50::numeric, 1.180::numeric, timestamp '2026-03-16 10:15:00', timestamp '2026-03-16 10:15:00'),
        ('SKU-IPAD-AIR', 'iPad Air', 'Apple tablet 11-inch', 699.90::numeric, 0.461::numeric, timestamp '2026-03-16 10:20:00', timestamp '2026-03-16 10:20:00'),
        ('SKU-AIRPODS-PRO', 'AirPods Pro', 'Wireless earbuds with ANC', 249.99::numeric, 0.056::numeric, timestamp '2026-03-16 10:25:00', timestamp '2026-03-16 10:25:00'),
        ('SKU-APPLE-WATCH-10', 'Apple Watch Series 10', 'Smart watch GPS model', 429.00::numeric, 0.042::numeric, timestamp '2026-03-16 10:30:00', timestamp '2026-03-16 10:30:00'),
        ('SKU-MX-MASTER-3S', 'Logitech MX Master 3S', 'Wireless productivity mouse', 99.95::numeric, 0.141::numeric, timestamp '2026-03-16 10:35:00', timestamp '2026-03-16 10:35:00'),
        ('SKU-KEYCHRON-K8', 'Keychron K8', 'Mechanical wireless keyboard', 89.99::numeric, 0.820::numeric, timestamp '2026-03-16 10:40:00', timestamp '2026-03-16 10:40:00'),
        ('SKU-ANKER-65W', 'Anker 65W Charger', 'Fast USB-C wall charger', 39.90::numeric, 0.120::numeric, timestamp '2026-03-16 10:45:00', timestamp '2026-03-16 10:45:00')
),
generated_products AS (
    SELECT
        'SKU-DEMO-' || lpad(gs::text, 3, '0') AS sku,
        CASE (gs - 11) % 12
            WHEN 0 THEN 'USB-C Cable ' || lpad(gs::text, 3, '0')
            WHEN 1 THEN 'Laptop Stand ' || lpad(gs::text, 3, '0')
            WHEN 2 THEN 'Monitor Arm ' || lpad(gs::text, 3, '0')
            WHEN 3 THEN 'Desk Lamp ' || lpad(gs::text, 3, '0')
            WHEN 4 THEN 'SSD Drive ' || lpad(gs::text, 3, '0')
            WHEN 5 THEN 'Power Bank ' || lpad(gs::text, 3, '0')
            WHEN 6 THEN 'Webcam ' || lpad(gs::text, 3, '0')
            WHEN 7 THEN 'Router ' || lpad(gs::text, 3, '0')
            WHEN 8 THEN 'Smart Speaker ' || lpad(gs::text, 3, '0')
            WHEN 9 THEN 'Dock Station ' || lpad(gs::text, 3, '0')
            WHEN 10 THEN 'Portable SSD ' || lpad(gs::text, 3, '0')
            ELSE 'Bluetooth Mouse ' || lpad(gs::text, 3, '0')
        END AS title,
        'Demo catalog product ' || lpad(gs::text, 3, '0') || ' for join practice' AS description,
        round((((gs * 17) % 280) + 19)::numeric + (((gs * 7) % 100)::numeric / 100), 2) AS price,
        round((0.05 + ((gs * 37) % 180)::numeric / 100), 3) AS weight,
        timestamp '2026-03-17 09:00:00' + (gs - 11) * interval '7 minutes' AS created_at,
        timestamp '2026-03-18 09:00:00' + (gs - 11) * interval '7 minutes' AS updated_at
    FROM generate_series(11, 100) AS gs
),
all_products AS (
    SELECT * FROM base_products
    UNION ALL
    SELECT * FROM generated_products
)
INSERT INTO products (sku, title, description, price, weight, created_at, updated_at)
SELECT sku, title, description, price, weight, created_at, updated_at
FROM all_products
ON CONFLICT (sku) DO UPDATE
SET title = EXCLUDED.title,
    description = EXCLUDED.description,
    price = EXCLUDED.price,
    weight = EXCLUDED.weight,
    created_at = EXCLUDED.created_at,
    updated_at = EXCLUDED.updated_at;

WITH target_orders(order_code, username, order_status, created_at, updated_at) AS (
    VALUES
        (1, 'ilgiz', 1, timestamp '2026-03-16 08:16:01.280303', timestamp '2026-03-16 08:16:01.280303'),
        (2, 'ilgiz', 1, timestamp '2026-03-16 11:00:00', timestamp '2026-03-16 11:00:00'),
        (3, 'ilgiz', 2, timestamp '2026-03-16 11:10:00', timestamp '2026-03-16 11:10:00'),
        (4, 'ilgiz', 3, timestamp '2026-03-16 11:20:00', timestamp '2026-03-16 11:20:00'),
        (5, 'ilgiz', 4, timestamp '2026-03-16 11:30:00', timestamp '2026-03-16 11:30:00'),
        (6, 'ilgiz', 5, timestamp '2026-03-16 11:40:00', timestamp '2026-03-16 11:40:00'),
        (7, 'ilgiz', 1, timestamp '2026-03-20 16:51:48.702472', timestamp '2026-03-20 16:51:48.702472'),
        (8, 'asya', 2, timestamp '2026-03-21 10:15:00', timestamp '2026-03-21 10:15:00'),
        (9, 'timur', 6, timestamp '2026-03-21 11:05:00', timestamp '2026-03-21 11:05:00'),
        (10, 'elina', 7, timestamp '2026-03-21 12:20:00', timestamp '2026-03-21 12:20:00'),
        (11, 'asya', 1, timestamp '2026-03-22 09:00:00', timestamp '2026-03-22 09:00:00'),
        (12, 'asya', 3, timestamp '2026-03-22 09:20:00', timestamp '2026-03-22 09:20:00'),
        (13, 'asya', 5, timestamp '2026-03-22 09:40:00', timestamp '2026-03-22 09:40:00'),
        (14, 'timur', 1, timestamp '2026-03-22 10:00:00', timestamp '2026-03-22 10:00:00'),
        (15, 'timur', 2, timestamp '2026-03-22 10:25:00', timestamp '2026-03-22 10:25:00'),
        (16, 'timur', 4, timestamp '2026-03-22 10:50:00', timestamp '2026-03-22 10:50:00'),
        (17, 'elina', 1, timestamp '2026-03-22 11:15:00', timestamp '2026-03-22 11:15:00'),
        (18, 'elina', 6, timestamp '2026-03-22 11:40:00', timestamp '2026-03-22 11:40:00'),
        (19, 'ilgiz', 3, timestamp '2026-03-22 12:05:00', timestamp '2026-03-22 12:05:00'),
        (20, 'ilgiz', 7, timestamp '2026-03-22 12:30:00', timestamp '2026-03-22 12:30:00')
),
inserted_orders AS (
    INSERT INTO orders (order_status, created_at, updated_at, user_id)
    SELECT t.order_status, t.created_at, t.updated_at, u.id
    FROM target_orders t
    JOIN users u ON u.username = t.username
    WHERE NOT EXISTS (
        SELECT 1
        FROM orders o
        WHERE o.user_id = u.id
          AND o.created_at = t.created_at
    )
    RETURNING id
)
UPDATE orders o
SET order_status = t.order_status,
    updated_at = t.updated_at
FROM target_orders t
JOIN users u ON u.username = t.username
WHERE o.user_id = u.id
  AND o.created_at = t.created_at;

WITH target_orders(order_code, username, created_at) AS (
    VALUES
        (1, 'ilgiz', timestamp '2026-03-16 08:16:01.280303'),
        (2, 'ilgiz', timestamp '2026-03-16 11:00:00'),
        (3, 'ilgiz', timestamp '2026-03-16 11:10:00'),
        (4, 'ilgiz', timestamp '2026-03-16 11:20:00'),
        (5, 'ilgiz', timestamp '2026-03-16 11:30:00'),
        (6, 'ilgiz', timestamp '2026-03-16 11:40:00'),
        (7, 'ilgiz', timestamp '2026-03-20 16:51:48.702472'),
        (8, 'asya', timestamp '2026-03-21 10:15:00'),
        (9, 'timur', timestamp '2026-03-21 11:05:00'),
        (10, 'elina', timestamp '2026-03-21 12:20:00')
),
manual_items(order_code, sku, quantity) AS (
    VALUES
        (1, 'SKU-MACBOOK-AIR-M3', 2),
        (1, 'SKU-DEMO-045', 1),
        (1, 'SKU-DEMO-046', 2),
        (1, 'SKU-DEMO-047', 1),
        (1, 'SKU-DEMO-048', 3),
        (1, 'SKU-DEMO-049', 1),
        (1, 'SKU-DEMO-050', 2),
        (1, 'SKU-DEMO-051', 1),
        (1, 'SKU-DEMO-052', 2),
        (1, 'SKU-DEMO-053', 1),

        (2, 'SKU-IPHONE-15', 1),
        (2, 'SKU-AIRPODS-PRO', 2),
        (2, 'SKU-ANKER-65W', 1),
        (2, 'SKU-DEMO-011', 1),
        (2, 'SKU-DEMO-012', 2),
        (2, 'SKU-DEMO-013', 1),
        (2, 'SKU-DEMO-014', 3),
        (2, 'SKU-DEMO-015', 1),
        (2, 'SKU-DEMO-016', 2),
        (2, 'SKU-DEMO-017', 1),

        (3, 'SKU-MACBOOK-AIR-M3', 1),
        (3, 'SKU-MX-MASTER-3S', 1),
        (3, 'SKU-KEYCHRON-K8', 1),
        (3, 'SKU-DEMO-018', 1),
        (3, 'SKU-DEMO-019', 2),
        (3, 'SKU-DEMO-020', 1),
        (3, 'SKU-DEMO-021', 2),
        (3, 'SKU-DEMO-022', 1),
        (3, 'SKU-DEMO-023', 3),
        (3, 'SKU-DEMO-024', 1),

        (4, 'SKU-GALAXY-S24', 1),
        (4, 'SKU-APPLE-WATCH-10', 1),
        (4, 'SKU-ANKER-65W', 2),
        (4, 'SKU-DEMO-025', 1),
        (4, 'SKU-DEMO-026', 2),
        (4, 'SKU-DEMO-027', 1),
        (4, 'SKU-DEMO-028', 2),
        (4, 'SKU-DEMO-029', 1),
        (4, 'SKU-DEMO-030', 3),
        (4, 'SKU-DEMO-031', 1),

        (5, 'SKU-DELL-XPS-13', 1),
        (5, 'SKU-MX-MASTER-3S', 2),
        (5, 'SKU-ANKER-65W', 3),
        (5, 'SKU-DEMO-032', 1),
        (5, 'SKU-DEMO-033', 2),
        (5, 'SKU-DEMO-034', 1),
        (5, 'SKU-DEMO-035', 2),
        (5, 'SKU-DEMO-036', 1),
        (5, 'SKU-DEMO-037', 2),
        (5, 'SKU-DEMO-038', 1),

        (6, 'SKU-IPAD-AIR', 1),
        (6, 'SKU-AIRPODS-PRO', 1),
        (6, 'SKU-APPLE-WATCH-10', 1),
        (6, 'SKU-KEYCHRON-K8', 1),
        (6, 'SKU-DEMO-039', 1),
        (6, 'SKU-DEMO-040', 2),
        (6, 'SKU-DEMO-041', 1),
        (6, 'SKU-DEMO-042', 2),
        (6, 'SKU-DEMO-043', 1),
        (6, 'SKU-DEMO-044', 3),

        (7, 'SKU-DEMO-054', 1),
        (7, 'SKU-DEMO-055', 2),
        (7, 'SKU-DEMO-056', 1),
        (7, 'SKU-DEMO-057', 3),
        (7, 'SKU-DEMO-058', 1),
        (7, 'SKU-DEMO-059', 2),
        (7, 'SKU-DEMO-060', 1),
        (7, 'SKU-DEMO-061', 2),
        (7, 'SKU-DEMO-062', 1),
        (7, 'SKU-DEMO-063', 3),

        (8, 'SKU-DEMO-064', 1),
        (8, 'SKU-DEMO-065', 2),
        (8, 'SKU-DEMO-066', 1),
        (8, 'SKU-DEMO-067', 2),
        (8, 'SKU-DEMO-068', 1),
        (8, 'SKU-DEMO-069', 3),
        (8, 'SKU-DEMO-070', 1),
        (8, 'SKU-DEMO-071', 2),
        (8, 'SKU-DEMO-072', 1),
        (8, 'SKU-DEMO-073', 2),

        (9, 'SKU-DEMO-074', 1),
        (9, 'SKU-DEMO-075', 2),
        (9, 'SKU-DEMO-076', 1),
        (9, 'SKU-DEMO-077', 3),
        (9, 'SKU-DEMO-078', 1),
        (9, 'SKU-DEMO-079', 2),
        (9, 'SKU-DEMO-080', 1),
        (9, 'SKU-DEMO-081', 2),
        (9, 'SKU-DEMO-082', 1),
        (9, 'SKU-DEMO-083', 3),

        (10, 'SKU-DEMO-084', 1),
        (10, 'SKU-DEMO-085', 2),
        (10, 'SKU-DEMO-086', 1),
        (10, 'SKU-DEMO-087', 2),
        (10, 'SKU-DEMO-088', 1),
        (10, 'SKU-DEMO-089', 3),
        (10, 'SKU-DEMO-090', 1),
        (10, 'SKU-DEMO-091', 2),
        (10, 'SKU-DEMO-092', 1),
        (10, 'SKU-DEMO-093', 2)
),
resolved_items AS (
    SELECT
        o.id AS order_id,
        p.id AS product_id,
        m.quantity,
        round(p.price * m.quantity, 2) AS amount
    FROM manual_items m
    JOIN target_orders t ON t.order_code = m.order_code
    JOIN users u ON u.username = t.username
    JOIN orders o ON o.user_id = u.id AND o.created_at = t.created_at
    JOIN products p ON p.sku = m.sku
)
INSERT INTO order_items (order_id, product_id, quantity, amount)
SELECT order_id, product_id, quantity, amount
FROM resolved_items r
WHERE NOT EXISTS (
    SELECT 1
    FROM order_items oi
    WHERE oi.order_id = r.order_id
      AND oi.product_id = r.product_id
);

WITH target_orders(order_code, username, created_at) AS (
    VALUES
        (1, 'ilgiz', timestamp '2026-03-16 08:16:01.280303'),
        (2, 'ilgiz', timestamp '2026-03-16 11:00:00'),
        (3, 'ilgiz', timestamp '2026-03-16 11:10:00'),
        (4, 'ilgiz', timestamp '2026-03-16 11:20:00'),
        (5, 'ilgiz', timestamp '2026-03-16 11:30:00'),
        (6, 'ilgiz', timestamp '2026-03-16 11:40:00'),
        (7, 'ilgiz', timestamp '2026-03-20 16:51:48.702472'),
        (8, 'asya', timestamp '2026-03-21 10:15:00'),
        (9, 'timur', timestamp '2026-03-21 11:05:00'),
        (10, 'elina', timestamp '2026-03-21 12:20:00')
),
manual_items(order_code, sku, quantity) AS (
    VALUES
        (1, 'SKU-MACBOOK-AIR-M3', 2),
        (1, 'SKU-DEMO-045', 1),
        (1, 'SKU-DEMO-046', 2),
        (1, 'SKU-DEMO-047', 1),
        (1, 'SKU-DEMO-048', 3),
        (1, 'SKU-DEMO-049', 1),
        (1, 'SKU-DEMO-050', 2),
        (1, 'SKU-DEMO-051', 1),
        (1, 'SKU-DEMO-052', 2),
        (1, 'SKU-DEMO-053', 1),

        (2, 'SKU-IPHONE-15', 1),
        (2, 'SKU-AIRPODS-PRO', 2),
        (2, 'SKU-ANKER-65W', 1),
        (2, 'SKU-DEMO-011', 1),
        (2, 'SKU-DEMO-012', 2),
        (2, 'SKU-DEMO-013', 1),
        (2, 'SKU-DEMO-014', 3),
        (2, 'SKU-DEMO-015', 1),
        (2, 'SKU-DEMO-016', 2),
        (2, 'SKU-DEMO-017', 1),

        (3, 'SKU-MACBOOK-AIR-M3', 1),
        (3, 'SKU-MX-MASTER-3S', 1),
        (3, 'SKU-KEYCHRON-K8', 1),
        (3, 'SKU-DEMO-018', 1),
        (3, 'SKU-DEMO-019', 2),
        (3, 'SKU-DEMO-020', 1),
        (3, 'SKU-DEMO-021', 2),
        (3, 'SKU-DEMO-022', 1),
        (3, 'SKU-DEMO-023', 3),
        (3, 'SKU-DEMO-024', 1),

        (4, 'SKU-GALAXY-S24', 1),
        (4, 'SKU-APPLE-WATCH-10', 1),
        (4, 'SKU-ANKER-65W', 2),
        (4, 'SKU-DEMO-025', 1),
        (4, 'SKU-DEMO-026', 2),
        (4, 'SKU-DEMO-027', 1),
        (4, 'SKU-DEMO-028', 2),
        (4, 'SKU-DEMO-029', 1),
        (4, 'SKU-DEMO-030', 3),
        (4, 'SKU-DEMO-031', 1),

        (5, 'SKU-DELL-XPS-13', 1),
        (5, 'SKU-MX-MASTER-3S', 2),
        (5, 'SKU-ANKER-65W', 3),
        (5, 'SKU-DEMO-032', 1),
        (5, 'SKU-DEMO-033', 2),
        (5, 'SKU-DEMO-034', 1),
        (5, 'SKU-DEMO-035', 2),
        (5, 'SKU-DEMO-036', 1),
        (5, 'SKU-DEMO-037', 2),
        (5, 'SKU-DEMO-038', 1),

        (6, 'SKU-IPAD-AIR', 1),
        (6, 'SKU-AIRPODS-PRO', 1),
        (6, 'SKU-APPLE-WATCH-10', 1),
        (6, 'SKU-KEYCHRON-K8', 1),
        (6, 'SKU-DEMO-039', 1),
        (6, 'SKU-DEMO-040', 2),
        (6, 'SKU-DEMO-041', 1),
        (6, 'SKU-DEMO-042', 2),
        (6, 'SKU-DEMO-043', 1),
        (6, 'SKU-DEMO-044', 3),

        (7, 'SKU-DEMO-054', 1),
        (7, 'SKU-DEMO-055', 2),
        (7, 'SKU-DEMO-056', 1),
        (7, 'SKU-DEMO-057', 3),
        (7, 'SKU-DEMO-058', 1),
        (7, 'SKU-DEMO-059', 2),
        (7, 'SKU-DEMO-060', 1),
        (7, 'SKU-DEMO-061', 2),
        (7, 'SKU-DEMO-062', 1),
        (7, 'SKU-DEMO-063', 3),

        (8, 'SKU-DEMO-064', 1),
        (8, 'SKU-DEMO-065', 2),
        (8, 'SKU-DEMO-066', 1),
        (8, 'SKU-DEMO-067', 2),
        (8, 'SKU-DEMO-068', 1),
        (8, 'SKU-DEMO-069', 3),
        (8, 'SKU-DEMO-070', 1),
        (8, 'SKU-DEMO-071', 2),
        (8, 'SKU-DEMO-072', 1),
        (8, 'SKU-DEMO-073', 2),

        (9, 'SKU-DEMO-074', 1),
        (9, 'SKU-DEMO-075', 2),
        (9, 'SKU-DEMO-076', 1),
        (9, 'SKU-DEMO-077', 3),
        (9, 'SKU-DEMO-078', 1),
        (9, 'SKU-DEMO-079', 2),
        (9, 'SKU-DEMO-080', 1),
        (9, 'SKU-DEMO-081', 2),
        (9, 'SKU-DEMO-082', 1),
        (9, 'SKU-DEMO-083', 3),

        (10, 'SKU-DEMO-084', 1),
        (10, 'SKU-DEMO-085', 2),
        (10, 'SKU-DEMO-086', 1),
        (10, 'SKU-DEMO-087', 2),
        (10, 'SKU-DEMO-088', 1),
        (10, 'SKU-DEMO-089', 3),
        (10, 'SKU-DEMO-090', 1),
        (10, 'SKU-DEMO-091', 2),
        (10, 'SKU-DEMO-092', 1),
        (10, 'SKU-DEMO-093', 2)
),
resolved_items AS (
    SELECT
        oi.id,
        m.quantity,
        round(p.price * m.quantity, 2) AS amount
    FROM manual_items m
    JOIN target_orders t ON t.order_code = m.order_code
    JOIN users u ON u.username = t.username
    JOIN orders o ON o.user_id = u.id AND o.created_at = t.created_at
    JOIN products p ON p.sku = m.sku
    JOIN order_items oi ON oi.order_id = o.id AND oi.product_id = p.id
)
UPDATE order_items oi
SET quantity = r.quantity,
    amount = r.amount
FROM resolved_items r
WHERE oi.id = r.id;

COMMIT;
