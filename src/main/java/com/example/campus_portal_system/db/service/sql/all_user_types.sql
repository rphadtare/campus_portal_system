DROP TABLE IF EXISTS all_user_types;
CREATE TABLE `all_user_types` 
(   `id` INT NOT NULL AUTO_INCREMENT,
    `user_type` varchar(36) NOT NULL DEFAULT '',   
        PRIMARY KEY (`id`) 
);

insert into all_user_types
select 1 as id, 'Admin' as user_type
UNION
select 2 as id, 'Head Of Department' as user_type
UNION
select 3 as id, 'Class Teacher' as user_type
UNION
select 4 as id, 'Head Of Department and Class Teacher' as user_type
UNION
select 5 as id, 'Teacher' as user_type
UNION 
select 6 as id, 'Student' as user_type;

--data from all_user_types
select * from all_user_types;
