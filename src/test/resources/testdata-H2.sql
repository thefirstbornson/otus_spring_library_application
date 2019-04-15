insert into author (ID,FIRST_NAME, LAST_NAME) values (99,'Fedor','Dostoevsky');
insert into author (ID,FIRST_NAME, LAST_NAME) values (88,'Viktor','Pelevin');
insert into author (ID,FIRST_NAME, LAST_NAME) values (77,'Alexander','Filipenko');

insert into genre (ID,NAME) values (999,'Drama');
insert into genre (ID,NAME) values (888,'Sci-fi');
insert into genre (ID,NAME) values (777,'Historical Drama');

insert into book (ID,NAME, AUTHOR_ID, GENRE_ID ) values (9,'Anna Karenina',99,999);
insert into book (ID,NAME, AUTHOR_ID, GENRE_ID ) values (8,'Snuff',88,888);
insert into book (ID,NAME, AUTHOR_ID, GENRE_ID ) values (7,'Red Cross',77,777);