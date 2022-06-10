import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        String q1 = "Multiple Choice 1\n" + "(a)Test1\n(b)Test2\n(c)Test3";
        String q2 = "Multiple Choice 2\n" + "(a)Test4\n(b)Test5\n(c)Test6";

        Question[] questions = {
                new Question(q1, "a"),
                new Question(q2, "c")
        };

        takeTask(questions);
    }

    public static void takeTask(Question[] questions) {
        @SuppressWarnings("resource")
        Scanner input = new Scanner(System.in);
        int score = 0;

        for (int i = 0; i < questions.length; i++) {
            System.out.println(questions[i].prompt);
            System.out.print("Answer : ");
            String answer = input.nextLine().toLowerCase();
            if (answer.equals(questions[i].answer)) {
                score++;
            }
        }
        System.out.println("Your score: " + score + "/" + questions.length);
    }
}
