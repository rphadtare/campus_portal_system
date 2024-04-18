drop table if exists notification_attachments;
CREATE TABLE `notification_attachments` (
`id` int REFERENCES notifications(id),
`attachments` blob
);

select * from notification_attachments;