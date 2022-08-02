create table roles (
    id serial primary key,
    name varchar not null unique
);

create table if not exists person (
    id       serial primary key not null,
    login    varchar(2000),
    password varchar(2000),
    person_role integer
);

create table if not exists room (
    id serial primary key not null,
    name varchar not null unique
);

create table person_room (
    person_id int references person(id),
    room_id int references room(id)
);

create table if not exists messages (
    id serial primary key not null,
    name varchar not null unique,
    created timestamp,
    room_id int not null references room(id),
    person_id int not null references person(id)
);

