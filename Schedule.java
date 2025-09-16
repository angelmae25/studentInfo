public class Schedule {
    private String subject;
    private String day;
    private String time;

    public Schedule(String subject, String day, String time) {
        this.subject = subject;
        this.day = day;
        this.time = time;
    }

    @Override
    public String toString() {
        return subject + " | " + day + " | " + time;
    }
}
