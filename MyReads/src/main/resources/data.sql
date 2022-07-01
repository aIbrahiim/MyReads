
UNLOCK TABLES;


CREATE TABLE users (
id bigint(19) unsigned NOT NULL AUTO_INCREMENT,
first_name varchar(255) NOT NULL,
last_name varchar(255) NOT NULL,
username varchar(255) NOT NULL,
email varchar(255) NOT NULL,
password varchar(255) NOT NULL,
joined_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
birthday date NOT NULL,
gender varchar(50) NOT NULL,
bio varchar(255),
website varchar(255),
city varchar(255),
country varchar(255),
is_enabled BIT(1),
PRIMARY KEY(id)
);

CREATE TABLE roles (
 id bigint(19) unsigned NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL,
  PRIMARY KEY (id)
);
CREATE TABLE user_role (
user_id bigInt(19) unsigned NOT NULL,
role_id bigInt(19) unsigned NOT NULL,
PRIMARY KEY(user_id, role_id),
CONSTRAINT fk_security_user_id FOREIGN KEY (user_id) REFERENCES users (id),
CONSTRAINT fk_security_role_id FOREIGN KEY (role_id) REFERENCES roles (id),

);
CREATE TABLE authors (
id bigint(19) unsigned NOT NULL AUTO_INCREMENT,
first_name varchar(255) NOT NULL,
last_name varchar(255) NOT NULL,
born date NOT NULL,
died date NOT NULL,
city varchar(255) NOT NULL,
country varchar(255) NOT NULL,
PRIMARY KEY(id)
);

create table books(
id bigint(19) unsigned NOT NULL AUTO_INCREMENT,
title varchar(255) NOT NULL,
isbn varchar(255) NOT NULL,
num_of_pages int NOT NULL,
genre varchar(255) NOT NULL,
rating double NOT NULL,
cover_uuid varchar(255) NOT NULL,
PRIMARY KEY(id)

);

create table book_author(
author_id bigInt(19) unsigned NOT NULL,
book_id bigInt(19) unsigned NOT NULL,
PRIMARY KEY(author_id, book_id),
CONSTRAINT fk_security_author_id FOREIGN KEY (author_id) REFERENCES authors (id),
CONSTRAINT fk_security_book_id FOREIGN KEY (book_id) REFERENCES books (id),
);

create table shelves(
id bigint(19) unsigned NOT NULL AUTO_INCREMENT,
name varchar(255) NOT NULL,
predefined BIT(1),
user_id bigint(19) unsigned NOT NULL,
PRIMARY KEY(id),
CONSTRAINT fk_security_user_id FOREIGN KEY (user_id) REFERENCES users (id),
);

create table book_shelves(
user_id bigInt(19) unsigned NOT NULL,
shelf_id bigInt(19) unsigned NOT NULL,
book_id bigInt(19) unsigned NOT NULL,
PRIMARY KEY(author_id, book_id),
CONSTRAINT fk_security_user_id FOREIGN KEY (user_id) REFERENCES users (id),
CONSTRAINT fk_security_shelf_id FOREIGN KEY (shelf_id) REFERENCES shelf (id),
CONSTRAINT fk_security_book_id FOREIGN KEY (book_id) REFERENCES books (id),
);

create table reviews (
id bigInt(19) unsigned NOT NULL,
body varchar(255) NOT NULL,
parent_id bigInt(19) unsigned NOT NULL,
rating double,
user_id bigInt(19) unsigned NOT NULL,
book_id bigInt(19) unsigned NOT NULL,
PRIMARY KEY(id),
CONSTRAINT fk_security_user_id FOREIGN KEY (user_id) REFERENCES users (id),
CONSTRAINT fk_security_book_id FOREIGN KEY (book_id) REFERENCES books (id),
);

create table password_verification_code (
id bigInt(19) unsigned NOT NULL,
code varchar(255) NOT NULL,
user_id bigInt(19) unsigned NOT NULL,
PRIMARY KEY(id),
CONSTRAINT fk_security_user_id FOREIGN KEY (user_id) REFERENCES users (id),
);

create table confirmation_token (
token_id bigInt(19) unsigned NOT NULL,
token varchar(255) NOT NULL,
status varchar(255) NOT NULL,
expiredDateTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
issuedDateTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
confirmedDateTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
user_id bigInt(19) unsigned NOT NULL,
PRIMARY KEY(toke_id),
CONSTRAINT fk_security_user_id FOREIGN KEY (user_id) REFERENCES users (id)
);

LOCK TABLES `roles` WRITE;
INSERT INTO roles VALUES (1,'ROLE_ADMIN'),(2,'ROLE_USER'), (3,'ROLE_LIBERARIAN');
UNLOCK TABLES;


