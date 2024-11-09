import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class StudentGradeTracker {
    private ArrayList<ArrayList<Integer>> studentMarks;
    private int numSubjects;
    private int numStudents;

    // Constructor to initialize the number of subjects and students
    public StudentGradeTracker(int numSubjects, int numStudents) {
        this.numSubjects = numSubjects;
        this.numStudents = numStudents;
        studentMarks = new ArrayList<>();

        // Initialize the list of marks for each student
        for (int i = 0; i < numStudents; i++) {
            studentMarks.add(new ArrayList<>());
        }
    }

    // Method to add marks for a student in a specific subject
    public void addMarks(int studentIndex, int marks) {
        studentMarks.get(studentIndex).add(marks);
    }

    // Method to calculate the average score for each student
    public double calculateStudentAverage(int studentIndex) {
        ArrayList<Integer> marks = studentMarks.get(studentIndex);
        int total = 0;
        for (int mark : marks) {
            total += mark;
        }
        return marks.isEmpty() ? 0 : (double) total / marks.size();
    }

    // Method to calculate the total marks of all students (for class average)
    public double calculateClassTotalMarks() {
        double totalMarks = 0;
        for (int i = 0; i < numStudents; i++) {
            totalMarks += calculateStudentAverage(i) * numSubjects;
        }
        return totalMarks;
    }

    // Method to calculate the average score for the entire class
    public double calculateClassAverage() {
        double totalMarks = calculateClassTotalMarks();
        return totalMarks / (numStudents * numSubjects);
    }

    // Method to determine grade based on average score
    public String calculateGrade(double average) {
        if (average >= 95) return "A+";
        else if (average >= 90) return "A";
        else if (average >= 85) return "B+";
        else if (average >= 80) return "B";
        else if (average >= 75) return "C+";
        else if (average >= 70) return "C";
        else if (average >= 60) return "D";
        else return "F";
    }

    // Method to calculate pass/fail status
    public String passOrFail(double average) {
        return average >= 60 ? "Pass" : "Fail";
    }

    // Method to calculate median score for the entire class
    public double calculateClassMedian() {
        ArrayList<Double> averages = new ArrayList<>();
        for (int i = 0; i < numStudents; i++) {
            averages.add(calculateStudentAverage(i));
        }
        Collections.sort(averages);
        int mid = averages.size() / 2;
        return averages.size() % 2 == 0 ? (averages.get(mid - 1) + averages.get(mid)) / 2 : averages.get(mid);
    }

    // Method to display the top-performing student
    public void displayTopPerformer() {
        double highestAvg = 0;
        int topStudentIndex = -1;
        for (int i = 0; i < numStudents; i++) {
            double avg = calculateStudentAverage(i);
            if (avg > highestAvg) {
                highestAvg = avg;
                topStudentIndex = i;
            }
        }
        if (topStudentIndex != -1) {
            System.out.println("Top Performer: Student " + (topStudentIndex + 1));
            System.out.println("Average Marks: " + highestAvg);
            System.out.println("Grade: " + calculateGrade(highestAvg));
            System.out.println("Status: " + passOrFail(highestAvg));
        }
    }

    // Display formatted student-wise results
    public void displayStudentResults() {
        System.out.println("\nStudent-wise Averages, Grades, and Status:");
        for (int i = 0; i < numStudents; i++) {
            double average = calculateStudentAverage(i);
            System.out.printf("Student %d - Average Marks: %.2f, Grade: %s, Status: %s\n", (i + 1), average, calculateGrade(average), passOrFail(average));
        }
    }

    // Display formatted class result
    public void displayClassResult() {
        double classAverage = calculateClassAverage();
        double classMedian = calculateClassMedian();
        System.out.println("\nClass Average Marks: " + classAverage);
        System.out.println("Class Median Marks: " + classMedian);
        System.out.println("Class Grade: " + calculateGrade(classAverage));
    }

    // Method for user input handling
    public static int getValidIntInput(Scanner scanner, String prompt, int min, int max) {
        int input;
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                input = scanner.nextInt();
                if (input >= min && input <= max) {
                    break;
                } else {
                    System.out.println("Please enter a valid number between " + min + " and " + max + ".");
                }
            } else {
                System.out.println("Invalid input! Please enter a valid integer.");
                scanner.next(); // Consume the invalid input
            }
        }
        return input;
    }

    // Main method to run the program
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Display menu options
        System.out.println("Welcome to the Student Grade Tracker System!");

        // Input number of subjects and students with validation
        int numSubjects = getValidIntInput(scanner, "Enter the number of subjects: ", 1, 10);
        int numStudents = getValidIntInput(scanner, "Enter the number of students: ", 1, 50);

        // Create a new tracker object
        StudentGradeTracker tracker = new StudentGradeTracker(numSubjects, numStudents);

        // Enter marks for each student across all subjects
        for (int i = 0; i < numStudents; i++) {
            System.out.println("\nEntering marks for Student " + (i + 1) + ":");
            for (int j = 0; j < numSubjects; j++) {
                int marks = getValidIntInput(scanner, "Enter marks for Subject " + (j + 1) + " (0-100): ", 0, 100);
                tracker.addMarks(i, marks);
            }
        }

        // Main menu to display results or perform actions
        boolean continueMenu = true;
        while (continueMenu) {
            System.out.println("\nChoose an option:");
            System.out.println("1. View student results");
            System.out.println("2. View class results");
            System.out.println("3. View top performer");
            System.out.println("4. Exit");
            int choice = getValidIntInput(scanner, "Enter your choice (1-4): ", 1, 4);

            switch (choice) {
                case 1:
                    tracker.displayStudentResults();
                    break;
                case 2:
                    tracker.displayClassResult();
                    break;
                case 3:
                    tracker.displayTopPerformer();
                    break;
                case 4:
                    System.out.println("Exiting the program... Thank you!");
                    continueMenu = false;
                    break;
                default:
                    System.out.println("Invalid choice! Please select a valid option.");
            }
        }

        scanner.close();
    }
}