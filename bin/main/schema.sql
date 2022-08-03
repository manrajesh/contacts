DROP TABLE IF EXISTS CONTACTS;

CREATE TABLE CONTACTS
(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    url VARCHAR(500)
)
AS
SELECT ROWNUM(), *
FROM CSVREAD('classpath:people.csv');

