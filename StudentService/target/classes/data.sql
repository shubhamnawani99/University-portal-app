insert into student values 
('06714802717', 1, 'Shubham', 'Nawani', '+91-998765431', '+91-7788903324', 'A+'),
('00714802717', 2, 'Ajay', 'Kumar', '+91-998765431', '+91-7788903324', 'A+'),
('12314802717', 3, 'Sunmeet', 'Oberoi', '+91-998765431', '+91-7788903324', 'A+'); 

insert into branch values
(1, 'Computer Science and Engineering'),
(2, 'Information Technology'),
(3, 'Electrical Engineering');

-- subject(subject_id, subject_name, credits)
insert into subject values
-- 1st semester subjects
('ETMA-101', 'Applied Maths-I', 4),
('ETPH-103', 'Applied Physics-I', 3),
('ETME-105', 'Manufacturing Processes', 3),
('ETEE-107', 'Electrical Technology', 3),
('ETCS-111', 'Fundamentals of Computing', 2),
-- 2nd semester subjects
('ETMA-102', 'Applied Maths-II', 4),
('ETPH-104', 'Applied Physics-II', 3),
('ETME-110', 'Engineering Mechanics', 3),
('ETEC-106', 'Electronic Devices', 3),
('ETCS-108', 'Introduction to Programming', 3),
-- 3rd semester subjects (CSE)
('ETMA-201', 'Applied Maths-III', 4),
('ETCS-203', 'Foundation of Computer Science', 4),
('ETEC-205', 'Switching Theory and Logic Design', 4),
('ETCS-209', 'Data Structure', 4),
('ETCS-211', 'Computer Graphics and Multimedia', 4),
-- 4th semester subjects (CSE)
('ETMA-202', 'Applied Maths-IV', 4),
('ETCS-204', 'Computer Organization and Architecture', 4),
('ETCS-206', 'Theory of Computation', 4),
('ETCS-210', 'Database Management Systems', 4),
('ETEC-212', 'Communication Systems', 4),
-- 5th semester subjects (CSE)
('ETCS-301', 'Algorithms Design and Analysis', 4),
('ETCS-303', 'Software Engineering', 4),
('ETCS-307', 'Java Programming', 4),
('ETEC-311', 'Digital Communication', 4),
('ETHS-301', 'Communication Skills for Professionals', 1),
-- 6th semester subjects (CSE)
('ETCS-302', 'Compiler Design', 4),
('ETCS-304', 'Operating Systems', 4),
('ETCS-306', 'Computer Networks', 4),
('ETCS-308', 'Web Technology', 3),
('ETCS-310', 'Artificial Intelligence', 4),
-- 7th semester subjects (CSE)
('ETCS-401', 'Information Security', 4),
('ETCS-403', 'Software Testing and Quality Assurance', 3),
('ETEC-405', 'Wireless Communication ', 3),
('ETCS-413', 'Data Mining and Business Intelligence', 3),
('ETCS-423', 'Advanced DBMS', 3),
-- 8th semester subjects (CSE)
('ETIT-402', 'Mobile Computing', 4),
('ETCS-402', 'Machine Learning', 3),
('ETHS-402', 'Human Values and Professional Ethics-II', 1),
('ETIT-410', 'Soft Computing', 3),
('ETCS-424', 'Principles of Programming Languages', 3);


-- marks(marks_id, student_id, semester, marks, subject_id)
insert into marks values
(1, '06714802717',  1, 75, 'ETCS-111'),
(2, '06714802717', 2, 65, 'ETMA-102'),
(3, '06714802717', 3, 81, 'ETCS-203'),
(4, '06714802717', 4, 75, 'ETCS-204'),
(5, '06714802717', 5, 88, 'ETCS-303'),
(6, '06714802717', 6, 81, 'ETCS-306'),
(7, '06714802717', 7, 64, 'ETCS-413'),
(8, '06714802717', 8, 78, 'ETCS-402'),
(9, '06714802717', 1, 85, 'ETMA-101'),
(10, '06714802717', 1, 81, 'ETPH-103'),
(11, '06714802717', 1, 78, 'ETME-105'),
(12, '06714802717', 1, 79, 'ETEE-107'),
(15, '00714802717', 1, 79, 'ETEE-107');