create table if not exists label
(
    label_id    int auto_increment
        primary key,
    label_num   int                                null,
    label_name  varchar(32)                        null,
    category    varchar(32)                        null,
    created_at  datetime default CURRENT_TIMESTAMP not null,
    modified_at datetime default CURRENT_TIMESTAMP not null
);

create table if not exists post
(
    post_id      int auto_increment
        primary key,
    user_id      int                                null,
    title        varchar(256)                        null,
    content_text text                               null,
    content_html text                               null,
    created_at   datetime default CURRENT_TIMESTAMP not null,
    modified_at  datetime default CURRENT_TIMESTAMP not null
);

create table if not exists post_label
(
    post_label_id int auto_increment
        primary key,
    label_id      int null,
    post_id       int null
);

create table if not exists user_data
(
    user_id     int auto_increment
        primary key,
    username    varchar(56)                        not null,
    password    varchar(128)                        not null,
    nickname varchar(256),
    authority varchar(56) ,
    created_at  datetime default CURRENT_TIMESTAMP not null,
    modified_at datetime default CURRENT_TIMESTAMP not null
);

create table if not exists authority
(
    authority_id int auto_increment primary key ,
    user_id int,
    authority_name varchar(32) default 'ROLE_USER'
);