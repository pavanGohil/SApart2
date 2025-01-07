public class Customer {
    private String name;
    private String parcelId;

    public Customer(String name, String parcelId) {
        this.name = name;
        this.parcelId = parcelId;
    }

    public String getName() {
        return name;
    }

    public String getParcelId() {
        return parcelId;
    }

    @Override
    public String toString() {
        return "Customer Name: " + name + ", Parcel ID: " + parcelId;
    }

    public static void main(String[] args) {
        // Test the Customer class
        Customer customer = new Customer("Andrew Robertson", "X123");
        System.out.println(customer);
    }
}
