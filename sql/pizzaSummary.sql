SELECT po.id_order                AS idOrder,
       cu.name                    AS customerName,
       po.date                    AS orderDate,
       po.total                   AS orderTotal,
       GROUP_CONCAT(pi.name)     AS pizzaNames
FROM pizza_order po
         INNER JOIN customer cu ON po.id_customer = cu.id_customer
         INNER JOIN order_item oi ON po.id_order = oi.id_order
         INNER JOIN pizza pi ON oi.id_pizza = pi.id_pizza
WHERE po.id_order = :orderId
GROUP BY po.id_order,
         cu.name,
         po.date,
         po.total