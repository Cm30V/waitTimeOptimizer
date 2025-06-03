import java.util.*;
public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the wait time optimizer");
        System.out.println("First input the following information");
        System.out.print("Average meeting time in minutes: ");
        double serviceTime = scanner.nextDouble();
        System.out.print("+/- minute range of meeting time: ");
        double errorRate = scanner.nextDouble();
        System.out.print("How many employees do you currently have?: ");
        int numEmployees = scanner.nextInt();
        System.out.print("How many customers per day: ");
        int numCustomer = scanner.nextInt();
        System.out.print("How many hours open: ");
        double hoursOpen = scanner.nextDouble();
        System.out.print("Desired wait time: ");
        double desiredTime = scanner.nextDouble();

        boolean meetsDesire = false;

        while(!meetsDesire){
            Customers customers = new Customers(serviceTime, errorRate, numCustomer, hoursOpen);
            customers.generateCustomerTimes();
            double[] listTimes = customers.getCustomerTimes();
            Employees employees = new Employees(numEmployees, numCustomer, listTimes, customers);
            List<Double> waitTimes = employees.finishService();
            double upperCI = employees.returnUpperCI();
            double lowerCI = employees.returnLowerCI();

            if(upperCI <= desiredTime){
                System.out.println("Desired wait time goal achieved.\nWhere there is " + numEmployees +" employees, there is a 95% Confidence Interval for average wait time from [" + roundTo(lowerCI,2)+ ", " + roundTo(upperCI,2) + "] minutes.");
                break;
            }
            numEmployees++;
        }
    }
    public static double roundTo(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }
}
