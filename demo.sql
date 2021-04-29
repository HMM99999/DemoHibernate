show databases ;

create database hibernate;

drop database hibernate;

use hibernate;


show tables;

SELECT concat('DROP TABLE IF EXISTS ', table_name, ';') FROM information_schema.tables WHERE table_schema = 'hibernate';

DROP TABLE IF EXISTS student;DROP TABLE IF EXISTS stuidcard;

drop table hibernate_sequence;
drop table stuidcard;
drop table student;
drop table husband;
drop table wife;

select * from teacher for update ;


select * from student;
select * from stuidcard;

