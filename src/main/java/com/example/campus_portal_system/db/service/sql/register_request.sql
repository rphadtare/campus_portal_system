drop table if exists register_request;
CREATE TABLE `register_request` (     
`id` int AUTO_INCREMENT PRIMARY KEY,  
`request_type` varchar(50) NOT NULL CHECK (request_type = 'Student Register' or request_type = 'Teacher Register' or request_type = 'HOD Register'),
`request_information` JSON NOT NULL,   
`approver_type_id` int REFERENCES all_user_types(id),
`approver_id` int REFERENCES teacher(teacher_id),
`status` varchar(100) NOT NULL DEFAULT 'OPEN' CHECK (status = 'OPEN' or status = 'APPROVED' or status = 'DENIED')
); 

select * from register_request;