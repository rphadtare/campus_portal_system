drop table if exists login;
CREATE TABLE `login` (  
    id int AUTO_INCREMENT PRIMARY KEY,
    user_type int REFERENCES all_user_types(id),
    user_name varchar(8) not null,
    password VARCHAR(12) not NULL,
    is_deleted int check(is_deleted in (0, 1))
);

select * from login;