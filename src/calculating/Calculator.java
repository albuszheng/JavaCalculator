package calculating;

/**
 * Created by keyangzheng on 8/31/16.
 */
public class Calculator {
    public static double operation(double x, double y, char op){
        switch (op) {
            case '+':
                return plus(x,y);

            case '-':
                return minus(x,y);

            case '*':
                return multiply(x,y);

            case '/':
                return division(x,y);

            default:
                return 0;
        }
    }

    static double plus(double x, double y){
        return (x + y);
    }
    static double minus(double x, double y){
        return (x - y);
    }
    static double multiply(double x, double y){
        return (x * y);
    }
    static double division(double x, double y){
        return (x / y);
    }
}
