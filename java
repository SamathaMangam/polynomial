import java.math.BigInteger;
import java.util.*;

public class Main {

    // Convert number string from given base to decimal
    static BigInteger toDecimal(String value, int base) {
        return new BigInteger(value, base);
    }

    // Method to compute constant term from JSON string
    static void processJson(String jsonStr) {
        // Extract n and k
        int n = Integer.parseInt(jsonStr.split("\"n\":")[1].split(",")[0].trim());
        int k = Integer.parseInt(jsonStr.split("\"k\":")[1].split("}")[0].trim());

        List<BigInteger> roots = new ArrayList<>();
        String[] parts = jsonStr.split("\\},");

        int count = 0;
        for (String part : parts) {
            if (part.contains("\"base\"") && part.contains("\"value\"")) {
                String baseStr = part.split("\"base\":")[1].split(",")[0].replaceAll("[^0-9]", "");
                String valueStr = part.split("\"value\":")[1].split("[}\\\"]")[1].trim();

                int base = Integer.parseInt(baseStr);
                BigInteger root = toDecimal(valueStr, base);
                roots.add(root);

                count++;
                if (count == k) break; // only need first k roots
            }
        }

        // Compute constant term
        BigInteger constant = BigInteger.ONE;
        for (BigInteger r : roots) {
            constant = constant.multiply(r);
        }
        if (k % 2 != 0) {
            constant = constant.negate();
        }

        // Print only constant c
        System.out.println("Output (constant c) = ");
        System.out.println(constant);
        System.out.println();
    }

    public static void main(String[] args) {

        // Sample Test Case
        String jsonStr1 = """
        {
            "keys": {
                "n": 4,
                "k": 3
            },
            "1": {
                "base": "10",
                "value": "4"
            },
            "2": {
                "base": "2",
                "value": "111"
            },
            "3": {
                "base": "10",
                "value": "12"
            },
            "6": {
                "base": "4",
                "value": "213"
            }
        }
        """;

        // Second Test Case
        String jsonStr2 = """
        {
            "keys": {
                "n": 10,
                "k": 7
            },
            "1": {
                "base": "6",
                "value": "13444211440455345511"
            },
            "2": {
                "base": "15",
                "value": "aed7015a346d635"
            },
            "3": {
                "base": "15",
                "value": "6aeeb69631c227c"
            },
            "4": {
                "base": "16",
                "value": "e1b5e05623d881f"
            },
            "5": {
                "base": "8",
                "value": "316034514573652620673"
            },
            "6": {
                "base": "3",
                "value": "2122212201122002221120200210011020220200"
            },
            "7": {
                "base": "3",
                "value": "20120221122211000100210021102001201112121"
            },
            "8": {
                "base": "6",
                "value": "20220554335330240002224253"
            },
            "9": {
                "base": "12",
                "value": "45153788322a1255483"
            },
            "10": {
                "base": "7",
                "value": "1101613130313526312514143"
            }
        }
        """;

        System.out.println("=== Sample Test Case ===");
        processJson(jsonStr1);

        System.out.println("=== Second Test Case ===");
        processJson(jsonStr2);
    }
}
