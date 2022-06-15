drop table if exists usr;
create table usr(
    username varchar(50) not null unique,
    birthdate date not null,
    country varchar(50) not null,
    phone_number varchar(20),
    gender varchar(10),
    primary key(username)
);

insert into usr values ('joe323', '1920-01-01', 'France', '0202050665','Male');
insert into usr values ('emti52', '1992-11-16', 'France', '0846756611', 'Female');
insert into usr values ('zigzip8', '1963-01-01', 'France', '0124059470','Male');
