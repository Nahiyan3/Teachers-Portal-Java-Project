import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MarkInitializer {
    public int[] initializeMarks(List<Student> students, int[] marks, int maxMarks) {
        Scanner input = new Scanner(System.in);
        int i = 0;

        for (Student student : students) {
            int regNum = student.getRegistrationNumber();

            System.out.print("Enter obtained marks for ID " + regNum + " : ");
            int mark = input.nextInt();

            if (mark >= 0 && mark <= maxMarks) {
                marks[i] = mark;
                i++;
            } else {
                System.out.println("Invalid input! Marks should be between 0 and " + maxMarks);
            }
        }

        return marks;
    }


    public String[] initializeAttendance(List<Student> students, String[] attendance) {
        List<String> validAttendanceOptions = Arrays.asList("p", "a", "P", "A");
        Scanner input = new Scanner(System.in);
        int i = 0;

        for (Student student : students) {
            int regNum = student.getRegistrationNumber();

            System.out.print("Enter attendance status of ID " + regNum +" : ");
            String attend = input.next();

            if (validAttendanceOptions.contains(attend.toLowerCase())) {
                attendance[i] = attend;
                i++;
            } else {
                System.out.println("Invalid Input");
            }
        }

        return attendance;
    }

}
