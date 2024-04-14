drop table if exists ADMIN;
CREATE TABLE `ADMIN` (     
`admin_id` int AUTO_INCREMENT,
`institute_id` int not null,
`admin_type_id` int not null,
`salutations` varchar(100) NOT NULL DEFAULT 'Mr.',   
`first_name` varchar(100) NOT NULL DEFAULT '',
`middle_name` varchar(100) NOT NULL DEFAULT '',
`last_name` varchar(100) NOT NULL DEFAULT '',      
`email_id` varchar(100) NOT NULL DEFAULT '',   
`contact_no` varchar(12) NOT NULL DEFAULT '',
`is_deleted` int not null DEFAULT 0 check (is_deleted in (0, 1)),   
PRIMARY KEY (`admin_id`, `admin_type_id`, `institute_id`),
FOREIGN KEY (`institute_id`) REFERENCES institute(institute_id),
FOREIGN KEY (`admin_type_id`) REFERENCES all_user_types(id)
); 

select * from ADMIN where is_deleted = 0;

