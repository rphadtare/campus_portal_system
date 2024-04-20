drop table if exists class_details_mapping;
CREATE TABLE `class_details_mapping` (     
`id` int AUTO_INCREMENT PRIMARY KEY,
`class_name` varchar(15) CHECK (class_name in ("FIRST YEAR", "SECOND YEAR", "THIRD YEAR", "FINAL YEAR")),
`institute_id` int REFERENCES institute(institute_id),
`department_id` int REFERENCES department(id),
`class_teacher_id` int CHECK (class_teacher_id in (4,5,6,7)) REFERENCES all_user_types(id),
`timetable` blob
); 

select * from class_details_mapping;