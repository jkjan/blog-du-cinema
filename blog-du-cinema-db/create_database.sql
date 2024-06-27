create database blog_du_cinema
    with owner root;

create sequence public.user_data_user_data_id_seq
    as integer;

alter sequence public.user_data_user_data_id_seq owner to root;

create table public.user_data
(
    user_id     integer   default nextval('user_data_user_data_id_seq'::regclass) not null
        primary key,
    username    varchar(56)                                                       not null,
    password    varchar(128)                                                      not null,
    created_at  timestamp default CURRENT_TIMESTAMP                               not null,
    modified_at timestamp default CURRENT_TIMESTAMP                               not null
);

alter table public.user_data
    owner to root;

alter sequence public.user_data_user_data_id_seq owned by public.user_data.user_id;

create table public.post
(
    post_id      serial
        primary key,
    user_id      integer                             not null,
    title        varchar(256)                        not null,
    content_text text,
    content_html text,
    created_at   timestamp default CURRENT_TIMESTAMP not null,
    modified_at  timestamp default CURRENT_TIMESTAMP not null
);

alter table public.post
    owner to root;

create table public.label
(
    label_id    serial
        primary key,
    label_num   integer,
    label_name  varchar(32),
    category    varchar(32),
    created_at  timestamp default CURRENT_TIMESTAMP not null,
    modified_at timestamp default CURRENT_TIMESTAMP not null
);

alter table public.label
    owner to root;

create table public.post_label
(
    post_label_id serial
        primary key,
    label_id      integer,
    post_id       integer
);

alter table public.post_label
    owner to root;

create table public.authority
(
    authority_id   serial
        constraint authority_pk
            primary key,
    user_id        integer,
    authority_name varchar(32) default 'ROLE_USER'::character varying not null
);

alter table public.authority
    owner to root;


