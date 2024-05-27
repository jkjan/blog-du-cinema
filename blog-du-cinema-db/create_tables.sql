create table if not exists user_data (
    user_data_id SERIAL primary key,
    username varchar(56) not null,
    password varchar(56) not null,
    created_at timestamp not null default current_timestamp,
    modified_at timestamp not null default current_timestamp
);

create table if not exists post (
    post_id SERIAL primary key,
    user_data_id int not null,
    title varchar(256) not null,
    content_text TEXT,
    content_html TEXT,
    created_at timestamp not null default current_timestamp,
    modified_at timestamp not null default current_timestamp
);

create table if not exists label (
    label_id SERIAL primary key ,
    label_num int,
    label_name varchar(32),
    category varchar(32),
    created_at timestamp not null default current_timestamp,
    modified_at timestamp not null default current_timestamp
);

create table if not exists post_label (
    post_label_id SERIAL primary key ,
    label_id int,
    post_id int
);
