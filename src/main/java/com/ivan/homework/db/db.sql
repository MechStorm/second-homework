create table if not exists department (
    id int auto_increment primary key,
    name varchar(50) not null,
    phone_number int,
    email varchar(50) not null,
    years_works int
);

create table if not exists employees (
    id int auto_increment primary key,
    name varchar(30) not null,
    surname varchar(50) not null,
    salary int not null ,
    work_experience int,
    department_id int not null,
    foreign key (department_id) references department(id)
);

create table if not exists hobbies (
    id int auto_increment primary key,
    name varchar(30) not null
);

create table if not exists employees_hobbies (
    employee_id int not null,
    hobby_id int not null,
    primary key(employee_id, hobby_id),
    constraint fk_employeeid foreign key (employee_id) references employees (id),
    constraint fk_hobbyid foreign key (hobby_id) references hobbies (id)
);