import java.io.File;

public class Driver {
    public static void main(String [] args) {
        Polynomial p = new Polynomial();
        // System.out.println(p.evaluate(3));
        double [] c1 = {6,5};
        int [] e1 = {1, 4};
        Polynomial p1 = new Polynomial(c1, e1);
        double [] c2 = {-2,-9, 10};
        int [] e2 = {1, 3, 5};
        Polynomial p2 = new Polynomial(c2, e2);
        Polynomial s = p1.add(p2);
        Polynomial t = p2.add(p1);

        System.out.println("s(0.1) = " + s.evaluate(0.1) + "," + "t(0.1) = " + t.evaluate(0.1));
        System.out.println(java.util.Arrays.toString(s.coefficients));
        System.out.println(java.util.Arrays.toString(s.exponents));
        System.out.println(java.util.Arrays.toString(t.coefficients));
        System.out.println(java.util.Arrays.toString(t.exponents));

        if(s.hasRoot(-1))
            System.out.println("-1 is a root of s");
        else
            System.out.println("-1 is not a root of s");

        File f1 = new File("poly1.txt");
        File f2 = new File("poly2.txt");
        Polynomial p3 = new Polynomial(f1);
        Polynomial p4 = new Polynomial(f2);
        System.out.println(java.util.Arrays.toString(p3.coefficients));
        System.out.println(java.util.Arrays.toString(p3.exponents));
        System.out.println(java.util.Arrays.toString(p4.coefficients));
        System.out.println(java.util.Arrays.toString(p4.exponents));

        Polynomial product = p3.multiply(p4);

        System.out.println(java.util.Arrays.toString(product.coefficients));
        System.out.println(java.util.Arrays.toString(product.exponents));


        product.saveToFile("out.txt");
    }
}