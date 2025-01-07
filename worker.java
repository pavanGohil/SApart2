public class Worker {
    private Parcel currentParcel;

    public void processCustomer(Customer customer, ParcelMap parcelMap) {
        String parcelId = customer.getParcelId();

        if (parcelMap.containsParcel(parcelId)) {
            currentParcel = parcelMap.getParcel(parcelId);
            System.out.println("Processing Customer: " + customer.getName());
            System.out.println("Parcel Details: " + currentParcel);
            System.out.printf("Fee Calculated: $%.2f%n", currentParcel.calculateFee());
        } else {
            System.out.println("Parcel not found for Customer: " + customer.getName());
            currentParcel = null;
        }
    }

    public String getCurrentParcelInfo() {
        if (currentParcel != null) {
            return currentParcel.toString();
        } else {
            return "No parcel currently being processed.";
        }
    }
}
