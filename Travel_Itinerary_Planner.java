import java.util.ArrayList;
import java.util.Scanner;

class Destination {
    private String name;
    private String date;
    private double accommodationBudget;
    private double foodBudget;
    private double activityBudget;
    private String travelMode;
    private double travelCost;
    private String travelNotes;
    private boolean reminderSet;

    public Destination(String name, String date, double accommodationBudget, double foodBudget, double activityBudget, String travelMode, double travelCost) {
        this.name = name;
        this.date = date;
        this.accommodationBudget = accommodationBudget;
        this.foodBudget = foodBudget;
        this.activityBudget = activityBudget;
        this.travelMode = travelMode;
        this.travelCost = travelCost;
        this.travelNotes = "";
        this.reminderSet = false;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public double getTotalBudget() {
        return accommodationBudget + foodBudget + activityBudget + travelCost;
    }

    public void addTravelNotes(String notes) {
        this.travelNotes = notes;
        System.out.println("Notes added for " + name + ": " + notes);
    }

    public void setReminder() {
        this.reminderSet = true;
        System.out.println("Reminder set for " + name + " on " + date);
    }

    public void displayDetails() {
        System.out.println("\nDestination: " + name);
        System.out.println("Date: " + date);
        System.out.println("Accommodation Budget: $" + accommodationBudget);
        System.out.println("Food Budget: $" + foodBudget);
        System.out.println("Activity Budget: $" + activityBudget);
        System.out.println("Travel Mode: " + travelMode + " (Cost: $" + travelCost + ")");
        System.out.println("Total Budget: $" + getTotalBudget());
        System.out.println("Travel Notes: " + (travelNotes.isEmpty() ? "No notes added." : travelNotes));
        System.out.println("Reminder Set: " + (reminderSet ? "Yes" : "No"));
        displayWeather();
    }

    public void displayWeather() {
        String[] weatherConditions = {"Sunny", "Cloudy", "Rainy", "Windy"};
        String weather = weatherConditions[(int) (Math.random() * weatherConditions.length)];
        System.out.println("Weather Forecast (simulated): " + ((int) (Math.random() * 15) + 20) + "°C, " + weather);
    }
}

public class Travel_Itinerary_Planner {
    private static ArrayList<Destination> itinerary = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to the Enhanced Travel Itinerary Planner!");

        while (true) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Add Destination");
            System.out.println("2. View Itinerary");
            System.out.println("3. Add Notes to Destination");
            System.out.println("4. Set Reminder for Destination");
            System.out.println("5. Calculate Total Budget");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addDestination();
                    break;
                case 2:
                    viewItinerary();
                    break;
                case 3:
                    addNotesToDestination();
                    break;
                case 4:
                    setReminderForDestination();
                    break;
                case 5:
                    calculateTotalBudget();
                    break;
                case 6:
                    System.out.println("Thank you for using the Travel Itinerary Planner! Safe travels!");
                    return;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }

    private static void addDestination() {
        System.out.print("Enter destination name: ");
        String name = scanner.nextLine();
        System.out.print("Enter travel date (e.g., 2024-12-31): ");
        String date = scanner.nextLine();
        System.out.print("Enter budget for accommodation: ");
        double accommodation = scanner.nextDouble();
        System.out.print("Enter budget for food: ");
        double food = scanner.nextDouble();
        System.out.print("Enter budget for activities: ");
        double activities = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter travel mode (e.g., flight, train, car): ");
        String travelMode = scanner.nextLine();
        System.out.print("Enter travel cost: ");
        double travelCost = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        Destination destination = new Destination(name, date, accommodation, food, activities, travelMode, travelCost);
        itinerary.add(destination);
        System.out.println("Destination added to itinerary!");
    }

    private static void viewItinerary() {
        System.out.println("\nYour Travel Itinerary:");
        if (itinerary.isEmpty()) {
            System.out.println("No destinations added yet.");
        } else {
            for (int i = 0; i < itinerary.size(); i++) {
                System.out.println("\n" + (i + 1) + ". " + itinerary.get(i).getName() + " on " + itinerary.get(i).getDate());
                itinerary.get(i).displayDetails();
            }
        }
    }

    private static void addNotesToDestination() {
        if (itinerary.isEmpty()) {
            System.out.println("No destinations available to add notes.");
            return;
        }

        System.out.print("Enter the number of the destination to add notes: ");
        int index = scanner.nextInt() - 1;
        scanner.nextLine(); // Consume newline

        if (index >= 0 && index < itinerary.size()) {
            System.out.print("Enter notes for the destination: ");
            String notes = scanner.nextLine();
            itinerary.get(index).addTravelNotes(notes);
        } else {
            System.out.println("Invalid destination number.");
        }
    }

    private static void setReminderForDestination() {
        if (itinerary.isEmpty()) {
            System.out.println("No destinations available to set reminders.");
            return;
        }

        System.out.print("Enter the number of the destination to set a reminder: ");
        int index = scanner.nextInt() - 1;

        if (index >= 0 && index < itinerary.size()) {
            itinerary.get(index).setReminder();
        } else {
            System.out.println("Invalid destination number.");
        }
    }

    private static void calculateTotalBudget() {
        double totalBudget = 0;

        for (Destination destination : itinerary) {
            totalBudget += destination.getTotalBudget();
        }

        System.out.println("\nOverall Budget for All Destinations: $" + totalBudget);
    }
}