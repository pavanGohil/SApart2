import java.util.HashMap;

public class ParcelMap {
    private HashMap<String, Parcel> parcelMap;

    public ParcelMap() {
        parcelMap = new HashMap<>();
    }

    public void addParcel(Parcel parcel) {
        parcelMap.put(parcel.getParcelId(), parcel);
    }

    public Parcel getParcel(String parcelId) {
        return parcelMap.get(parcelId);
    }

    public boolean containsParcel(String parcelId) {
        return parcelMap.containsKey(parcelId);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Parcels in System:\n");
        for (Parcel parcel : parcelMap.values()) {
            sb.append(parcel).append("\n");
        }
        return sb.toString();
    }
}
