public class IrregularStudent extends Student {
    public IrregularStudent(String name, String course, int yearLevel, String email, String password) {
        super(name, course, yearLevel, email, password);
    }

    @Override
    public String getType() {
        return "Irregular";
    }
}
