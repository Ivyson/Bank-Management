public class PowerCalculator {

    public static double calculatePower(double base, int exponent) {
        // Base cases:
        if (exponent == 0) {
            return 1;
        }
        if (exponent == 1) {
            return base; 
        }
        // Recursive step:
        if (exponent > 0) {
            return base * calculatePower(base, exponent - 1); 
        } else {
            return 1 / calculatePower(base, -exponent); 
        }
    }

    public static void main(String[] args) {
        System.out.println();
        System.out.println(calculatePower(2, 3)); // 2^3 = 8
        System.out.println(calculatePower(5, -2)); // 5^-2 = 0.04
        System.out.println(calculatePower(3, 0)); // 3^0 = 1
        System.out.println(calculatePower(3, 1)); // 3^1 = 3
        // EvenSumRecursive even = new EvenSumRecursive();
        // even.sumEven(0, 0)
    }
}