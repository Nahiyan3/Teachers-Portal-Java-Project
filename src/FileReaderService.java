import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileReaderService {
    private final String fileName;

    public FileReaderService(String fileName) {
        this.fileName = fileName;
    }

    public List<Teacher> readTeachersFromFile() {
        List<Teacher> teachers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    teachers.add(new Teacher(parts[0], parts[1], parts[2]));
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return teachers;
    }

    public int getLastRegistrationID() {
        int lastRegID = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 2) {
                    lastRegID = Integer.parseInt(data[1].trim());
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println(e.getMessage());
        }

        return lastRegID;
    }

    public boolean isRegIDExists(int newRegID) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 2) {
                    int existingRegID = Integer.parseInt(data[1].trim());
                    if (existingRegID == newRegID) {
                        return true;
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public List<Student> getAllStudents(String adderTeacher) {
        List<Student> students = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 2) {
                    String teacherName = data[0].trim();
                    if (adderTeacher.equals(teacherName)) {
                        int regID = Integer.parseInt(data[1].trim());
                        String name = data[2].trim();
                        students.add(new Student(name, regID));
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println(e.getMessage());
        }

        return students;
    }

    public List<String> readFileLines() {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return lines;
    }

    public Student getStudentByRegNum(int regNum, String adderTeacher) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String teacher = data[0];

                if (adderTeacher.equals(teacher)) {
                    int registrationNumber = Integer.parseInt(data[1].trim());

                    if (registrationNumber == regNum) {
                        String name = data[2];
                        int quiz1Mark = Integer.parseInt(data[3].trim());
                        int quiz2Mark = Integer.parseInt(data[4].trim());
                        int midMark = Integer.parseInt(data[5].trim());
                        int termEndMark = Integer.parseInt(data[6].trim());

                        Student student = new Student(name, registrationNumber);
                        student.setQuiz1Mark(quiz1Mark);
                        student.setQuiz2Mark(quiz2Mark);
                        student.setMidMark(midMark);
                        student.setTermEndMark(termEndMark);

                        return student;
                    }
                }

            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public void printStudentsWithRegId(String adderTeacher) {
        List<Student> students = getAllStudents(adderTeacher);

        for (Student student : students) {
            System.out.println(student.getName() + "(" + student.getRegistrationNumber() + ")");
        }
    }

}

