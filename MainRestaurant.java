import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class MainRestaurant {
    private Queue<CustomerInformation> queue1;
    private Queue<CustomerInformation> queue2;
    private Queue<CustomerInformation> queue3;
    private Stack<CustomerInformation> completeStack;

    public MainRestaurant() {
        queue1 = new LinkedList<>();
        queue2 = new LinkedList<>();
        queue3 = new LinkedList<>();
        completeStack = new Stack<>();

        loadDataFromFile();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CustomerSystemGUI(queue1, queue2, queue3, completeStack).initGUI();
            }
        });
    }

    private void loadDataFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("customerList.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("; ");
                if (data.length == 8) {
                    int custId = Integer.parseInt(data[0]);
                    String custName = data[1];
                    int tableNumber = Integer.parseInt(data[2]);
                    int orderId = Integer.parseInt(data[3]);
                    String itemName = data[4];
                    double itemPrice = Double.parseDouble(data[5]);
                    int quantity = Integer.parseInt(data[6]);
                    String orderTime = data[7];

                    OrderInformation order = new OrderInformation(orderId, itemName, itemPrice, quantity, orderTime);
                    CustomerInformation customer = new CustomerInformation(custId, custName, tableNumber);
                    customer.addOrder(order);

                    if (quantity <= 5) {
                        if (queue1.size() <= queue2.size()) {
                            queue1.add(customer);
                        } else {
                            queue2.add(customer);
                        }
                    } else {
                        queue3.add(customer);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading customerList.txt: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new MainRestaurant();
    }
}
