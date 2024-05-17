create table if not exists label
(
    label_id    int 
        primary key,
    label_num   int                                null,
    label_name  varchar(32)                        null,
    category    varchar(32)                        null,
    created_at  datetime default CURRENT_TIMESTAMP not null,
    modified_at datetime default CURRENT_TIMESTAMP not null
);

create table if not exists post
(
    post_id      int 
        primary key,
    user_id      int                                not null,
    title        varchar(256)                       not null,
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

create table if not exists users
(
    user_id     int 
        primary key,
    username    varchar(56)                        not null,
    password    varchar(56)                        not null,
    created_at  datetime default CURRENT_TIMESTAMP not null,
    modified_at datetime default CURRENT_TIMESTAMP not null
);

