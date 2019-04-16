package skillup.additionalTasks;

public class Second {
    public static void main(String args[]) {
        int a = 1;
        int b = 2;
        int c = 5;
        int sum = a + b + c;
        int mult = a * b * c;
        if (sum > mult) {
            System.out.println("Result of addition is greater then result of multiplication");
        } else if (sum < mult) {
            System.out.println("Result of multiplication is greater then result of addition");
        } else if (sum == mult){
            System.out.println("Result of addition is equal to the result of multiplication");
        }
    }
}
