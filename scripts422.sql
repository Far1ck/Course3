create table humans (
    id serial primary key,
    name text not null,
    age integer not null check (age >= 0),
    car_id integer references cars (id)
);

create table cars (
    id integer primary key check (id > 0),
    make text not null,
    model text not null,
    price decimal not null check (price >= 0)
);