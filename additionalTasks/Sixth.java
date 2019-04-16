package skillup.additionalTasks;

public class Sixth {
    public static void main(String args[]){
        int sum = 0;
        int[] numbers = {3, 4, 5, 6, 7, 8, 9};
        for (int i = 0; i < numbers.length; i++){
            sum += numbers[i];
        }
        double average = sum/numbers.length;
        System.out.println(average);
    }
}
