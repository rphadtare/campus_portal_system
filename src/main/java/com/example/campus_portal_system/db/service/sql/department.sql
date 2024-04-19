DROP TABLE IF EXISTS department;
CREATE TABLE `department` 
(   `id` int NOT NULL AUTO_INCREMENT,
    `department_name` varchar(36) NOT NULL DEFAULT '',   
        PRIMARY KEY (`id`) 
);

insert into department
select 1 as id, 'Information Technology' as department_name
UNION
select 2 as id, 'Computer Science' as department_name
UNION
select 3 as id, 'Mechanical' as department_name
UNION
select 4 as id, 'Civil' as department_name;

-- data from department
select * from department;