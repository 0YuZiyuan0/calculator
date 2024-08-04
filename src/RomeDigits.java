import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public enum RomeDigits {
    I(1),
    V(5),
    X(10),
    L(50),
    C(100);

    public final Integer value;

    RomeDigits(Integer value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static boolean contains(String value) {
        for (RomeDigits e : values()) {
            if (e.toString().equals(value)) {
                return true;
            }
        }
        return false;
    }

    public static int getValueByCharacter(Character value) {
        return RomeDigits.valueOf(Character.toString(value)).value;
    }

    public static List<RomeDigits> getReverseSortedValues() {
        return Arrays.stream(values())
                .sorted(Comparator.comparing((RomeDigits e) -> e.value).reversed())
                .collect(Collectors.toList());
    }

}