--create database campus;
use campus;

drop table if exists institute;
create table institute as
select 1 as institute_id,
"G.S. Moze College of Engineering" as name,
"Pune University" as university,
"abc@moze.com" as email_id,
"Pune, Maharashtra, India, Postal code 411006" as address;

drop TABLE if EXISTS teacher_type;
create table teacher_type as
select 1 as id, "Head of Department" as type_of_teacher
UNION
select 2 as id, "Class Teacher" as type_of_teacher
UNION
select 3 as id, "Head of Department and Class Teacher" as type_of_teacher
UNION
select 4 as id, "Teacher" as type_of_teacher;



select * from institute;
select * from teacher_type;

DESCRIBE teacher_type;



