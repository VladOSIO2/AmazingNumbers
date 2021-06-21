package numbers;

import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    enum Properties {
        ODD, EVEN, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, SQUARE, SUNNY, JUMPING, HAPPY, SAD;

        static final String[] listOfProperties = Arrays
                .stream(values())
                .map(Objects::toString)
                .toArray(String[]::new);

        public static boolean noSuchProperty (String keyword) {
            //comparing keyword to each of properties
            for (String prop : listOfProperties) {
                if (keyword.equals(prop)) return false;
            }
            return true;
        }

        public static String[] getListOfProperties () {
            return listOfProperties;
        }
    }

    public static void main(String[] args) {
//        variables
        Scanner scanner = new Scanner(System.in);
//        on-start message
        System.out.println("Welcome to Amazing Numbers!\n\n" +
                "Supported requests:\n" +
                "- enter a natural number to know its properties;\n" +
                "- enter two natural numbers to obtain the properties of the list:\n" +
                "\t* the first parameter represents a starting number;\n" +
                "\t* the second parameter shows how many consecutive numbers are to be printed;\n" +
                "- two natural numbers and properties to search for;\n" +
                "- a property preceded by minus must not be present in numbers;\n" +
                "- separate the parameters with one space;\n" +
                "- enter 0 to exit.\n");
//        number analyzer loop
        while (true) {
            //getting request
            System.out.print("Enter a request: ");
            String[] strArr = scanner.nextLine().split(" ");
            long number = Long.parseLong(strArr[0]);
            if (number < 0) {
                System.out.println("\nThe first parameter should be a natural number or zero.\n");
                continue;
            }
            //on exit input
            if (strArr[0].equals("0") && strArr.length == 1) {
                System.out.println("\nGoodbye!");
                return;
            }
            //info output
            if (strArr.length == 1) { //one number entered
                printOneNumberInfo(number);
            } else { //number, amount and properties
                int amount = Integer.parseInt(strArr[1]);
                if (amount < 0) {
                    System.out.println("\nThe second parameter should be a natural number.\n");
                    continue;
                }
                printSearchNumberSequenceInfo(
                        number, //current number
                        amount, //sequence size
                        Arrays.copyOfRange(strArr, 2, strArr.length) //arr of properties
                );
            }
        }
    }

    static boolean isEven (long number) {
        return number % 2 == 0;
    }

    static boolean isOdd (long number) {
        return number % 2 == 1;
    }

    static boolean isBuzz (long number) {
        //if number is buzz - it divisible on 7 or contains 7 on last digit
        return (number % 7 == 0) || (number % 10 == 7);
    }

    static boolean isDuck (long number) {
        return Long.toString(number).contains("0");
    }

    static boolean isPalindromic (long number) {
        //number palindromic property (ex: 12321, 724427)
        String numberStr = Long.toString(number);
        boolean isPalindromic = true;
        for (int i = 0; i < numberStr.length() / 2; i++) {
            if (numberStr.charAt(i) != numberStr.charAt(numberStr.length() - 1 - i)) {
                isPalindromic = false;
                break;
            }
        }
        return isPalindromic;
    }

    static boolean isGapful (long number) {
        //number is gapful when has >3 digits and concat of 1st and last digit is divider of number
        char[] ch = Long.toString(number).toCharArray();
        int gapNumber = Character.getNumericValue(ch[0]) * 10 + Character.getNumericValue(ch[ch.length - 1]);
        return ch.length > 2 && number % gapNumber == 0;
    }

    static boolean isSpy (long number) {
        //number is spy if sum of digits == equation of digits
        String numStr = Long.toString(number);
        int sum = 0;
        int equation = 1;
        for (int i = 0; i < numStr.length(); i++) {
            int currentDigit = Character.getNumericValue(numStr.charAt(i));
            sum += currentDigit;
            equation *= currentDigit;
        }
        return sum == equation;
    }

    static boolean isSquare (long number) {
        /*
         if square of rounded down square root
         of number equals to that number then
         given number is a perfect square root
         */
        double numRoot = Math.sqrt(number);
        numRoot = Math.floor(numRoot);
        return (numRoot * numRoot == number);
    }

    static boolean isSunny (long number) {
        //number is sunny if next number is a perfect square
        return isSquare(number + 1);
    }

    static boolean isJumping (long number) {
        /*
          Number is jumping if each next digit differs from past by 1.
          The difference between 9 and 0 is not considered as 1
          Jumping: 78765, 12345
          Not jumping: 159, 7890
         */
        char[] digits = Long.toString(number).toCharArray();
        //one-digit number is a jumping number
        if (digits.length == 1) return true;
        //checking the number
        for (int i = 0; i < digits.length - 1; i++) {
            if (Math.abs(digits[i] - digits[i + 1]) != 1) return false;
            if (digits[i] == '9' && digits [i + 1] == '0') return false;
            if (digits[i] == '0' && digits [i + 1] == '9') return false;
        }
        //all digits in number are appropriate then it`s a jumping number
        return true;
    }

    static boolean isHappy (long number) {
        /*
          In number theory, a happy number is a number that reaches 1 after a sequence
          during which the number is replaced by the sum of each digit squares.
          For example, 13 is a happy number, as 1^2 + 3^2 = 10 which leads to 1^2 + 0^2 = 1.
          4 is not a happy number cause it will always come back to 4
         */
        while (true) {
            char[] digits = Long.toString(number).toCharArray();
            int sum = 0;
            for (char digit : digits) {
                sum += Math.pow(Character.getNumericValue(digit), 2);
            }
            if (sum == 4) return false; //number came to 4 - not a happy number
            if (sum == 1) return true; //number came to 1 - it's a happy number
            number = sum;
        }
    }

    static boolean isSad (long number) {
        return !isHappy(number);
    }

    static void printOneNumberInfo (long number) {
        if (number < 0) {
            System.out.println("\nThe first parameter should be a natural number or zero.\n");
            return;
        }
        System.out.println("\nProperties of " + number);
        System.out.println("\teven: " + isEven(number));
        System.out.println("\todd: " + isOdd(number));
        System.out.println("\tbuzz: " + isBuzz(number));
        System.out.println("\tduck: " + isDuck(number));
        System.out.println("\tgapful: " + isGapful(number));
        System.out.println("\tpalindromic: " + isPalindromic(number));
        System.out.println("\tspy: " + isSpy(number));
        System.out.println("\tsquare: " + isSquare(number));
        System.out.println("\tsunny: " + isSunny(number));
        System.out.println("\tjumping: " + isJumping(number));
        System.out.println("\thappy: " + isHappy(number));
        System.out.println("\tsad: " + isSad(number));
        System.out.println();
    }

    static void printOneNumberInfoShorted (long number) {
        if (number < 0) {
            System.out.println("\nThe first parameter should be a natural number or zero.\n");
            return;
        }
        StringBuilder info;
        info = new StringBuilder(number + " is ");
        info.append(isBuzz(number) ? "buzz, " : "");
        info.append(isDuck(number) ? "duck, " : "");
        info.append(isPalindromic(number) ? "palindromic, " : "");
        info.append(isGapful(number) ? "gapful, " : "");
        info.append(isSpy(number) ? "spy, " : "");
        info.append(isSquare(number) ? "square, " : "");
        info.append(isSunny(number) ? "sunny, " : "");
        info.append(isJumping(number) ? "jumping, " : "");
        info.append(isHappy(number) ? "happy, " : "");
        info.append(isSad(number) ? "sad, " : "");
        info.append(isOdd(number) ? "odd" : "even");
        System.out.println(info);
    }

    static void printSearchNumberSequenceInfo (long number, int amount, String... keywords) {

        //property validation
        if (isWrongPropertyCombination(keywords)) { //found inappropriate keyword combination
            return;
        }
        StringBuilder notExistingKeywords = new StringBuilder();
        for (int i = 0; i < keywords.length; i++) {
            keywords[i] = keywords[i].toUpperCase();
            if (Properties.noSuchProperty(keywords[i].replace("-", ""))) {
                notExistingKeywords.append(keywords[i]).append(" ");
            }
        }
        if (notExistingKeywords.length() != 0) { //found not existing keywords
            printWrongPropertyInfo(notExistingKeywords.toString().split(" "));
            return;
        }
        //number finder
        int counter = 0;
        do {
            boolean isProperNumber = true;
            for (String key : keywords) {
                isProperNumber &=
                        (key.charAt(0) == '-') != hasProperty(number, key.replace("-", ""));
            }
            if (isProperNumber) {
                //if current number matches chosen properties
                printOneNumberInfoShorted(number);
                counter++;
            }
            number++;
        } while (counter != amount);
    }

    static boolean hasProperty (long number, String keyword) {
        switch (keyword) {
            case "ODD": return isOdd(number);
            case "EVEN": return isEven(number);
            case "BUZZ": return isBuzz(number);
            case "DUCK": return isDuck(number);
            case "PALINDROMIC": return isPalindromic(number);
            case "GAPFUL": return isGapful(number);
            case "SPY": return isSpy(number);
            case "SQUARE": return isSquare(number);
            case "SUNNY": return isSunny(number);
            case "JUMPING": return isJumping(number);
            case "HAPPY": return isHappy(number);
            case "SAD": return isSad(number);
        }
        return false;
    }

    static void printWrongPropertyInfo (String... listOfProperties) {
        if (listOfProperties.length == 1) {
            System.out.println("The property [" + listOfProperties[0] + "] is wrong.");
        } else {
            StringBuilder info = new StringBuilder();
            for (String prop : listOfProperties) {
                info.append(prop).append(", ");
            }
            info.delete(info.lastIndexOf(", "), info.length());
            System.out.println("The properties [" + info + "] are wrong.");
        }
        System.out.println("Available properties "+ Arrays.toString(Properties.getListOfProperties()));
    }

    static boolean isWrongPropertyCombination (String... keywords) {
        /*
        checks if gotten keyword array contains
        mutually exclusive properties,
        returns boolean value based on it
        prints inappropriate keywords if present
         */
        //for including or excluding keyword
        String[] keywordArr = Arrays
                .stream(keywords)
                .map(String::new)
                .toArray(String[]::new);
        for (int i = 0; i < keywords.length; i++) {
            keywordArr[i] = keywordArr[i].toUpperCase();
            if (keywordArr[i].charAt(0) != '-') {
                keywordArr[i] = "+" + keywordArr[i];
            }
        }
        String keys = Arrays.toString(keywordArr);
        String wrongCombination = null;
        if (keys.contains("+EVEN") && keys.contains("+ODD")) {
            wrongCombination = "EVEN, ODD";
        }
        if (keys.contains("+SPY") && keys.contains("+DUCK")) {
            wrongCombination = "SPY, DUCK";
        }
        if (keys.contains("+SQUARE") && keys.contains("+SUNNY")) {
            wrongCombination = "SQUARE, SUNNY";
        }
        if (keys.contains("+HAPPY") && keys.contains("+SAD")) {
            wrongCombination = "HAPPY, SAD";
        }
        if (keys.contains("-EVEN") && keys.contains("-ODD")) {
            wrongCombination = "-EVEN, -ODD";
        }
        if (keys.contains("-HAPPY") && keys.contains("-SAD")) {
            wrongCombination = "-HAPPY, -SAD";
        }
        //excluding the +key and -key at the same time
        for (String prop : Properties.getListOfProperties()) {
            if (keys.contains("-" + prop) && keys.contains("+" + prop)) {
                wrongCombination = "-" + prop + ", +" + prop;
            }
        }
        if (wrongCombination == null) {
            return false;
        } else {
            System.out.println(
                    "The request contains mutually exclusive properties: [" + wrongCombination + "]\n" +
                            "There are no numbers with these properties.");
            return true;
        }
    }
}
