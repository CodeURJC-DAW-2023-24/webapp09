
# webapp09: ESTUDOKU 📚

## PHASE 0: Formation of the team and definition of the functionalities of the website

### Members of the development team 💻

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


### Main Aspects of the Web Application 

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


## PHASE 1: Page Layout with HTML and CSS 

### Screenshots:

- **Edit Profile**: in this screen registered users will be able to edit their profile: photo, name, email, city...

<img src="https://github.com/CodeURJC-DAW-2023-24/webapp09/assets/103761543/798132c5-a8b3-40d4-bc6e-1fed237fd248" width="500" height="400">


- **Login**: in this screen the registered users can login

<img src="https://github.com/CodeURJC-DAW-2023-24/webapp09/assets/103761543/2f5f7261-71de-4fc8-9e0c-b89d87622cff" width="500" height="400">


- **Main Page**: on the main screen all users will be able to view information about the site and see the different courses offered.
<img src="https://github.com/CodeURJC-DAW-2023-24/webapp09/assets/103761543/798132c5-a8b3-40d4-bc6e-1fed237fd248" width="500" height="400">


- **Main Page Registered Users**: this is the main screen once a user has registered, he will be able to see the courses he is enrolled in and other similar courses will be recommended.
<img src="https://github.com/CodeURJC-DAW-2023-24/webapp09/assets/103761543/1630d4cb-cfa4-4732-9f0d-6d08f38fd503" width="500" height="400">

- **Profile**: On this screen, registered users will be able to view their profile, their subjects and graphs showing their grades, their evolution...
<img src="https://github.com/CodeURJC-DAW-2023-24/webapp09/assets/103761543/6b6d7577-57cf-450a-a2c2-a97e8f6a173a" width="500" height="400">

- **SignUp**: On this screen, unregistered users can create an account on the page.
<img src="https://github.com/CodeURJC-DAW-2023-24/webapp09/assets/103761543/04728269-39c1-4a92-b2e5-44d3d01020da" width="500" height="400">

- **SubjectInfo**: this screen shows information about a specific course and has a button to register for it.
<img src="https://github.com/CodeURJC-DAW-2023-24/webapp09/assets/103761543/df2aa6b4-cbf8-4e75-aa47-459407097fb3" width="500" height="400">

- **SubjectInfoAdmin**: This screen can only be accessed by administrators, where they can view a specific course and edit it (adding teachers and students).
<img src="https://github.com/CodeURJC-DAW-2023-24/webapp09/blob/main/screenshots/subjectInfo-admin.jpeg?raw=true" width="500" height="400">

- **SubjectInfoStudent**: this screen is for the students of the subject, in it they will be able to see their grades and exams of this subject.
<img src="https://github.com/CodeURJC-DAW-2023-24/webapp09/assets/103761543/5186cfda-c54c-48a7-946e-c74a3b64a854" width="500" height="400">

- **SubjectInfoStudentExams**: this is the exams section of the previous page, it shows the exams of the course and each student will be able to see the exam and upload the answer.
<img src="https://github.com/CodeURJC-DAW-2023-24/webapp09/assets/103761543/3f856ee4-566a-4799-b2a0-4125d31b4d2e" width="500" height="400">

- **SubjectInfoStudentMarks**: the same as the previous page but this one shows the marks of the student in the subject.
<img src="https://github.com/CodeURJC-DAW-2023-24/webapp09/assets/103761543/1a58a51a-9693-4db7-aca5-85310d2fedbe" width="500" height="400">

- **SubjectInfoTeacher**: this screen is for the teachers of the course, in it they can add students, see exams and grades.
<img src="https://github.com/CodeURJC-DAW-2023-24/webapp09/assets/103761543/c949a6ce-2ebf-4515-9e9e-683fadcd2bcf" width="500" height="400">

- **SubjectInfoTeacherExams**: this is the exams section of the previous page, in it the teachers will be able to view, upload and correct the exams of the students.
<img src="https://github.com/CodeURJC-DAW-2023-24/webapp09/assets/103761543/23363083-4aab-4c14-a9f4-59d73d7a1e8f" width="500" height="400">

- **SubjectInfoTeachersMarks**: the same as the previous page but this one shows the marks of the different students of the subject.
<img src="https://github.com/CodeURJC-DAW-2023-24/webapp09/assets/103761543/29535c79-6473-4390-b21d-02627f818c6a" width="500" height="400">

### Navigation diagram
This diagram shows how you can move through the different pages according to the type of user you are.
<img src="https://github.com/CodeURJC-DAW-2023-24/webapp09/blob/main/screenshots/DiagramaDeNavegaci%C3%B3n.png" width="500" height="400">


