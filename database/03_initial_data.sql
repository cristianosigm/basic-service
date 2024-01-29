-- new address
insert into sigm.basicservice.address
(street, address_state, address_country) values 
('Some street', 'BR_SP', 'BR');

-- new person
insert into sigm.basicservice.person
(address_id, first_name, display_name, email) values 
(1, 'Administrator', 'Admin', 'admin@gmail.com');

-- new user
insert into sigm.basicservice.csuser
(person_id, csuser_role, language_code, username, password) values
(1, 'ADMINISTRATOR', 'EN_US', 'admin@gmail.com', '$2a$10$LUqotRmeaXyNilL01axkqug8M.3mmchr9xPCSuLSNJHVQnjEicUr6');
