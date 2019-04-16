package skillup.additionalTasks;

public class Fifth {
    public static void main(String args[]) {
        int sum = 0;
        double mult = 1;
        for (int i = 1; i <= 100; i++) {
            if (i % 2 == 0) {
                sum += i;
            } else {
                mult *=  i;
            }
        }
        System.out.println(sum);
        System.out.println(mult);
    }
}

