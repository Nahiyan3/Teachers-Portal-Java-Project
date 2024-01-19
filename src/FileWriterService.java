import java.io.*;
import java.util.List;
import java.util.Scanner;

public class FileWriterService {
    private final String fileName;

    public FileWriterService(String fileName) {
        this.fileName = fileName;
    }

    public boolean writeTeacherInfo(Teacher teacher) {
        createFileIfNotExists();
        boolean exists = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 1 && parts[1].equals(teacher.getUsername())) {
                    exists = true;
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        if (!exists) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
                String teacherInfo = teacher.getName() + "," + teacher.getUsername() + "," + teacher.getPassword() + "\n";
                writer.write(teacherInfo);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Teacher with the same username already exists.");
        }

        return exists;
    }


    public void writeStudentInfo(String adderTeacher, Student student) {
        createFileIfNotExists();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(adderTeacher + "," + student.getRegistrationNumber() + "," + student.getName() + ","
                    + student.getQuiz1Mark() + "," + student.getQuiz2Mark() + ","
                    + student.getMidMark() + "," + student.getTermEndMark() + "\n");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }


    public void updateColumnInFile(List<Student> students, int columnToUpdate) {
        FileReaderService fileReaderService = new FileReaderService(fileName);
        List<String> existingData = fileReaderService.readFileLines();
        createFileIfNotExists();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String line : existingData) {
                String[] parts = line.split(",");
                String regNum = parts[1];

                for (Student student : students) {
                    if (student.getRegistrationNumber() == Integer.parseInt(regNum)) {
                        parts[columnToUpdate] = String.valueOf(student.getMarkByIndex(columnToUpdate));
                        break;
                    }
                }
                writer.write(String.join(",", parts) + "\n");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    private void createFileIfNotExists() {
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("File created: " + file.getName());
            }
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }







    public void writeAttendance(List<Student> students, String lectureNumber) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            for (Student student : students) {
                writer.write(student.getRegistrationNumber() + "," + lectureNumber + "," + student.getAttendance(lectureNumber));
                writer.newLine();
            }
            System.out.println("Attendance recorded for lecture of " + lectureNumber);
        } catch (IOException e) {
            System.out.println("Error writing attendance: " + e.getMessage());
        }
    }


}

