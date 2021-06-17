show databases ;

create database hibernate;

drop database hibernate;

use hibernate;

show tables;

SELECT concat('DROP TABLE IF EXISTS ', table_name, ';') FROM information_schema.tables WHERE table_schema = 'hibernate';

show tables;

select * from t_student