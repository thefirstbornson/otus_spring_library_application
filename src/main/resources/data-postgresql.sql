insert into author (FIRST_NAME, LAST_NAME) values ('Leo','Tolstoy');
insert into author (FIRST_NAME, LAST_NAME) values ('Fedor','Dostoevsky');

insert into genre (NAME) values ('historical drama');
insert into genre (NAME) values ('drama');

insert into book (NAME, AUTHOR_ID, GENRE_ID ) values ('War & Peace',1,1);
insert into book (NAME, AUTHOR_ID, GENRE_ID ) values ('Anna Karenina',1,2);
insert into book (NAME, AUTHOR_ID, GENRE_ID ) values ('Sevastopol Sketches',1,1);
insert into book (NAME, AUTHOR_ID, GENRE_ID ) values ('The Death of Ivan Ilyich',1,1);

insert into book (NAME, AUTHOR_ID, GENRE_ID ) values ('Crime and Punishment',2,2);
insert into book (NAME, AUTHOR_ID, GENRE_ID ) values ('Demons',2,2);
insert into book (NAME, AUTHOR_ID, GENRE_ID ) values ('The Idiot',2,2);
insert into book (NAME, AUTHOR_ID, GENRE_ID ) values ('The Brothers Karamazov',2,2);

insert into book_comment (COMMENT, BOOK_ID) values ('Great novel!',1);
insert into book_comment (COMMENT, BOOK_ID) values ('The worst book I ever read',1);
insert into book_comment (COMMENT, BOOK_ID) values ('The movie is better',1);
insert into book_comment (COMMENT, BOOK_ID) values ('Interesting ending',1);

insert into book_comment (COMMENT, BOOK_ID) values ('Great novel!',2);
insert into book_comment (COMMENT, BOOK_ID) values ('The worst book I ever read',2);
insert into book_comment (COMMENT, BOOK_ID) values ('The movie is better',2);
insert into book_comment (COMMENT, BOOK_ID) values ('Interesting ending',2);

insert into book_comment (COMMENT, BOOK_ID) values ('Great novel!',3);
insert into book_comment (COMMENT, BOOK_ID) values ('The worst book I ever read',3);
insert into book_comment (COMMENT, BOOK_ID) values ('The movie is better',3);
insert into book_comment (COMMENT, BOOK_ID) values ('Interesting ending',3);

insert into book_comment (COMMENT, BOOK_ID) values ('Great novel!',4);
insert into book_comment (COMMENT, BOOK_ID) values ('The worst book I ever read',4);
insert into book_comment (COMMENT, BOOK_ID) values ('The movie is better',4);
insert into book_comment (COMMENT, BOOK_ID) values ('Interesting ending',4);

insert into book_comment (COMMENT, BOOK_ID) values ('Great novel!',5);
insert into book_comment (COMMENT, BOOK_ID) values ('The worst book I ever read',5);
insert into book_comment (COMMENT, BOOK_ID) values ('The movie is better',5);
insert into book_comment (COMMENT, BOOK_ID) values ('Interesting ending',5);

insert into book_comment (COMMENT, BOOK_ID) values ('Great novel!',6);
insert into book_comment (COMMENT, BOOK_ID) values ('The worst book I ever read',6);
insert into book_comment (COMMENT, BOOK_ID) values ('The movie is better',6);
insert into book_comment (COMMENT, BOOK_ID) values ('Interesting ending',6);

insert into book_comment (COMMENT, BOOK_ID) values ('Great novel!',7);
insert into book_comment (COMMENT, BOOK_ID) values ('The worst book I ever read',7);
insert into book_comment (COMMENT, BOOK_ID) values ('The movie is better',7);
insert into book_comment (COMMENT, BOOK_ID) values ('Interesting ending',7);

insert into book_comment (COMMENT, BOOK_ID) values ('Great novel!',8);
insert into book_comment (COMMENT, BOOK_ID) values ('The worst book I ever read',8);
insert into book_comment (COMMENT, BOOK_ID) values ('The movie is better',8);
insert into book_comment (COMMENT, BOOK_ID) values ('Interesting ending',8);

insert into App_User (USER_NAME, ENCRYPTED_PASSWORD, ENABLED)
    values ('admin', '$2a$10$6l5wO2mm4VqJ6Jop8rw9ZufXrD9/K/iWSjXGKQKXCPuhCIB5byesu', 1);
insert into App_User ( USER_NAME, ENCRYPTED_PASSWORD, ENABLED)
values ('editor', '$2a$10$HtEQXnCWduKMQYjtv3/NyeScR2JfU8ZZStlzpDPRmwbvt2KkSR7BC', 1);
insert into App_User ( USER_NAME, ENCRYPTED_PASSWORD, ENABLED)
values ('viewer', '$2a$10$hye94yK7KRO5FlpHO14k1uFf/QAK0J8JzP2UNbVrR4t6n.AkiCH36', 1);

insert into app_role (ROLE_NAME)values ('ROLE_ADMIN');
insert into app_role (ROLE_NAME)values ('ROLE_EDITOR');
insert into app_role (ROLE_NAME)values ('ROLE_VIEWER');

insert into user_role (ID, USER_ID, ROLE_ID)values (1, 1, 1);
insert into user_role (ID, USER_ID, ROLE_ID)values (2, 1, 2);
insert into user_role (ID, USER_ID, ROLE_ID)values (3, 1, 3);
insert into user_role (ID, USER_ID, ROLE_ID)values (4, 2, 2);
insert into user_role (ID, USER_ID, ROLE_ID)values (5, 2, 3);
insert into user_role (ID, USER_ID, ROLE_ID)values (6, 3, 3);