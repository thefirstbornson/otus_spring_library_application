insert into author (ID,FIRST_NAME, LAST_NAME) values (99,'Fedor','Dostoevsky');
insert into author (ID,FIRST_NAME, LAST_NAME) values (88,'Viktor','Pelevin');
insert into author (ID,FIRST_NAME, LAST_NAME) values (77,'Alexander','Filipenko');

insert into genre (ID,NAME) values (999,'Drama');
insert into genre (ID,NAME) values (888,'Sci-fi');
insert into genre (ID,NAME) values (777,'Historical Drama');

insert into book (ID,NAME, AUTHOR_ID, GENRE_ID ) values (9,'Anna Karenina',99,999);
insert into book (ID,NAME, AUTHOR_ID, GENRE_ID ) values (8,'Snuff',88,888);
insert into book (ID,NAME, AUTHOR_ID, GENRE_ID ) values (7,'Red Cross',77,777);

insert into App_User (USER_NAME, ENCRYPTED_PASSWORD, ENABLED)
  values ('admin', '$2a$10$6l5wO2mm4VqJ6Jop8rw9ZufXrD9/K/iWSjXGKQKXCPuhCIB5byesu', 1);
insert into App_User ( USER_NAME, ENCRYPTED_PASSWORD, ENABLED)
  values ('editor', '$2a$10$HtEQXnCWduKMQYjtv3/NyeScR2JfU8ZZStlzpDPRmwbvt2KkSR7BC', 1);

insert into app_role (ROLE_NAME)values ('ROLE_ADMIN');
insert into app_role (ROLE_NAME)values ('ROLE_EDITOR');

insert into user_role (ID, USER_ID, ROLE_ID)values (1, 1, 1);
insert into user_role (ID, USER_ID, ROLE_ID)values (2, 1, 2);
insert into user_role (ID, USER_ID, ROLE_ID)values (3, 2, 2);