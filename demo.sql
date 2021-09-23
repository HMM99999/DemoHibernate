show databases;

drop database hibernate;
create database hibernate;
use hibernate;

show tables;

SELECT concat('DROP TABLE IF EXISTS ', table_name, ';') FROM information_schema.tables WHERE table_schema = 'hibernate';

show tables;

select * from t_user;
select * from t_group;
