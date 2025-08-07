select student.name, student.age, faculty.name from student
inner join faculty on student.faculty_id = faculty.id;

select student.name, student.age, faculty.name from avatar
inner join student on avatar.student_id = student.id
inner join faculty on student.faculty_id = faculty.id;