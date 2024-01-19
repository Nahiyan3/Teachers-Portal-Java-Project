import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Student implements Learner {
    private String name;
    private int registrationNumber;
    private int quiz1Mark;
    private int quiz2Mark;
    private int midMark;
    private int termEndMark;
    private Map<String, String> attendanceRecords;


    public Student(String name, int registrationNumber) {
        this.name = name;
        this.registrationNumber = registrationNumber;
        this.attendanceRecords = new HashMap<>();
    }

    public int getQuiz1Mark() {
        return quiz1Mark;
    }

    public void setQuiz1Mark(int quiz1Mark) {
        this.quiz1Mark = quiz1Mark;
    }

    public int getQuiz2Mark() {
        return quiz2Mark;
    }

    public void setQuiz2Mark(int quiz2Mark) {
        this.quiz2Mark = quiz2Mark;
    }

    public int getMidMark() {
        return midMark;
    }

    public void setMidMark(int midMark) {
        this.midMark = midMark;
    }

    public int getTermEndMark() {
        return termEndMark;
    }

    public void setTermEndMark(int termEndMark) {
        this.termEndMark = termEndMark;
    }

    public void setAttendance(String lectureNumber, String attendance) {
        attendanceRecords.put(lectureNumber, attendance);
    }

    public String getAttendance(String lectureNumber) {
        return attendanceRecords.getOrDefault(lectureNumber, "N/A");
    }

    @Override
    public int getRegistrationNumber() {
        return registrationNumber;
    }

    @Override
    public String getName() {
        return name;
    }

    public int getMarkByIndex(int columnToUpdate) {
        int mark;

        switch (columnToUpdate) {
            case 3: // Quiz1 marks (column 3)
                mark = this.quiz1Mark;
                break;
            case 4: // Quiz2 marks (column 4)
                mark = this.quiz2Mark;
                break;
            case 5: // Mid marks (column 5)
                mark = this.midMark;
                break;
            case 6: // TermEnd marks (column 6)
                mark = this.termEndMark;
                break;
            default:
                mark = 0; // Empty marks array for unknown column
        }

        return mark;
    }



}
