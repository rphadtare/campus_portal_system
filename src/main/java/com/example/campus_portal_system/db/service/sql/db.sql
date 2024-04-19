sequence of table creation
1.  all_user_types
2.  department
3.  institute
4.  teacher
5.  class_details_mapping
6.  login
7.  notifications
8.  notification_attachments
9.  admin
10. register_request


--dropping table sequence;
drop table if exists register_request;
drop table if exists admin;
drop table if exists notification_attachments;
drop table if exists notifications;
drop table if exists login;
drop table if exists class_details_mapping;
drop table if exists teacher;
drop table if exists institute;
drop table if exists department;
drop table if exists all_user_types;