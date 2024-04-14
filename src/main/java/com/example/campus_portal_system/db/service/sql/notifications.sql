drop table if exists notifications;
CREATE TABLE `notifications` (     
`id` int AUTO_INCREMENT PRIMARY KEY,
`user_name` varchar(8) REFERENCES login(user_name),  
`message` TEXT not null,
`attachments` BLOB,
`end_user_list` JSON
); 

select * from notifications;