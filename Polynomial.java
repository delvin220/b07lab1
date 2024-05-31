import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Polynomial {
    double[] coefficients;
    int[] exponents;

    public Polynomial() {
        coefficients = new double[1];
        exponents = new int[1];
    }

    public Polynomial(double[] coefficients, int[] exponents) {
        this.coefficients = coefficients;
        this.exponents = exponents;
    }

    public Polynomial(File file) {
        try {
            Scanner reader = new Scanner(file);
            if (reader.hasNextLine()) {
                String line = reader.nextLine();
                // System.out.println(line);
                parsePolynomial(line);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void parsePolynomial(String line) {
        int count = 0;
        int i = 0;
        int sign = 1;
        while (i < line.length()) {
            char curr = line.charAt(i);
            if (curr == '+') {
                i++;
                sign = 1;
            }
            else if (curr == '-') {
                i++;
                sign = -1;
            }

            int coeff_start = i;
            double curr_coeff;
            if (i < line.length() && Character.isDigit(line.charAt(i))) {
                while (i < line.length() && Character.isDigit(line.charAt(i))) {
                    i++;
                }
                curr_coeff = Double.parseDouble(line.substring(coeff_start, i)) * sign;
            }
            else {
                curr_coeff = sign;
            }

            
            int curr_exp = 0;
            if (i < line.length() && line.charAt(i) == 'x') {
                i++;
                int exp_start = i;
                if (i < line.length() && Character.isDigit(line.charAt(i))) {
                    while (i < line.length() && Character.isDigit(line.charAt(i))) {
                        i++;
                    }
                    curr_exp = Integer.parseInt(line.substring(exp_start, i));
                }
                else {
                    curr_exp = 1;
                }
            }
            exponents = append(count, exponents, curr_exp);
            coefficients = append(count, coefficients, curr_coeff);
            count++;
        }
            
    }

    // append to int array method 
    private int[] append(int len, int arr[], int value) {
        int newarr[] = new int[len + 1];

        for (int i = 0; i < len; i++) {
            newarr[i] = arr[i];
        }

        newarr[len] = value;
        return newarr;
    }

    // append to double array method 
    private double[] append(int len, double arr[], double value) {
        double newarr[] = new double[len + 1];

        for (int i = 0; i < len; i++) {
            newarr[i] = arr[i];
        }

        newarr[len] = value;
        return newarr;
    }

    public Polynomial add(Polynomial p2) {
        int total_len = 0;
        double total_coefficients[] = {};
        int total_exponents[] = {};
        boolean contain;

        for (int i = 0; i < this.exponents.length; i++) {
            contain = false;
            // if current this and p2 have a same exponents 
            for (int j = 0; j < p2.exponents.length; j++) {
                if (this.exponents[i] == p2.exponents[j]) {
                    total_coefficients = append(total_len, total_coefficients, this.coefficients[i] + p2.coefficients[j]);
                    total_exponents = append(total_len, total_exponents, this.exponents[i]);
                    total_len = total_len + 1;
                    contain = true;
                    break;
                }
            }

            // if current this and p2 don't have a same exponents
            if (contain == false) {
                total_coefficients = append(total_len, total_coefficients, this.coefficients[i]);
                total_exponents = append(total_len, total_exponents, this.exponents[i]);
                total_len = total_len + 1;
            }
        }

        for (int i = 0; i < p2.exponents.length; i++) {
            contain = false;
            for (int j = 0; j < total_len; j++) {
                if (total_exponents[j] == p2.exponents[i]) {
                    contain = true;
                    break;
                }
            }
            if (contain == false) {
                total_coefficients = append(total_len, total_coefficients, p2.coefficients[i]);
                total_exponents = append(total_len, total_exponents, p2.exponents[i]);
                total_len = total_len + 1;
            }
        }
        Polynomial newPoly = new Polynomial(total_coefficients, total_exponents);
        return newPoly;
    }

    public double evaluate(double x) {
        double result = 0;

        for (int i = 0; i < this.exponents.length; i++) {
            result += this.coefficients[i] * Math.pow(x, this.exponents[i]);
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

    public Polynomial multiply(Polynomial p2) {
        // find highest exponents of the two polynomial 
        int max_exponent = 0;
        for (int this_exp : this.exponents) {
            for (int p2_exp : p2.exponents) {
                int total_exp = this_exp + p2_exp;
                if (total_exp > max_exponent) {
                    max_exponent = total_exp;
                }
            }
        }

        // create an array of length of highest exponents 
        double total_coefficients[] = new double[max_exponent + 1];

        for (int i = 0; i < this.exponents.length; i++) {
            for (int j = 0; j < p2.exponents.length; j++) {
                int new_exponent = this.exponents[i] + p2.exponents[j];
                double new_coefficient = this.coefficients[i] * p2.coefficients[j];
                total_coefficients[new_exponent] += new_coefficient;
            }
        }

        // count non zero elements 
        int non_zero = 0;
        for (int i = 0; i < total_coefficients.length; i++) {
            if (total_coefficients[i] != 0) {
                non_zero += 1;
            }
        }

        // create final exponent and coefficient array 
        int final_exponent[] = new int[non_zero];
        double final_coefficient[] = new double[non_zero];
        int curr = 0;
        for (int i = 0; i < total_coefficients.length; i++) {
            if (total_coefficients[i] != 0) {
                final_coefficient[curr] = total_coefficients[i];
                final_exponent[curr] = i;
                curr += 1; 
            }
        }

        return new Polynomial(final_coefficient, final_exponent);
    }

    public void saveToFile(String file) {
        try {
            FileWriter myObj = new FileWriter(file);
            for (int i = 0; i < exponents.length; i++) {
                if (exponents[i] == 0) {
                    myObj.write(String.valueOf(coefficients[i]));
                    if (i != exponents.length - 1 && coefficients[i+1] > 0) {
                        myObj.write('+');
                    }
                }
                else if (exponents[i] == 1) {
                    myObj.write(String.valueOf(coefficients[i]) + 'x');
                    if (i != exponents.length - 1 && coefficients[i+1] > 0) {
                        myObj.write('+');
                    }
                }
                else {
                    myObj.write(String.valueOf(coefficients[i]) + 'x' + String.valueOf(exponents[i]));
                    if (i != exponents.length - 1 && coefficients[i+1] > 0) {
                        myObj.write('+');
                    }
                }
            }
            myObj.close();
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        
    }
   
}