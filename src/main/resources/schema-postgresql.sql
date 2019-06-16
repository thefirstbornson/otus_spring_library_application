 DROP TABLE IF EXISTS author CASCADE ;
CREATE TABLE author(
   ID  BIGSERIAL PRIMARY KEY,
   FIRST_NAME VARCHAR (80)  NOT NULL,
   LAST_NAME  VARCHAR (80)  NOT NULL
);

DROP TABLE IF EXISTS genre CASCADE;
CREATE TABLE genre(
   ID BIGSERIAL PRIMARY KEY,
   NAME VARCHAR (80)  NOT NULL
);

DROP TABLE IF EXISTS book CASCADE;
CREATE TABLE book(
   ID  BIGSERIAL  PRIMARY KEY,
   NAME VARCHAR (80) NOT NULL,
   AUTHOR_ID  BIGINT REFERENCES  author(ID),
   GENRE_ID BIGINT REFERENCES  genre(ID)
);

DROP TABLE IF EXISTS book_comment;
CREATE TABLE book_comment(
   ID  BIGSERIAL  PRIMARY KEY,
   COMMENT VARCHAR (255) NOT NULL,
   BOOK_ID  BIGINT
      REFERENCES  book(ID)
         ON DELETE CASCADE
);
 DROP TABLE IF EXISTS APP_USER CASCADE;
 CREATE TABLE APP_USER
 (
   USER_ID           BIGSERIAL  NOT NULL PRIMARY KEY,
    USER_NAME         VARCHAR(36)  NOT NULL UNIQUE ,
    ENCRYPTED_PASSWORD VARCHAR(128)  NOT NULL,
    ENABLED           INT  NOT NULL

 ) ;

 DROP TABLE IF EXISTS APP_ROLE CASCADE;
 create table APP_ROLE
 (
   ROLE_ID   BIGSERIAL NOT NULL PRIMARY KEY,
    ROLE_NAME VARCHAR(30) NOT NULL UNIQUE
 ) ;

 DROP TABLE IF EXISTS USER_ROLE;
 create table USER_ROLE
 (
    ID      BIGSERIAL NOT NULL PRIMARY KEY,
    USER_ID BIGINT NOT NULL REFERENCES APP_USER (USER_ID)  ,
    ROLE_ID BIGINT NOT NULL REFERENCES APP_ROLE (ROLE_ID)
 );