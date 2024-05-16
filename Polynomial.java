

public class Polynomial {
    double[] coefficients;

    public Polynomial() {
        coefficients = new double[1];
    }

    public Polynomial(double[] coefficients) {
        this.coefficients = coefficients;
    }

    public Polynomial add(Polynomial p2) {
        int total_len = Math.max(this.coefficients.length, p2.coefficients.length);
        double[] result = new double[total_len];
        for (int i = 0; i < total_len; i++) {
            if (i < this.coefficients.length) {
                result[i] += this.coefficients[i];
            }

            if (i < p2.coefficients.length) {
                result[i] += p2.coefficients[i];
            }
        }
        Polynomial tmp = new Polynomial(result);
        return tmp;
    }

    public double evaluate(double x) {
        double result = 0;
        for (int i = 0; i < this.coefficients.length; i++) {
            result += this.coefficients[i] * Math.pow(x, i);
        }
        return result;
    }

    public boolean hasRoot(double x) {
        double result = evaluate(x);
        if (result == 0) {
            return true;
        }
        return false;
    }

}