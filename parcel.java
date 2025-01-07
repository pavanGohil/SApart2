public class Parcel {
    private String parcelId; // Unique ID for the parcel
    private int weight;
    private int length;
    private int width;
    private int height;
    private int feePerKg;

    public Parcel(String parcelId, int weight, int length, int width, int height, int feePerKg) {
        this.parcelId = parcelId;
        this.weight = weight;
        this.length = length;
        this.width = width;
        this.height = height;
        this.feePerKg = feePerKg;
    }

    // Getter for parcel ID
    public String getParcelId() {
        return parcelId;
    }

    public int getWeight() {
        return weight;
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    // Method to calculate the fee
    public double calculateFee() {
        return weight * feePerKg;
    }

    @Override
    public String toString() {
        return String.format("Parcel ID: %s, Weight: %d kg, Dimensions: %d x %d x %d cm, Fee: $%.2f",
                parcelId, weight, length, width, height, calculateFee());
    }
}
