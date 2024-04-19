DROP TABLE IF EXISTS all_user_types;
CREATE TABLE `all_user_types` 
(   `id` INT NOT NULL AUTO_INCREMENT,
    `user_type` varchar(36) NOT NULL DEFAULT '',   
        PRIMARY KEY (`id`) 
);

delete from all_user_types;

insert into all_user_types(id, user_type)
select 1, 'Admin' union
select 2, 'Institute' union
select 3, 'Institute Admin' union
select 4, 'Head Of Department' union
select 5, 'Class Teacher' union
select 6, 'Head Of Department and Class Teacher' union
select 7, 'Teacher' union
select 8, 'Student';


--data from all_user_types
select * from all_user_types;