public enum Operations {
    PLUS("+"),
    MINUS("-"),
    DIVIDE("/"),
    MULTIPLY("*");

    public final String value;

    private Operations(String value) {
        this.value = value;
    }

    public static boolean contains(String label) {
        for (Operations e : values()) {
            if (e.value.equals(label)) {
                return true;
            }
        }
        return false;
    }

    public static Operations valueOfLabel(String label) {
        for (Operations e : values()) {
            if (e.value.equals(label)) {
                return e;
            }
        }
        return null;
    }
}