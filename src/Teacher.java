import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Teacher implements User {
    private final String STUDENT_MARKS_FILE = "student_marks.txt";
    private final String STUDENT_ATTENDANCE_FILE = "attendance_info.txt";

    private String name;
    private String username;
    private String password;
    private boolean session;
    private FileWriterService fileWriterService;
    private FileReaderService fileReaderService;
    private FileWriterService attendanceWriterService;
    private FileReaderService attendanceReaderService;

    public Teacher(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.session = true;
        this.fileWriterService = new FileWriterService(STUDENT_MARKS_FILE);
        this.fileReaderService = new FileReaderService(STUDENT_MARKS_FILE);
        this.attendanceWriterService = new FileWriterService(STUDENT_ATTENDANCE_FILE);
        this.attendanceReaderService = new FileReaderService(STUDENT_ATTENDANCE_FILE);
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean getSession() {
        return session;
    }

    public void performTeacherOperations() {
        System.out.println("###################################");
        System.out.println("Choose an option:");
        System.out.println("1. View Students");
        System.out.println("2. Add Student");
        System.out.println("3. Insert Marks");
        System.out.println("4. Insert Attendance");
        System.out.println("5. View Student Marks");
        System.out.println("6. View Student Attendance");
        System.out.println("7. Log Out");
        System.out.println("Enter Choice : ");

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                viewStudents();
                break;
            case 2:
                addStudent(username);
                break;
            case 3:
                insertMarks();
                break;
            case 4:
                insertAttendance();
                break;
            case 5:
                viewStudentMarks();
                break;
            case 6:
                viewStudentAttendance();
                break;
            case 7:
                session = false;
                System.out.println("Logging Out...");
                break;
            default:
                System.out.println("Invalid choice");
        }
    }

    private void viewStudents() {
        List<Student> students = fileReaderService.getAllStudents(username);

        if (students.isEmpty()) {
            System.out.println("No students found!");
        } else {
            System.out.println("List of Students:");
            System.out.println("Registration ID\tName");
            for (Student student : students) {
                System.out.println(student.getRegistrationNumber() + "\t" + student.getName());
            }
        }
    }


    private void addStudent(String adderTeacher) {
        Scanner scanner = new Scanner(System.in);

        int lastRegID = fileReaderService.getLastRegistrationID();
        int newRegID = lastRegID + 1;

        System.out.println("Enter student name:");
        String studentName = scanner.nextLine();

        if (fileReaderService.isRegIDExists(newRegID)) {
            System.out.println("Error: Registration ID already exists!");
        } else {
            Student newStudent = new Student(studentName, newRegID);

            fileWriterService.writeStudentInfo(adderTeacher, newStudent);

            System.out.println("Student added with registration ID: " + newRegID);
        }
    }

    private void insertMarks() {
        Scanner scanner = new Scanner(System.in);
        List<Student> students = fileReaderService.getAllStudents(username);

        if (students.isEmpty()) {
            System.out.println("No students found!");
        } else {
            System.out.println("Choose an exam to enter marks for:");
            System.out.println("1. Quiz 1");
            System.out.println("2. Quiz 2");
            System.out.println("3. Mid");
            System.out.println("4. Term End");
            System.out.println("Enter choice : ");
            int choice = scanner.nextInt();
            int maxMarks;
            Scanner maxMarkScanner = new Scanner(System.in);
            System.out.println("Enter maximum marks : ");

            switch (choice) {
                case 1:
                    maxMarks = maxMarkScanner.nextInt();
                    enterExamMarks(students, "Quiz 1", maxMarks);
                    break;
                case 2:
                    maxMarks = maxMarkScanner.nextInt();
                    enterExamMarks(students, "Quiz 2", maxMarks);
                    break;
                case 3:
                    maxMarks = maxMarkScanner.nextInt();
                    enterExamMarks(students, "Mid", maxMarks);
                    break;
                case 4:
                    maxMarks = maxMarkScanner.nextInt();
                    enterExamMarks(students, "Term End", maxMarks);
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private void enterExamMarks(List<Student> students, String exam, int maxMarks) {
        MarkInitializer markInitializer = new MarkInitializer();

        System.out.println("Enter marks for " + exam);
        int[] examMarks = new int[students.size()];
        examMarks = markInitializer.initializeMarks(students, examMarks, maxMarks);

        for (int i = 0; i < students.size(); i++) {
            switch (exam) {
                case "Quiz 1":
                    students.get(i).setQuiz1Mark(examMarks[i]);
                    fileWriterService.updateColumnInFile(students, 3);
                    break;
                case "Quiz 2":
                    students.get(i).setQuiz2Mark(examMarks[i]);
                    fileWriterService.updateColumnInFile(students, 4);
                    break;
                case "Mid":
                    students.get(i).setMidMark(examMarks[i]);
                    fileWriterService.updateColumnInFile(students, 5);
                    break;
                case "Term End":
                    students.get(i).setTermEndMark(examMarks[i]);
                    fileWriterService.updateColumnInFile(students, 6);
                    break;
                default:
                    break;
            }
        }

    }

    private void viewStudentMarks() {
        fileReaderService.printStudentsWithRegId(username);
        System.out.println("Enter registration number of the student:");
        Scanner scanner = new Scanner(System.in);
        int regNum = scanner.nextInt();

        Student student = fileReaderService.getStudentByRegNum(regNum, username);
        if (student != null) {
            System.out.println("Student Found");
            System.out.println("Name: " + student.getName());
            System.out.println("Registration Number: " + student.getRegistrationNumber());
            System.out.println("Quiz 1 Mark: " + student.getQuiz1Mark());
            System.out.println("Quiz 2 Mark: " + student.getQuiz2Mark());
            System.out.println("Mid Mark: " + student.getMidMark());
            System.out.println("Term End Mark: " + student.getTermEndMark());
        } else {
            System.out.println("Student not found.");
        }
    }


    private void insertAttendance() {
        Scanner scanner = new Scanner(System.in);

        List<Student> students = fileReaderService.getAllStudents(username);

        if (students.isEmpty()) {
            System.out.println("No students found for this teacher!");
        } else {
            System.out.println("Enter lecture date :");
            String lectureNumber = scanner.next();

            System.out.println("Enter attendance for each student (P for present, A for absent):");
            for (Student student : students) {

                System.out.println("ID " + student.getRegistrationNumber() + " attendance:");
                String attendance = scanner.next();
                student.setAttendance(lectureNumber, attendance);

            }

            attendanceWriterService.writeAttendance(students, lectureNumber);
        }
    }


    public void viewStudentAttendance() {
        Scanner scanner = new Scanner(System.in);
        fileReaderService.printStudentsWithRegId(username);

        System.out.println("Enter registration number of the student:");
        int regNum = scanner.nextInt();

        Student student = fileReaderService.getStudentByRegNum(regNum, username);
        if (student != null) {
            System.out.println("Student Found");
            System.out.println("Name: " + student.getName());
            System.out.println("Registration Number: " + student.getRegistrationNumber());

            List<String> lectureNumbers = new ArrayList<>();
            List<String> attendanceList = new ArrayList<>();

            try (BufferedReader br = new BufferedReader(new FileReader("attendance_info.txt"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(",");
                    if (data.length >= 3 && Integer.parseInt(data[0].trim()) == regNum) {
                        lectureNumbers.add(data[1].trim());
                        attendanceList.add(data[2].trim());
                    }
                }
            } catch (IOException | NumberFormatException e) {
                System.out.println("Error reading attendance file: " + e.getMessage());
            }

            if (!lectureNumbers.isEmpty() && !attendanceList.isEmpty()) {
                System.out.println("Attendance for student with registration number " + regNum + ":");
                for (int i = 0; i < lectureNumbers.size(); i++) {
                    System.out.println("Lecture Date: " + lectureNumbers.get(i) + ", Attendance: " + attendanceList.get(i));
                }
            } else {
                System.out.println("No attendance data available for this student.");
            }
        } else {
            System.out.println("Student not found.");
        }

    }

}