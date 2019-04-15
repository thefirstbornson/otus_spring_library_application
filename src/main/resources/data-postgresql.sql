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