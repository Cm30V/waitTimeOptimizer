import java.util.*;

public class Employees {
    double serviceTime;
    int numEmployees;
    int numCustomer;
    double[] listTimes;
    double totalWait = 0.0;
    double[] employeesAvailable;
    double[] arrivalTime;
    List<Double> waitTimes = new ArrayList<>();
    Customers customers;

    public Employees(int numEmployees, int numCustomer, double[] listTimes, Customers customers) {
        this.numEmployees = numEmployees;
        this.numCustomer = numCustomer;
        this.listTimes = listTimes;
        this.customers = customers;
        employeesAvailable = new double[numEmployees];
        arrivalTime = new double[numCustomer];
    }


    public int returnMostAvailable() {

        double lowest = employeesAvailable[0];
        int tracker = 0;

        for (int i = 0; i < numEmployees; i++) {
            if (employeesAvailable[i] < lowest) {
                lowest = employeesAvailable[i];
                tracker = i;
            }
        }
        return tracker;
    }

    public List finishService() {


        waitTimes.clear();
        totalWait = 0;
        double[] interArrivals = new double[numCustomer];

        for (int i = 0; i < numCustomer; i++) {
            interArrivals[i] = customers.returnInterArrivalTime();
        }

        arrivalTime[0] = interArrivals[0];

        for (int i = 1; i < numCustomer; i++) {
            arrivalTime[i] = arrivalTime[i - 1] + interArrivals[i];
        }


        for (int i = 0; i < numCustomer; i++) {

            int mostAvailable = returnMostAvailable();
            double startTime = Math.max(arrivalTime[i], employeesAvailable[mostAvailable]);
            employeesAvailable[mostAvailable] = startTime + listTimes[i];
            double wait = startTime - arrivalTime[i];
            totalWait += wait;
            waitTimes.add(wait);
        }
        return waitTimes;

    }

    public double returnUpperCI() {
        double mean = totalWait/numCustomer;
        double sumSq = 0.0;
        for (double wt : waitTimes) {
            sumSq += Math.pow((wt - mean), 2);
        }
        double stdDev = Math.sqrt(sumSq / (numCustomer - 1));
        return mean + 1.96 * (stdDev / (Math.sqrt(numCustomer)));
    }

    public double returnLowerCI() {
        double mean = totalWait/numCustomer;
        double sumSq = 0.0;
        for (double wt : waitTimes) {
            sumSq += Math.pow((wt - mean), 2);
        }
        double stdDev = Math.sqrt(sumSq / (numCustomer - 1));
        return mean - 1.96 * (stdDev / (Math.sqrt(numCustomer)));
    }
}

