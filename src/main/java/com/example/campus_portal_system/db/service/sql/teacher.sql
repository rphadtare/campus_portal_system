drop table if exists teacher;
CREATE TABLE `teacher` (     
`teacher_id` int NOT NULL AUTO_INCREMENT,
`institute_id` int not null,
`department_id` int not null,
`teacher_type_id` int not null CHECK (teacher_type_id in (4,5,6,7)),
`salutations` varchar(100) NOT NULL DEFAULT 'Mr.',   
`first_name` varchar(100) NOT NULL DEFAULT '',
`middle_name` varchar(100) NOT NULL DEFAULT '',
`last_name` varchar(100) NOT NULL DEFAULT '',   
`qualifications` varchar(200) NOT NULL DEFAULT '',   
`email_id` varchar(100) NOT NULL DEFAULT '',   
`contact_no` varchar(12) NOT NULL DEFAULT '',
`is_deleted` int not null DEFAULT 0,   
PRIMARY KEY (`teacher_id`,`institute_id`),
FOREIGN KEY (`institute_id`) REFERENCES institute(institute_id),
FOREIGN KEY (`department_id`) REFERENCES department(id),
FOREIGN KEY (`teacher_type_id`) REFERENCES all_user_types(id)
); 

select * from teacher where is_deleted = 0;

