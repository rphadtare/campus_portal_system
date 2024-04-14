drop table if exists institute;
CREATE TABLE `institute` (     
`institute_id` int NOT NULL,   
`name` varchar(100) NOT NULL DEFAULT '',   
`university` varchar(100) NOT NULL DEFAULT '',   
`email_id` varchar(100) NOT NULL DEFAULT '',   
`address` varchar(200) NOT NULL DEFAULT '',   
PRIMARY KEY (`institute_id`) ); 

-- insert statements
insert into institute 
select 1 as institute_id,
"G.S. Moze College of Engineering" as name,
"Pune University" as university,
"abc@moze.com" as email_id,
"Pune, Maharashtra, India, Postal code 411006" as address;

insert into institute values(2, "Modern College of Engineering", "Pune University", "abc@modern_coe.com", 
"Pune, Maharashtra, India, Postal code 411005");


-- select data from instittute
select * from institute;
