import java.util.List;
import java.util.Scanner;

public class Authentication {
    private final String TEACHER_INFO_FILE = "teacher_info.txt";

    public Teacher register() {
        Scanner input = new Scanner(System.in);

        System.out.println("Enter teacher information:");
        System.out.print("Name: ");
        String name = input.nextLine();
        System.out.print("Username: ");
        String username = input.nextLine();
        System.out.print("Password: ");
        String password = input.nextLine();

        Teacher newTeacher = new Teacher(name, username, password);

        FileWriterService fileWriterService = new FileWriterService(TEACHER_INFO_FILE);
        boolean exists = fileWriterService.writeTeacherInfo(newTeacher);
        if (exists) {
            return null;
        }
        return newTeacher;
    }

    public Teacher login() {
        Scanner input = new Scanner(System.in);

        int tries = 0;
        while (tries < 3) {
            System.out.print("Enter Username: ");
            String username = input.nextLine().trim();
            System.out.print("Enter Password: ");
            String password = input.nextLine();

            FileReaderService fileReaderService = new FileReaderService(TEACHER_INFO_FILE);
            List<Teacher> teachers = fileReaderService.readTeachersFromFile();

            for (Teacher teacher : teachers) {
                if (teacher.getUsername().equals(username) && teacher.getPassword().equals(password)) {
                    System.out.println("Authentication successful. Welcome, " + teacher.getName() + "!");
                    return teacher;
                }
            }

            tries++;
            if (tries < 3) {
                System.out.println("Invalid credentials. Try again. (" + (3 - tries) + " tries remaining)");
            } else {
                System.out.println("You've exceeded the maximum login attempts.");
            }
        }
        return null;
    }
}
