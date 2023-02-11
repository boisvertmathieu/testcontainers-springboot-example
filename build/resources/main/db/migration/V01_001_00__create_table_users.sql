create table users
(
    id            uniqueidentifier not null,
    first_name    varchar(50)      not null,
    last_name     varchar(50)      not null,
    email_address varchar(75)      not null,
    created_at    datetime         not null,
    created_by    varchar(50)      not null,
    updated_at    datetime         not null,
    updated_by    varchar(50)      not null,

    constraint id_pk primary key ${nonclustered} (id)
)