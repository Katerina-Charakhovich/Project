create table testlogin.films
(
    film_id        bigint auto_increment
        primary key,
    film_name_en   varchar(100)                       not null,
    film_avatar_en varchar(100) default 'notPage.jpg' null,
    film_name_ru   varchar(100)                       null,
    active         bit          default b'0'          null,
    film_avatar_ru varchar(100) default 'notPage.jpg' null
);

create table testlogin.films_info
(
    id_films_info    bigint auto_increment
        primary key,
    description_en   varchar(1000) null,
    year_of_creation varchar(4)    not null,
    genre_en         varchar(50)   not null,
    link_en          varchar(50)   not null,
    film_id          bigint        not null,
    description_ru   varchar(1000) null,
    genre_ru         varchar(50)   null,
    link_ru          varchar(100)  null,
    constraint films_info_ibfk_1
        foreign key (film_id) references testlogin.films (film_id)
);

create index film_id
    on testlogin.films_info (film_id);

 create table testlogin.users
(
    id_user   bigint auto_increment,
    email     varchar(45)                       not null,
    password  varchar(150)                      not null,
    role      varchar(15) default 'USER'        not null,
    name_user varchar(50)                       null,
    gender    varchar(10)                       null,
    country   varchar(20)                       null,
    about_me  varchar(300)                      null,
    avatar    varchar(50) default 'unnamed.jpg' null,
    locked    bit         default b'0'          null,
    constraint userId_UNIQUE
        unique (id_user),
    constraint users_email_uindex
        unique (email)
);

alter table testlogin.users
    add primary key (id_user);

   create table testlogin.users_films
(
    id_user bigint not null,
    film_id bigint not null,
    primary key (id_user, film_id),
    constraint users_films_ibfk_1
        foreign key (id_user) references testlogin.users (id_user),
    constraint users_films_ibfk_2
        foreign key (film_id) references testlogin.films (film_id)
);

create index film_id
    on testlogin.users_films (film_id);
