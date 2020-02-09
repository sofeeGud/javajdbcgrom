package lesson3.hw1;

public class Demo {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.findProductsByPrice(160,10));
        System.out.println(solution.findProductsByName("#toy"));
        System.out.println(solution.findProductsWithEmptyDescription());
    }
}
