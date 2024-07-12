import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class CustomerSystemGUI {
    private JFrame frame;
    private JLabel lblCount;
    private int count = 0;
    private Queue<CustomerInformation> queue1;
    private Queue<CustomerInformation> queue2;
    private Queue<CustomerInformation> queue3;
    private Stack<CustomerInformation> completeStack;
    private JTextArea txtQueue1;
    private JTextArea txtQueue2;
    private JTextArea txtQueue3;

    public CustomerSystemGUI(Queue<CustomerInformation> queue1, Queue<CustomerInformation> queue2,
                             Queue<CustomerInformation> queue3, Stack<CustomerInformation> completeStack) {
        this.queue1 = queue1;
        this.queue2 = queue2;
        this.queue3 = queue3;
        this.completeStack = completeStack;
    }

    public void initGUI() {
        frame = new JFrame("Restaurant Customer Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        JPanel countPanel = new JPanel();
        lblCount = new JLabel("Count: 0", SwingConstants.CENTER);
        countPanel.add(lblCount);
        topPanel.add(countPanel);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 4)); // Increased to 4 columns for additional buttons
        JButton btnShowData = new JButton("Show Data");
        JButton btnAddCustomer = new JButton("Add New Customer");
        JButton btnAddData = new JButton("Add Data");
        JButton btnClearAll = new JButton("Clear All Data");

        buttonPanel.add(btnShowData);
        buttonPanel.add(btnAddCustomer);
        buttonPanel.add(btnAddData);
        buttonPanel.add(btnClearAll);
        topPanel.add(buttonPanel);

        frame.add(topPanel, BorderLayout.NORTH);

        JPanel countersPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        countersPanel.add(createCounterPanel("Counter 1", queue1, txtQueue1 = new JTextArea("Queue Contents:\n", 10, 20)));
        countersPanel.add(createCounterPanel("Counter 2", queue2, txtQueue2 = new JTextArea("Queue Contents:\n", 10, 20)));
        countersPanel.add(createCounterPanel("Counter 3", queue3, txtQueue3 = new JTextArea("Queue Contents:\n", 10, 20)));
        frame.add(countersPanel, BorderLayout.CENTER);

        JPanel recordPanel = new JPanel(new GridLayout(1, 3));
        JButton btnRecord1 = new JButton("Record Counter 1");
        JButton btnRecord2 = new JButton("Record Counter 2");
        JButton btnRecord3 = new JButton("Record Counter 3");

        recordPanel.add(btnRecord1);
        recordPanel.add(btnRecord2);
        recordPanel.add(btnRecord3);
        frame.add(recordPanel, BorderLayout.SOUTH);

        btnAddCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                count++;
                lblCount.setText("Count: " + count);

                String custIdInput = JOptionPane.showInputDialog(frame, "Enter Customer ID:");
                int custId = Integer.parseInt(custIdInput);

                String custName = JOptionPane.showInputDialog(frame, "Enter Customer Name:");

                String tableNumberInput = JOptionPane.showInputDialog(frame, "Enter Table Number:");
                int tableNumber = Integer.parseInt(tableNumberInput);

                String orderIdInput = JOptionPane.showInputDialog(frame, "Enter Order ID:");
                int orderId = Integer.parseInt(orderIdInput);

                String itemName = JOptionPane.showInputDialog(frame, "Enter Item Name:");

                String itemPriceInput = JOptionPane.showInputDialog(frame, "Enter Item Price:");
                double itemPrice = Double.parseDouble(itemPriceInput);

                String quantityInput = JOptionPane.showInputDialog(frame, "Enter Quantity:");
                int quantity = Integer.parseInt(quantityInput);

                String orderTime = JOptionPane.showInputDialog(frame, "Enter Order Time:");

                OrderInformation order = new OrderInformation(orderId, itemName, itemPrice, quantity, orderTime);
                CustomerInformation newCustomer = new CustomerInformation(custId, custName, tableNumber);
                newCustomer.addOrder(order);

                if (quantity <= 5) {
                    if (queue1.size() <= queue2.size()) {
                        queue1.add(newCustomer);
                    } else {
                        queue2.add(newCustomer);
                    }
                } else {
                    queue3.add(newCustomer);
                }

                updateTextArea(txtQueue1, queue1);
                updateTextArea(txtQueue2, queue2);
                updateTextArea(txtQueue3, queue3);

                // Write to customerList.txt
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("customerList.txt", true))) {
                    writer.write(newCustomer.toString());
                    writer.newLine();
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error writing to customerList.txt");
                }
            }
        });

        btnShowData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showData();
            }
        });

        btnAddData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addDataFromFile();
            }
        });

        btnClearAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                queue1.clear();
                queue2.clear();
                queue3.clear();
                completeStack.clear();
                count = 0;
                lblCount.setText("Count: 0");
                updateTextArea(txtQueue1, queue1);
                updateTextArea(txtQueue2, queue2);
                updateTextArea(txtQueue3, queue3);
            }
        });

        btnRecord1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recordQueue(queue1, txtQueue1, "Counter 1");
            }
        });

        btnRecord2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recordQueue(queue2, txtQueue2, "Counter 2");
            }
        });

        btnRecord3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recordQueue(queue3, txtQueue3, "Counter 3");
            }
        });

        frame.setVisible(true);
    }

    private JPanel createCounterPanel(String counterTitle, Queue<CustomerInformation> queue, JTextArea txtQueue) {
        JPanel counterPanel = new JPanel(new BorderLayout());

        JLabel lblTitle = new JLabel(counterTitle, SwingConstants.CENTER);
        counterPanel.add(lblTitle, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(txtQueue);
        counterPanel.add(scrollPane, BorderLayout.CENTER);

        JButton btnNextQueue = new JButton("Next Queue");
        JButton btnClearQueue = new JButton("Clear Queue");

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(btnNextQueue);
        buttonPanel.add(btnClearQueue);

        counterPanel.add(buttonPanel, BorderLayout.SOUTH);

        btnNextQueue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!queue.isEmpty()) {
                    CustomerInformation customer = queue.poll();
                    txtQueue.append(customer.toString() + "\n\n");
                    completeStack.push(customer);
                } else {
                    JOptionPane.showMessageDialog(frame, counterTitle + " is empty!");
                }
            }
        });

        btnClearQueue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtQueue.setText("Queue Contents:\n");
                queue.clear();
                updateCountLabel(); // Update the customer count label after clearing
            }
        });

        return counterPanel;
    }

    private void addDataFromFile() {
        int addedCustomers = 0;
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
                    addedCustomers++;
                }
            }
            JOptionPane.showMessageDialog(frame, "Added " + addedCustomers + " customers from file.");
            updateCountLabel();
            updateTextArea(txtQueue1, queue1);
            updateTextArea(txtQueue2, queue2);
            updateTextArea(txtQueue3, queue3);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error reading from customerList.txt");
        }
    }

    private void showData() {
        JFrame dataFrame = new JFrame("All Customer Data");
        dataFrame.setSize(600, 400);
        dataFrame.setLocationRelativeTo(frame);

        JTextArea txtData = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(txtData);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        dataFrame.add(scrollPane);

        StringBuilder sb = new StringBuilder();

        // Read from customerList.txt and append data to the StringBuilder
        try (BufferedReader reader = new BufferedReader(new FileReader("customerList.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error reading from customerList.txt");
        }

        txtData.setText(sb.toString());
        dataFrame.setVisible(true);
    }

    private void updateTextArea(JTextArea textArea, Queue<CustomerInformation> queue) {
        textArea.setText("Queue Contents:\n");
        for (CustomerInformation customer : queue) {
            textArea.append(customer.toString() + "\n\n");
        }
    }

    private void recordQueue(Queue<CustomerInformation> queue, JTextArea textArea, String counterName) {
        if (queue.isEmpty()) {
            JOptionPane.showMessageDialog(frame, counterName + " is empty!");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("================== ").append(counterName).append(" ==================\n");
        for (CustomerInformation customer : queue) {
            sb.append(customer.toString()).append("\n\n");
            completeStack.push(customer);
        }
        sb.append("===================================================\n");

        // Display the recorded data in the text area
        textArea.setText(sb.toString());

        // Print the recorded data to the console
        System.out.println(sb.toString());

        queue.clear(); // Clear the queue after recording
        updateCountLabel(); // Update the customer count label after clearing
    }

    private void updateCountLabel() {
        int totalCustomers = queue1.size() + queue2.size() + queue3.size();
        lblCount.setText("Count: " + totalCustomers);
    }

    public static void main(String[] args) {
        Queue<CustomerInformation> queue1 = new LinkedList<>();
        Queue<CustomerInformation> queue2 = new LinkedList<>();
        Queue<CustomerInformation> queue3 = new LinkedList<>();
        Stack<CustomerInformation> completeStack = new Stack<>();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                CustomerSystemGUI gui = new CustomerSystemGUI(queue1, queue2, queue3, completeStack);
                gui.initGUI();
            }
        });
    }
}
