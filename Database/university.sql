create database if not exists university;
use university;
SET foreign_key_checks = 0;
drop table if exists marks;
drop table if exists marks_enrollment;
drop table if exists branch;
drop table if exists student;
drop table if exists subject;
drop table if exists admin;
SET foreign_key_checks = 1;

create table student
(
	student_id char(11) primary key,
    branch_id int not null check(branch_id > 0),
    password varchar(255) not null,
    first_name varchar(255) not null,
    last_name varchar(255),
    phone_no varchar(20) not null check(length(phone_no) >= 8),
    parent_no varchar(20),
    blood_grp varchar(3)
);

create table admin
(
	admin_id varchar(20) primary key,
    password varchar(255) not null
);

create table branch
(
	branch_id int primary key check(branch_id > 0),
    branch_name varchar(50) not null
);

create table subject
(
	subject_id char(8) primary key,
    subject_name varchar(100) not null,
    credits int check(credits >= 1 and credits <= 8) not null
);

create table marks
(
	marks_id int primary key auto_increment,
	semester int check(semester >=1 and semester <= 4),
	marks int check(marks >= 0 and marks <= 100),
	subject_id char(8) not null,
    foreign key (subject_id) references subject(subject_id)
);

create table marks_enrollment
(
	marks_id int,
    student_id char(11),
    foreign key (marks_id) references marks(marks_id),
    foreign key (student_id) references student(student_id)
);


insert into student values ("06714802717", 1, "password", "Shubham", "Nawani", "+91-998765431", "+91-7788903324", "A+"); 
insert into student values ("00714802717", 2, "password", "Ajay", "Kumar", "+91-998765431", "+91-7788903324", "A+"); 
insert into student values ("12314802717", 3, "password", "John", "Smith", "+91-998765431", "+91-7788903324", "A+"); 

insert into branch values(1, "Computer Science and Engineering");
insert into branch values(2, "Information Technology");
insert into branch values(3, "Electrical Engineering");

insert into subject values("ETCS-101", "Introduction to programming language", 3);
insert into subject values("ETEH-102", "Introduction to history", 3);
insert into subject values("ETEP-103", "Introduction to physics", 3);
insert into subject values("ETEC-104", "Introduction to chemistry", 3);
insert into subject values("ETCS-201", "Data Structures", 4);
insert into subject values("ETCS-202", "Theory of Computation", 3);
insert into subject values("ETCS-301", "Algorithms", 4);
insert into subject values("ETCS-302", "Introduction to Java Programming", 4);

insert into marks values(1, 1, 78, "ETCS-101");
insert into marks values(2, 1, 69, "ETEH-102");
insert into marks values(3, 2, 81, "ETEP-103");
insert into marks values(4, 2, 75, "ETEC-104");
insert into marks values(5, 3, 88, "ETCS-201");
insert into marks values(6, 3, 81, "ETCS-202");
insert into marks values(7, 1, 74, "ETCS-301");

insert into marks_enrollment values (1, "06714802717");
insert into marks_enrollment values (2, "06714802717");
insert into marks_enrollment values (3, "00714802717");
insert into marks_enrollment values (4, "00714802717");
insert into marks_enrollment values (5, "12314802717");
insert into marks_enrollment values (6, "12314802717");


-- select * from subject;
-- select * from branch;
-- select * from marks;
select * from student;
select * from marks_enrollment;

select m.marks_id, s.subject_name, m.semester, m.marks 
from branch b 
	inner join marks m
		on b.branch_id = m.branch_id
	inner join subject s
		on m.subject_id = s.subject_id
where
	semester = 2;

select CONCAT(st.first_name, " ", st.last_name) "Name", m.semester, CONCAT(sum(m.marks)/count(m.marks), "%") "Average"
from branch b 
	inner join marks m
		on b.branch_id = m.branch_id
	inner join subject s
		on m.subject_id = s.subject_id
	inner join marks_enrollment me
		on me.marks_id = m.marks_id
	inner join student st
		on st.student_id = me.student_id
group by semester;

select CONCAT(st.first_name, " ", st.last_name) "Name",  CONCAT(sum(m.marks)/count(m.marks), "%") "Total Aggregate"
from branch b 
	inner join marks m
		on b.branch_id = m.branch_id
	inner join subject s
		on m.subject_id = s.subject_id
	inner join marks_enrollment me
		on me.marks_id = m.marks_id
	inner join student st
		on st.student_id = me.student_id
group by st.student_id;
