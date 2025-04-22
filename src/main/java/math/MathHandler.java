package math;


import echo.RequestHandler;

import java.net.Socket;

public class MathHandler extends RequestHandler {
    private int total;
    public MathHandler(){
        super();
    }
    public MathHandler(Socket s) { super(s); }
    private double add(double[] numbers) {
        double sum = 0;
        for (double number : numbers) {
            sum += number;
        }
        return sum;
    }

    private double multiply(double[] numbers) {
        double product = 1;
        for (double number : numbers) {
            product *= number;
        }
        return product;
    }

    private double div(double[] numbers) {
        if (numbers.length == 0) {
            throw new IllegalArgumentException("Cant divide with no numbers");
        }

        double result = numbers[0];

        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i] == 0) {
                throw new ArithmeticException("Cant divide by zero");
            }
            result /= numbers[i];
        }
        return result;
    }

    private double sub(double[] numbers) {
        if (numbers.length == 0) {
            throw new IllegalArgumentException("Cant subtract with no numbers");
        }

        double result = numbers[0];

        for (int i = 1; i < numbers.length; i++) {
            result -= numbers[i];
        }
        return result;
    }
    protected String response(String request) throws Exception {

        if (request.equals("help")) {
            return  "operator num num etc";
        } else if (request.equals("operator")) {
            return "add | mul | sub | and div";
        } else if (request.equals("num")) {
            return "any number";
        }
        String[] parts = request.split(" ");
        String command = parts[0].toLowerCase();
        double total = 0.0;

        try{
            double[] numbers = new double[parts.length - 1];
            for (int i = 1; i < parts.length; i++) {
                numbers[i - 1] = Double.parseDouble(parts[i]);
            }
            // Perform calculation based on operation
            switch (command) {
                case "add":
                    total = add(numbers);
                    break;
                case "mul":
                    total = multiply(numbers);
                    break;
                case "sub":
                    total = sub(numbers);
                    break;
                case "div":
                    total = div(numbers);
                    break;
                default:
                    return "Wrong operation. Supported: add, mul, sub, div";
            }

            return String.valueOf(total);
        } catch (NumberFormatException e) {
            return "Error: Please provide valid numbers";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
