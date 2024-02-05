## webapp09
# Aplication Name: ESTUDOKU 

## Members of the development team 

- Name: Ismael Albert Aparicio
    - University email: i.albert.2020@alumnos.urjc.es
    - GitHub user: ismael2106

- Name: Amanda Castro López
    - University email: a.castrol.2020@alumnos.urjc.es
    - GitHub user: latinyloco

- Name: Paula Cuenca
    - University email: p.cuenca.2020@alumnos.urjc.es
    - GitHub user: PaulaCuena

- Name: Javier Rodriguez Salas
    - University email: j.rodriguezs.2020@alumnos.urjc.es
    - GitHub user: jrodriguezs2020

- Name: Alejandro Romero Torres
    - University email: a.romeroto.2020@alumnos.urjc.es
    - GitHub user: aleromtor


## Main Aspects of the Web Application 

- Entities: 
    + User (teachers, students and administrators).
    + Exams
    + Subjects
    + Correlation table (from marks, subjects, studentd...)

- User permissions: 
    + Registered user: edit own profile; delete own account; log in.
        + Adminstrator User: detele accounts; assing subjects to teachers; creatre, modify and delete subjects; view marks.
        + Teacher User: view and edit notes; create, modify, correct and delete exams; add and delete students from subjects.
        + Student User: do exams; view notes and subjects; request change to another subject; leaving the subjects.
    + Unregistered user: view subjects information; sign up.

- Asociated Images to profiles and subjects. 

- Use of Line Grafics for average marks.

- Advanced Query Algorithm: subjects recommendation

- Screens:
    + Log in and sing up (all users)
    + Subjects information and mail access (all users)
    + Subjects (registered user)
    + Exams (teachers)
    + Exams (students)
    + Profile (registered user)
    + Edit Profile (registered user)
    + View marks (teachers = subject -> tabla marks-student_name-exam_num)
    + View marks (students = subject -> marks-exam_num)
