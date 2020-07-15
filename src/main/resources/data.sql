--LOCATION
insert into location (id,location) values ('X122413','Gurugram');
insert into location (id,location) values ('X411002','Pune');
insert into location (id,location) values ('X560002','Bangalore');
commit;

--USER
insert into users (id,username,password,active,roles,first_name,last_name,location_id) values('3927985','jhanak.bhargava@xebia.com','password',True,'ROLE_SUPERADMIN','Jhanak','Bhargava','X122413');
insert into users (id,username,password,active,roles,first_name,last_name,location_id) values('1530815','mudassir.shaikh@xebia.com','password',True,'ROLE_ADMIN','Mudassir','Shaikh','X122413');
insert into users (id,username,password,active,roles,first_name,last_name,location_id) values('2969368','raghav.sindhwani@xebia.com','password',True,'ROLE_SUPERADMIN','Raghav','Sindhwani','X560002');
insert into users (id,username,password,active,roles,first_name,last_name,location_id) values('6190459','kamal.kumar@xebia.com','password',True,'ROLE_ADMIN','Kamal','Kumar','X411002');
commit;

--STATUS
INSERT INTO status (id, status) VALUES ('S101', 'Completed');
INSERT INTO status (id, status) VALUES ('S102', 'Upcoming');
INSERT INTO status (id, status) VALUES ('S103', 'Pending');
INSERT INTO status (id, status) VALUES ('S104', 'Rejected');
commit;

--FREQUENCY
INSERT INTO frequency (id, frequency) VALUES ('T001', 'Today');
INSERT INTO frequency (id, frequency) VALUES ('D001', 'Daily');
INSERT INTO frequency (id, frequency) VALUES ('W001', 'Weekly');
INSERT INTO frequency (id, frequency) VALUES ('2001', 'every 2 hour');
commit;



