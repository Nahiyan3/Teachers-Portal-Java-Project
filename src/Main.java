import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Authentication authentication = new Authentication();
        Teacher loggedInTeacher = null;

        Scanner scanner = new Scanner(System.in);
        System.out.println("**TEACHER PORTAL**");
        System.out.println("Enter number to perform corresponding operation");
        System.out.println("1: Login");
        System.out.println("2: Register");
        System.out.println("Select respective choice: ");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1 :
                loggedInTeacher = authentication.login();
                break;
            case 2 :
                loggedInTeacher = authentication.register();
                break;

            default:
                System.out.println("Invalid Operation");
                break;
        }

        if(loggedInTeacher != null) {
            while (loggedInTeacher.getSession()) {
                loggedInTeacher.performTeacherOperations();
            }
        } else {
            System.out.println("Invalid Login/Registration");
        }
    }
}