import java.util.LinkedList;
import java.util.Queue;

public class Manager {
    private QueOfCustomers customerQueue;
    private ParcelMap parcelMap;
    private Worker worker;

    public Manager(QueOfCustomers customerQueue, ParcelMap parcelMap, Worker worker) {
        this.customerQueue = customerQueue;
        this.parcelMap = parcelMap;
        this.worker = worker;
    }

    public QueOfCustomers getCustomerQueue() {
        return customerQueue;
    }

    public ParcelMap getParcelMap() {
        return parcelMap;
    }

    public void processNextCustomer() {
        if (!customerQueue.isEmpty()) {
            Customer nextCustomer = customerQueue.removeCustomer();
            Parcel parcel = parcelMap.getParcel(nextCustomer.getParcelId());

            if (parcel != null) {
                worker.processCustomer(nextCustomer, parcelMap);
                Log.getInstance().logEvent("Processed customer: " + nextCustomer.getName());
            } else {
                Log.getInstance().logEvent("No parcel found for Customer ID: " + nextCustomer.getParcelId());
            }
        }
    }
}

