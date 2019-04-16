package skillup.additionalTasks;

public class Third {
    public static void main(String args[]) {
        int sum = 0;
        for (int i = 1; i <= 100; i++) {
            sum += i;
        }

        double mult = 1;
        for (int i = 1; i <= 100; i++) {
            mult *= i;
        }
        System.out.println(sum);
        System.out.println(mult);
    }
}