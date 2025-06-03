import java.util.*;
public class Customers {
    double newTime;
    double serviceTime;
    double errorRate;
    double hoursOpen;
    int numCustomer;
    Random random = new Random();

    public Customers(double serviceTime, double errorRate, int numCustomer, double hoursOpen) {
        this.serviceTime = serviceTime;
        this.errorRate = errorRate;
        this.numCustomer = numCustomer;
        this.hoursOpen = hoursOpen;
    }



    public double errorTimes() {
        double min = serviceTime - errorRate;
        double max = serviceTime + errorRate;
        newTime = min + random.nextDouble() * ((max - min));
        return newTime;
    }
    double[] customersTimes;

    public void generateCustomerTimes(){
        customersTimes = new double[numCustomer];
        for (int i = 0; i<numCustomer; i++){
            customersTimes[i] = errorTimes();
        }
    }
    public double returnInterArrivalTime() {
        return (-Math.log(random.nextDouble())) / (numCustomer / (60 * hoursOpen));
    }
    public double[] getCustomerTimes() {
        return customersTimes;
    }
}
