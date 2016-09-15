package calculating;

/**
 * Created by keyangzheng on 9/15/16.
 */
public enum Operator {
    PLUS,
    MINUS,
    MULTIPLY,
    DIVISION,
    _Default;

    @Override
    public String toString() {
        switch (this) {
            case PLUS: return "+";
            case MINUS: return "-";
            case MULTIPLY: return "*";
            case DIVISION: return "/";
            case _Default: return "";
            default: return "";
        }
    }
}
