import java.util.LinkedList;
import java.util.Queue;

public class QueOfCustomers {
    private Queue<Customer> customerQueue;

    public QueOfCustomers() {
        this.customerQueue = new LinkedList<>();
    }

    public void addCustomer(Customer customer) {
        customerQueue.offer(customer);
    }

    public Customer removeCustomer() {
        return customerQueue.poll();
    }

    public boolean isEmpty() {
        return customerQueue.isEmpty();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Customer customer : customerQueue) {
            sb.append(customer.getName()).append("\n");
        }
        return sb.toString();
    }
}
