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
	semester int check(semester >=1 and semester <= 8),
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

insert into admin values ("916830", "adminpass@1234");
 
insert into student values 
("06714802717", 1, "password", "Shubham", "Nawani", "+91-998765431", "+91-7788903324", "A+"),
("00714802717", 2, "password", "Ajay", "Kumar", "+91-998765431", "+91-7788903324", "A+"),
("12314802717", 3, "password", "Sunmeet", "Oberoi", "+91-998765431", "+91-7788903324", "A+"); 

insert into branch values
(1, "Computer Science and Engineering"),
(2, "Information Technology"),
(3, "Electrical Engineering");

-- subject(subject_id, subject_name, credits)
insert into subject values
-- 1st semester subjects
("ETMA-101", "Applied Maths-I", 4),
("ETPH-103", "Applied Physics-I", 3),
("ETME-105", "Manufacturing Processes", 3),
("ETEE-107", "Electrical Technology", 3),
("ETCS-111", "Fundamentals of Computing", 2),
-- 2nd semester subjects
("ETMA-102", "Applied Maths-II", 4),
("ETPH-104", "Applied Physics-II", 3),
("ETME-110", "Engineering Mechanics", 3),
("ETEC-106", "Electronic Devices", 3),
("ETCS-108", "Introduction to Programming", 3),
-- 3rd semester subjects (CSE)
("ETMA-201", "Applied Maths-III", 4),
("ETCS-203", "Foundation of Computer Science", 4),
("ETEC-205", "Switching Theory and Logic Design", 4),
("ETCS-209", "Data Structure", 4),
("ETCS-211", "Computer Graphics and Multimedia", 4),
-- 4th semester subjects (CSE)
("ETMA-202", "Applied Maths-IV", 4),
("ETCS-204", "Computer Organization and Architecture", 4),
("ETCS-206", "Theory of Computation", 4),
("ETCS-210", "Database Management Systems", 4),
("ETEC-212", "Communication Systems", 4),
-- 5th semester subjects (CSE)
("ETCS-301", "Algorithms Design and Analysis", 4),
("ETCS-303", "Software Engineering", 4),
("ETCS-307", "Java Programming", 4),
("ETEC-311", "Digital Communication", 4),
("ETHS-301", "Communication Skills for Professionals", 1),
-- 6th semester subjects (CSE)
("ETCS-302", "Compiler Design", 4),
("ETCS-304", "Operating Systems", 4),
("ETCS-306", "Computer Networks", 4),
("ETCS-308", "Web Technology", 3),
("ETCS-310", "Artificial Intelligence", 4),
-- 7th semester subjects (CSE)
("ETCS-401", "Information Security", 4),
("ETCS-403", "Software Testing and Quality Assurance", 3),
("ETEC-405", "Wireless Communication ", 3),
("ETCS-413", "Data Mining and Business Intelligence", 3),
("ETCS-423", "Advanced DBMS", 3),
-- 8th semester subjects (CSE)
("ETIT-402", "Mobile Computing", 4),
("ETCS-402", "Machine Learning", 3),
("ETHS-402", "Human Values and Professional Ethics-II", 1),
("ETIT-410", "Soft Computing", 3),
("ETCS-424", "Principles of Programming Languages", 3);


-- marks(marks_id, semester, marks, subject_id)
insert into marks values
(1, 1, 75, "ETCS-111"),
(2, 2, 65, "ETMA-102"),
(3, 3, 81, "ETCS-203"),
(4, 4, 75, "ETCS-204"),
(5, 5, 88, "ETCS-303"),
(6, 6, 81, "ETCS-306"),
(7, 7, 64, "ETCS-413"),
(8, 8, 78, "ETCS-402"),
(9, 1, 85, "ETMA-101"),
(10, 1, 81, "ETPH-103"),
(11, 1, 78, "ETME-105"),
(12, 1, 79, "ETEE-107"),
(15, 1, 79, "ETEE-107");

insert into marks_enrollment values 
-- student1 mapping
(1, "06714802717"),
(2, "06714802717"),
(3, "06714802717"),
(4, "06714802717"),
(5, "06714802717"),
(6, "06714802717"),
(7, "06714802717"),
(8, "06714802717"),
(9, "06714802717"),
(10, "06714802717"),
(11, "06714802717"),
(12, "06714802717"),
(15, "00714802717");

-- reference queries are below to check functionality 

-- select * from subject;
-- select * from branch;
-- select * from marks;
-- select * from student;
-- select * from marks_enrollment;

-- Semester result for a student, result includes the student marks, subject name and credits per subject
-- student_id and semester number goes in the WHERE clause
select me.marks_id, s.subject_name, s.credits, m.marks 
from subject s
	inner join marks m
		on s.subject_id = m.subject_id
	inner join marks_enrollment me
		on m.marks_id = me.marks_id
	inner join student st
		on me.student_id = st.student_id
where st.student_id = "06714802717" and m.semester = 1;

-- Total Marks
-- for total marks you can use the above semester wise result calculation and then call that for all semesters
select semester, avg(marks), CONCAT(sum(marks), "/" , count(marks)*100) "Marks", sum(credits)
from subject s
	inner join marks m
		on s.subject_id = m.subject_id
	inner join marks_enrollment me
		on m.marks_id = me.marks_id
	inner join student st
		on me.student_id = st.student_id
where st.student_id = "06714802717" 
group by semester
order by semester;

-- Select Max Semester
select max(semester)
from marks m
	inner join marks_enrollment me
		on m.marks_id = me.marks_id
	inner join student st
		on me.student_id = st.student_id
where st.student_id = "00714802717" ;

select "All check" from dual;