-- // data_page_links
-- Migration SQL that makes the change goes here.

INSERT INTO `page_links` (`page_from`, `page_to`)
VALUES (1, 13),
       (13, 1),
       (2, 3),
       (2, 5);


-- //@UNDO
-- SQL to undo the change goes here.


