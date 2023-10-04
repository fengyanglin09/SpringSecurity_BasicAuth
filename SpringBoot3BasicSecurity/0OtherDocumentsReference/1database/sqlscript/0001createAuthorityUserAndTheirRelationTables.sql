create table authorities
(
    id        int auto_increment
        primary key,
    authority varchar(255) null,
    constraint UK_q0u5f2cdlshec8tlh6818bhbk
        unique (authority)
);



create table users
(
    account_non_expired     bit          null,
    account_non_locked      bit          null,
    credentials_non_expired bit          null,
    enabled                 bit          null,
    id                      int auto_increment
        primary key,
    birthdate               datetime(6)  null,
    email_address           varchar(255) null,
    first_name              varchar(255) null,
    last_name               varchar(255) null,
    password                varchar(255) null,
    username                varchar(255) null,
    constraint UK_r43af9ap4edm43mmtq01oddj6
        unique (username)
);





create table users_authorities
(
    authorities_id int not null,
    users_id       int not null,
    primary key (authorities_id, users_id),
    constraint FK2cmfwo8tbjcpmltse0rh5ir0t
        foreign key (users_id) references users (id),
    constraint FKmfxncv8ke1jjgna64c8kclry5
        foreign key (authorities_id) references authorities (id)
);


-- To create the users and authorities the you can use the following SQL


INSERT INTO authorities(authority) VALUES('ROLE_USER');
INSERT INTO authorities(authority) VALUES('ROLE_ADMIN');
INSERT INTO authorities(authority) VALUES('ROLE_DEVELOPER');

INSERT INTO users(username, password, account_non_expired, account_non_locked, credentials_non_expired, enabled, first_name, last_name, email_address, birthdate)
VALUES ('Developer', '111111'
       , true, true, true, true, 'mark', 'lin', 'mark@gmail.com', DATE('1990-01-01'));
INSERT INTO users(username, password, account_non_expired, account_non_locked, credentials_non_expired, enabled, first_name, last_name, email_address, birthdate)
VALUES ('Admin', '111111'
       , true, true, true, true, 'james', 'lin', 'james@gmail.com', DATE('1990-01-01'));
INSERT INTO users(username, password, account_non_expired, account_non_locked, credentials_non_expired, enabled, first_name, last_name, email_address, birthdate)
VALUES ('User', '111111',
        true, true, true, true, 'eric', 'lin', 'eric@gmail.com', DATE('1990-01-01'));

INSERT INTO users_authorities(users_id, authorities_id) VALUES (1, 1);
INSERT INTO users_authorities(users_id, authorities_id) VALUES (1, 2);
INSERT INTO users_authorities(users_id, authorities_id) VALUES (1, 3);
INSERT INTO users_authorities(users_id, authorities_id) VALUES (2, 1);
INSERT INTO users_authorities(users_id, authorities_id) VALUES (2, 2);
INSERT INTO users_authorities(users_id, authorities_id) VALUES (3, 1);
