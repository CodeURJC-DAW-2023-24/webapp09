
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

## PHASE 2: Web with server-generated HTML and AJAX

### Update navigation diagram and templates
- **Forum**: We have created a new template "forum.html" which will show the forum of each subject in which the students and teachers are gonna be able to talk and solve any question.
<img src="https://github.com/CodeURJC-DAW-2023-24/webapp09/blob/main/screenshots/forum.jpeg" width="500" height="400">

So the new navigation diagram will be this one (the same as the other one but at the top we added forum.html): 
<img src="https://github.com/CodeURJC-DAW-2023-24/webapp09/blob/main/screenshots/DiagramaDeNavegaci%C3%B3n2.png" width="500" height="400">

### Navigation instructions
Requirements: JDK 17, SprinBoot 3.0.0, MYSQL database (we used MySql Workbench), in VSCode download extensions "Extension Pack for Java" and "Spring Boot Extension Pack"
1. Download this repository
2. Configure database in MySQLWorkbench, creating a local instance of the database
3. In our navigator web (we recommend Chrome) we go to ""https://localhost:8443" and when it gives us the notice that the connection is not private we must click on "Advanced" and then on "Proceed to localhost (unsafe)"
4. Now everything should work and you can enjoy our application :)

### Diagram with database entities
<img src="https://github.com/CodeURJC-DAW-2023-24/webapp09/blob/main/screenshots/DatabaseEntities.jpg">

### Class diagram
<img src="https://github.com/CodeURJC-DAW-2023-24/webapp09/blob/main/screenshots/Diagrama%20de%20clases%20y%20templates.jpg">

### Main commits of the members
#### Ismael Albert Aparicio  
| Commit 💻      | Description |
|--------------|--------------|
| [1](https://github.com/CodeURJC-DAW-2023-24/webapp09/commit/750003204f05c402f5b9daecdb172c050b5a256b)    | Celda 1,2    | 
| [2](https://github.com/CodeURJC-DAW-2023-24/webapp09/commit/69875d4bfe97268ff2713b72ad4375a1e441f5d0)    | Celda 2,2    | 
| [3](https://github.com/CodeURJC-DAW-2023-24/webapp09/commit/8f5ef8fb5da1a20a71a4793d20660969219fb6c9)    | Celda 2,2    |
| [4](https://github.com/CodeURJC-DAW-2023-24/webapp09/commit/738822c76dd6dd82fa00ac33491e0920e2b020cf)    | Celda 2,2    |
| [5](https://github.com/CodeURJC-DAW-2023-24/webapp09/commit/6940ddde367577731c921b2fe927e09946850afa)    | Celda 2,2    |

| Files 📁       | Description |
|--------------|--------------|
| [1](https://github.com/CodeURJC-DAW-2023-24/webapp09/commit/750003204f05c402f5b9daecdb172c050b5a256b)    | Celda 1,2    | 
| [2](https://github.com/CodeURJC-DAW-2023-24/webapp09/commit/69875d4bfe97268ff2713b72ad4375a1e441f5d0)    | Celda 2,2    | 
| [3](https://github.com/CodeURJC-DAW-2023-24/webapp09/commit/8f5ef8fb5da1a20a71a4793d20660969219fb6c9)    | Celda 2,2    |
| [4](https://github.com/CodeURJC-DAW-2023-24/webapp09/commit/738822c76dd6dd82fa00ac33491e0920e2b020cf)    | Celda 2,2    |
| [5](https://github.com/CodeURJC-DAW-2023-24/webapp09/commit/6940ddde367577731c921b2fe927e09946850afa)    | Celda 2,2    |
 
 
#### Amanda Castro López 
| Commit 💻      | Description |
|--------------|--------------|
| [1](https://github.com/CodeURJC-DAW-2023-24/webapp09/commit/0c2500d04bd78d19d272a2bca44e8b0541a677e8)    | Celda 1,2    | 
| [2](https://github.com/CodeURJC-DAW-2023-24/webapp09/commit/289de07227354314e079c0a830da5acc62b03ee5)    | Celda 2,2    | 
| [3](https://github.com/CodeURJC-DAW-2023-24/webapp09/commit/ce8e31f512859c0486a752ea206fbc00aae91473)    | Celda 2,2    |
| [4](https://github.com/CodeURJC-DAW-2023-24/webapp09/commit/be8142249fb5dc217452ca4c1ddae4a05dd2e692)    | Celda 2,2    |
| [5](https://github.com/CodeURJC-DAW-2023-24/webapp09/commit/4d45cc42261d078b044cb49abc19bf39c0a980f1)    | Celda 2,2    |

| Files 📁       | Description |
|--------------|--------------|
| [1]()    | Celda 1,2    | 
| [2]()    | Celda 2,2    | 
| [3]()    | Celda 2,2    |
| [4]()    | Celda 2,2    |
| [5]()    | Celda 2,2    |

#### Paula Cuenca Gil
| Commit 💻      | Description |
|--------------|--------------|
| [1](https://github.com/CodeURJC-DAW-2023-24/webapp09/commit/06e8ab33b036690f3eebeca70aeeead14c590314)    | We solve problems with signup and login, we started the algorithm and solve somme bbdd problems    | 
| [2](https://github.com/CodeURJC-DAW-2023-24/webapp09/commit/973166e09661bc83121ade0e25ca565b91e790fd)    | Merge from other branch (commits before this one): hide/show buttons if registered or not, show user data in profile, logout and login.    | 
| [3](https://github.com/CodeURJC-DAW-2023-24/webapp09/commit/a22a7a17bd342234ce26c087cf80d61ac31f5d46)    | Solve problems with view subjects_registered_user    |
| [4](https://github.com/CodeURJC-DAW-2023-24/webapp09/commit/2d43d384d105846b79fe38d1af47691bb3136172)    | Graphic + english comments  |
| [5](https://github.com/CodeURJC-DAW-2023-24/webapp09/commit/d415ee369abfa202902f2807fa00e71ff0b7506e)    | I was in charge of the readme:    |

| Files 📁       | Description |
|--------------|--------------|
| [1]()    | Celda 1,2    | 
| [2]()    | Celda 2,2    | 
| [3]()    | Celda 2,2    |
| [4]()    | Celda 2,2    |
| [5]()    | Celda 2,2    |

#### Javier Rodriguez Salas 
| Commit 💻      | Description |
|--------------|--------------|
| [1](https://github.com/CodeURJC-DAW-2023-24/webapp09/commit/d5cbaba004c81bb804d2ea3edaeef0f9a1c4f25b)    | Security module    | 
| [2](https://github.com/CodeURJC-DAW-2023-24/webapp09/commit/15aa85d2f065ba05bd74fdc78a774f981235dfd7)    | Integration of administrator and teacher logic    | 
| [3](https://github.com/CodeURJC-DAW-2023-24/webapp09/commit/a8c0595f92e97de0ca338d773cfb0efe17784793)    | Integration of administrator and teacher logic pt2    |
| [4](https://github.com/CodeURJC-DAW-2023-24/webapp09/commit/10176f33a61629a97bbbb192ef5c08ec05a6149e)    | Migrate to a mysql database    |
| [5](https://github.com/CodeURJC-DAW-2023-24/webapp09/commit/c009373eb28d14a5f6199ac41fece97135bfc904)    | AYAX  |

| Files 📁       | Description |
|--------------|--------------|
| [1]()    | Celda 1,2    | 
| [2]()    | Celda 2,2    | 
| [3]()    | Celda 2,2    |
| [4]()    | Celda 2,2    |
| [5]()    | Celda 2,2    |


#### Alejandro Romero Torres 
| Commit 💻      | Description |
|--------------|--------------|
| [1](https://github.com/CodeURJC-DAW-2023-24/webapp09/commit/5cb54017b6a2ac4bc4cab7248188ad50fca1fc0c)    | Celda 1,2    | 
| [2](https://github.com/CodeURJC-DAW-2023-24/webapp09/commit/79872cec4992325eea9a563d2a1d24d450932d56)    | Celda 2,2    | 
| [3](https://github.com/CodeURJC-DAW-2023-24/webapp09/commit/d2bd2451401d5d7373aba4b493f284c042a34877)    | Celda 2,2    |
| [4](https://github.com/CodeURJC-DAW-2023-24/webapp09/commit/19d0bf5c05de4fea778ccd9a05e320546b0bced4)    | Celda 2,2    |
| [5](https://github.com/CodeURJC-DAW-2023-24/webapp09/commit/eca592d8ec9d98dc4a7caea612ed5cb1235bbcef)    | Celda 2,2    |

| Files 📁       | Description |
|--------------|--------------|
| [1](https://github.com/CodeURJC-DAW-2023-24/webapp09/blob/main/helloworld-vscode/src/main/java/es/codeurjc/helloworldvscode/Controller/SubjectController.java)    | Celda 1,2    | 
| [2](https://github.com/CodeURJC-DAW-2023-24/webapp09/blob/main/helloworld-vscode/src/main/java/es/codeurjc/helloworldvscode/Controller/StudentController.java)    | Celda 2,2    | 
| [3](https://github.com/CodeURJC-DAW-2023-24/webapp09/blob/main/helloworld-vscode/src/main/java/es/codeurjc/helloworldvscode/Controller/UserController.java)    | Celda 2,2    |
| [4](https://github.com/CodeURJC-DAW-2023-24/webapp09/blob/main/helloworld-vscode/src/main/java/es/codeurjc/helloworldvscode/services/SubjectService.java)    | Celda 2,2    |
| [5](https://github.com/CodeURJC-DAW-2023-24/webapp09/blob/main/helloworld-vscode/src/main/resources/templates/subject_onesubj_student.html)    | Celda 2,2    |
 













