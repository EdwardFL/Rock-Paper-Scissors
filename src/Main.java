import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

    enum Options {
        PAPER,
        SCISSORS,
        ROCK;

        public String getValueDisplayString() {
            switch (this) {
                case PAPER:
                    return "paper";
                case SCISSORS:
                    return "scissors";
                case ROCK:
                    return "rock";
                default:
                    return "Unknown";
            }
        }

    }

    public class Main {

        private static final String EXIT = "!exit";
        private static final String RATING = "!rating";
        private static int userScore = 0;
        private static String userName;
        private static String choice;
        public static void main(String[] args) {

            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter your name: ");
            userName = scanner.nextLine();
            System.out.printf("Hello, %s\n", userName);

            readScoreBoardFile();

            String userOptions = scanner.nextLine();
            List<String> optionsList = Arrays.asList(userOptions.split(","));;

            System.out.println("Okay, let's start");
            if (userOptions.isEmpty()) {
                playTheBasicGame();
            } else {

                Random random = new Random();

                while (true) {
                    String userChoice = scanner.nextLine();
                    choice = userChoice;
                    if (userChoice.equals(RATING)) {
                        System.out.printf("Your rating: %s\n", userScore);
                    } else if (userChoice.equals(EXIT)) {
                        System.out.println("Bye!");
                        break;
                    } else if (optionsList.contains(userChoice)) {
                        List<String> newList = new ArrayList<>();
                        int chosenIndex = optionsList.indexOf(userChoice);

                        for (int i = chosenIndex + 1; i < optionsList.size(); i++) {
                            newList.add(optionsList.get(i));
                        }
                        for (int i = 0; i < chosenIndex; i++) {
                            newList.add(optionsList.get(i));
                        }

                        String computerChoice = newList.get(random.nextInt(newList.size()));
                        int indexOfComputerChoice =newList.indexOf(computerChoice);
                        int indexOfUserChoice = newList.indexOf(userChoice);

                        int mid = newList.size() / 2;
                        if(newList.indexOf(computerChoice) >= newList.size() / 2) {
                            System.out.println("Well done. The computer chose (" + computerChoice +") and failed");
                            userScore += 100;
                        } else if (newList.indexOf(computerChoice) < newList.size() / 2) {
                            System.out.println("Sorry, but the computer chose (" + computerChoice +")");
                        } else {
                            System.out.println("There is a draw (" + computerChoice + ")");
                            userScore += 50;                }
                    }else {
                        System.out.println("Invalid input");
                    }
                }}
        }

        public static void printResult(String userChoice, Options computerChoice) {

            if (userChoice.equals(computerChoice.getValueDisplayString())) {
                System.out.println("There is a draw (" + computerChoice.getValueDisplayString() + ")");
                userScore += 50;
            } else if ((userChoice.equals("rock") && computerChoice.getValueDisplayString().equals("scissors")) ||
                    (userChoice.equals("paper") && computerChoice.getValueDisplayString().equals("rock")) ||
                    (userChoice.equals("scissors") && computerChoice.getValueDisplayString().equals("paper"))) {
                System.out.println("Well done. The computer chose (" + computerChoice.getValueDisplayString() +") and failed");
                userScore += 100;
            } else {
                System.out.println("Sorry, but the computer chose (" + computerChoice.getValueDisplayString() +")");
            }
        }

        public static void playTheBasicGame() {

            Scanner scanner = new Scanner(System.in);
            Random random = new Random();
            while (true) {
                String userChoice = scanner.nextLine();
                if (userChoice.equals(RATING)) {
                    System.out.printf("Your rating: %s\n", userScore);
                } else if (userChoice.equals(EXIT)) {
                    System.out.println("Bye!");
                    break;
                } else if (userChoice.equals("rock") || userChoice.equals("paper") || userChoice.equals("scissors")) {
                    Options computerChoice = Options.values()[random.nextInt(Options.values().length)];
                    printResult(userChoice, computerChoice);
                } else {
                    System.out.println("Invalid input");
                }
            }
        }

        public static void readScoreBoardFile() {
            File file = new File("rating.txt");
            try (Scanner fileScanner = new Scanner(file)) {
                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine();
                    String[] splitLine = line.split(" ");
                    System.out.println(line);
                    String userNameFromFile = splitLine[0];
                    int userScoreFromFile = Integer.parseInt(splitLine[1]);

                    if (userName.equals(userNameFromFile)) {
                        userScore = userScoreFromFile;
                        break;
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }