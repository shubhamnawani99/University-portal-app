create table student
(
	student_id char(11) primary key,
	branch_id int not null,
    first_name varchar(255) not null,
    last_name varchar(255),
    phone_no varchar(20) not null,
    parent_no varchar(20),
    blood_grp varchar(3)
);

create table branch
(
	branch_id int primary key,
    branch_name varchar(50) not null
);

create table subject
(
	subject_id char(8) primary key,
    subject_name varchar(100) not null,
    credits int not null
);

create table marks
(
	marks_id int primary key auto_increment,
	student_id char(11),
	semester int,
	marks int,
	subject_id char(8) not null,
    foreign key (subject_id) references subject(subject_id),
    foreign key (student_id) references student(student_id)
    
);