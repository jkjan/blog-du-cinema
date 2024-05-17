create database if not exists blog_du_cinema;

use blog_du_cinema;

create table if not exists user (
    user_id int auto_increment primary key,
    username varchar(56) not null,
    password varchar(56) not null,
    created_at datetime not null default current_timestamp,
    modified_at datetime not null default current_timestamp
);

create table if not exists post (
    post_id int auto_increment primary key,
    user_id int not null,
    title varchar(256) not null,
    content_text TEXT,
    content_html TEXT,
    created_at datetime not null default current_timestamp,
    modified_at datetime not null default current_timestamp
);

create table if not exists label (
    label_id int auto_increment primary key ,
    label_num int,
    label_name varchar(32),
    category varchar(32),
    created_at datetime not null default current_timestamp,
    modified_at datetime not null default current_timestamp
);

create table if not exists post_label (
    post_label_id int auto_increment primary key ,
    label_id int,
    post_id int
);
