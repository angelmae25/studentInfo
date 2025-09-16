public class RegularStudent extends Student {
    public RegularStudent(String name, String course, int yearLevel, String email, String password) {
        super(name, course, yearLevel, email, password);
    }

    @Override
    public String getType() {
        return "Regular";
    }
}
