public class EvenSumRecursive {
    /**
     * Prints a String and then terminates the line.  This method behaves as
     * though it invokes {@link #print(String)} and then
     * {@link #println()}.
     *
     * @param x  The {@code String} to be printed.
     */
    public static int sumEven(int start,int end) {
        // start is always 0
        if( end == 0 || end == 1 || start == end) 
            return end;
        // if (start > end) {
        //     return 0; 
        // }
        if (start % 2 == 0) { 
            return start + sumEven(start + 1, end);
        } else {
            return sumEven(start + 1, end);
        }
    }

    public static void main(String[] args) {
        int sum = sumEven(0, 100); 
        System.out.println("The sum of even numbers between 10 and 0 is: " + sum);
    }
}