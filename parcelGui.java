import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ParcelGUI extends JFrame {
    private JTextArea parcelListArea;
    private JTextArea customerQueueArea;
    private JTextArea currentParcelArea;
    private JButton processButton;
    private JButton addParcelButton;
    private JButton addCustomerButton;
    private JButton removeCustomerButton;
    private QueOfCustomers queOfCustomers;
    private ParcelMap parcelMap;
    private Worker worker;

    public ParcelGUI(QueOfCustomers queOfCustomers, ParcelMap parcelMap, Worker worker) {
        this.queOfCustomers = queOfCustomers;
        this.parcelMap = parcelMap;
        this.worker = worker;

        setTitle("Parcel Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Parcel List Panel
        JPanel parcelListPanel = new JPanel(new BorderLayout());
        parcelListPanel.setBorder(BorderFactory.createTitledBorder("Parcels to be Processed"));
        parcelListArea = new JTextArea(10, 30);
        parcelListArea.setEditable(false);
        parcelListPanel.add(new JScrollPane(parcelListArea), BorderLayout.CENTER);

        // Customer Queue Panel
        JPanel customerQueuePanel = new JPanel(new BorderLayout());
        customerQueuePanel.setBorder(BorderFactory.createTitledBorder("Customer Queue"));
        customerQueueArea = new JTextArea(10, 30);
        customerQueueArea.setEditable(false);
        customerQueuePanel.add(new JScrollPane(customerQueueArea), BorderLayout.CENTER);

        // Current Parcel Panel
        JPanel currentParcelPanel = new JPanel(new BorderLayout());
        currentParcelPanel.setBorder(BorderFactory.createTitledBorder("Current Parcel Details"));
        currentParcelArea = new JTextArea(5, 30);
        currentParcelArea.setEditable(false);
        currentParcelPanel.add(new JScrollPane(currentParcelArea), BorderLayout.CENTER);

        // Process Button
        processButton = new JButton("Process Next Customer");
        processButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processNextCustomer();
            }
        });

        // Add Parcel Button
        addParcelButton = new JButton("Add Parcel");
        addParcelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addParcel();
            }
        });

        // Add Customer Button
        addCustomerButton = new JButton("Add Customer");
        addCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCustomer();
            }
        });

        // Remove Customer Button
        removeCustomerButton = new JButton("Remove Customer");
        removeCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeCustomer();
            }
        });

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(processButton);
        buttonPanel.add(addParcelButton);
        buttonPanel.add(addCustomerButton);
        buttonPanel.add(removeCustomerButton);

        // Add components to frame
        add(parcelListPanel, BorderLayout.WEST);
        add(customerQueuePanel, BorderLayout.EAST);
        add(currentParcelPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        updateDisplay();
    }

    private void processNextCustomer() {
        if (!queOfCustomers.isEmpty()) {
            Customer customer = queOfCustomers.removeCustomer();
            Parcel parcel = parcelMap.getParcel(customer.getParcelId());

            if (parcel != null) {
                worker.processCustomer(customer, parcelMap);

                // Display Customer and Parcel details
                String details = String.format(
                        "Customer Name: %s%nParcel ID: %s%nWeight: %d kg%nDimensions: %d x %d x %d cm%nCalculated Fee: $%.2f",
                        customer.getName(),
                        parcel.getParcelId(),
                        parcel.getWeight(),
                        parcel.getLength(),
                        parcel.getWidth(),
                        parcel.getHeight(),
                        parcel.calculateFee()
                );
                currentParcelArea.setText(details);
                Log.getInstance().logEvent("Processed customer: " + customer.getName());
            } else {
                currentParcelArea.setText("No parcel found for Customer ID: " + customer.getParcelId());
            }

            updateDisplay();
        } else {
            JOptionPane.showMessageDialog(this, "No more customers in the queue.");
        }
    }

    private void addParcel() {
        JTextField parcelIdField = new JTextField();
        JTextField weightField = new JTextField();
        JTextField heightField = new JTextField();
        JTextField widthField = new JTextField();
        JTextField depthField = new JTextField();
        JTextField distanceField = new JTextField();

        JPanel inputPanel = new JPanel(new GridLayout(6, 2));
        inputPanel.add(new JLabel("Parcel ID:"));
        inputPanel.add(parcelIdField);
        inputPanel.add(new JLabel("Weight (kg):"));
        inputPanel.add(weightField);
        inputPanel.add(new JLabel("Height (cm):"));
        inputPanel.add(heightField);
        inputPanel.add(new JLabel("Width (cm):"));
        inputPanel.add(widthField);
        inputPanel.add(new JLabel("Depth (cm):"));
        inputPanel.add(depthField);
        inputPanel.add(new JLabel("Distance (km):"));
        inputPanel.add(distanceField);

        int result = JOptionPane.showConfirmDialog(
                this, inputPanel, "Add New Parcel", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String parcelId = parcelIdField.getText();
                int weight = Integer.parseInt(weightField.getText());
                int height = Integer.parseInt(heightField.getText());
                int width = Integer.parseInt(widthField.getText());
                int depth = Integer.parseInt(depthField.getText());
                int distance = Integer.parseInt(distanceField.getText());

                Parcel newParcel = new Parcel(parcelId, weight, height, width, depth, distance);
                parcelMap.addParcel(newParcel);
                JOptionPane.showMessageDialog(this, "Parcel added successfully!");
                updateDisplay();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid numbers for dimensions and weight.");
            }
        }
    }

    private void addCustomer() {
        JTextField nameField = new JTextField();
        JTextField parcelIdField = new JTextField();

        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        inputPanel.add(new JLabel("Customer Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Parcel ID:"));
        inputPanel.add(parcelIdField);

        int result = JOptionPane.showConfirmDialog(
                this, inputPanel, "Add New Customer", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String customerName = nameField.getText();
            String parcelId = parcelIdField.getText();

            Customer newCustomer = new Customer(customerName, parcelId);
            queOfCustomers.addCustomer(newCustomer);
            JOptionPane.showMessageDialog(this, "Customer added successfully!");
            updateDisplay();
        }
    }

    private void removeCustomer() {
        if (!queOfCustomers.isEmpty()) {
            Customer removedCustomer = queOfCustomers.removeCustomer();
            JOptionPane.showMessageDialog(this, "Customer " + removedCustomer.getName() + " removed from queue.");
            updateDisplay();
        } else {
            JOptionPane.showMessageDialog(this, "No customers in the queue to remove.");
        }
    }

    private void updateDisplay() {
        parcelListArea.setText(parcelMap.toString());
        customerQueueArea.setText(queOfCustomers.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ParcelMap parcelMap = new ParcelMap();
                QueOfCustomers queOfCustomers = new QueOfCustomers();
                Worker worker = new Worker();

                // Sample Data for Parcels
                parcelMap.addParcel(new Parcel("X009", 9, 1, 9, 9, 7));
                parcelMap.addParcel(new Parcel("X020", 1, 1, 6, 4, 14));
                parcelMap.addParcel(new Parcel("X025", 7, 1, 4, 9, 9));
                parcelMap.addParcel(new Parcel("X036", 8, 4, 6, 9, 12));
                parcelMap.addParcel(new Parcel("X064", 8, 4, 1, 8, 15));
                parcelMap.addParcel(new Parcel("X045", 5, 3, 8, 5, 10));
                parcelMap.addParcel(new Parcel("X057", 3, 2, 6, 7, 8));
                parcelMap.addParcel(new Parcel("X068", 10, 4, 9, 8, 20));
                parcelMap.addParcel(new Parcel("X073", 2, 1, 4, 3, 5));
                parcelMap.addParcel(new Parcel("X082", 6, 5, 7, 5, 15));
                parcelMap.addParcel(new Parcel("X093", 4, 2, 5, 6, 12));
                parcelMap.addParcel(new Parcel("X104", 9, 3, 8, 6, 13));
                parcelMap.addParcel(new Parcel("X115", 7, 4, 6, 8, 18));
                parcelMap.addParcel(new Parcel("X126", 12, 5, 9, 10, 25));
                parcelMap.addParcel(new Parcel("X137", 6, 4, 8, 6, 10));
                parcelMap.addParcel(new Parcel("X148", 14, 6, 10, 8, 30));
                // Sample Data for Customers
                queOfCustomers.addCustomer(new Customer("Andrew Robertson", "X009"));
                queOfCustomers.addCustomer(new Customer("Ann Jones", "X020"));
                queOfCustomers.addCustomer(new Customer("Blair Foster", "X025"));
                queOfCustomers.addCustomer(new Customer("Bob Dawson", "X036"));
                queOfCustomers.addCustomer(new Customer("Chris Smith", "X064"));
                queOfCustomers.addCustomer(new Customer("David Brown", "X045"));
                queOfCustomers.addCustomer(new Customer("Emma White", "X057"));
                queOfCustomers.addCustomer(new Customer("Frank Miller", "X068"));
                queOfCustomers.addCustomer(new Customer("Grace Green", "X073"));
                queOfCustomers.addCustomer(new Customer("Harry Lee", "X082"));
                queOfCustomers.addCustomer(new Customer("Ivy Walker", "X093"));
                queOfCustomers.addCustomer(new Customer("Jack Thomas", "X104"));
                queOfCustomers.addCustomer(new Customer("Kathy Harris", "X115"));
                queOfCustomers.addCustomer(new Customer("Leo Martinez", "X126"));
                queOfCustomers.addCustomer(new Customer("Mia Robinson", "X137"));
                queOfCustomers.addCustomer(new Customer("Noah Clark", "X148"));

                ParcelGUI gui = new ParcelGUI(queOfCustomers, parcelMap, worker);
                gui.setVisible(true);
            }
        });
    }
}
