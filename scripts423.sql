SELECT * FROM student GROUP BY id;


SELECT student.name, student.age, student.faculty_id, avatar.student_id
FROM student INNER JOIN avatar ON student.id = avatar.student_id;