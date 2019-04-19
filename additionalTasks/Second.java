package skillup.additionalTasks;

public class Second {
    public static void main(String args[]) {
        double a = 1;
        double b = 2;
        double c = 5;
        double sum = a + b + c;
        double mult = a * b * c;
        if (sum > mult) {
            System.out.println("Result of addition is greater then result of multiplication");
        } else if (sum < mult) {
            System.out.println("Result of multiplication is greater then result of addition");
        } else if (sum == mult){
            System.out.println("Result of addition is equal to the result of multiplication");
        }
    }
}
