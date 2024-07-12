import java.util.ArrayList;
import java.util.List;

public class CustomerInformation {
    private int custId;
    private String custName;
    private int tableNumber;
    private List<OrderInformation> orders;

    public CustomerInformation(int custId, String custName, int tableNumber) {
        this.custId = custId;
        this.custName = custName;
        this.tableNumber = tableNumber;
        this.orders = new ArrayList<>();
    }

    public void addOrder(OrderInformation order) {
        orders.add(order);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("==============================\n");
        sb.append("Customer ID: ").append(custId).append("\n");
        sb.append("Customer Name: ").append(custName).append("\n");
        sb.append("Table Number: ").append(tableNumber).append("\n");
        for (OrderInformation order : orders) {
            sb.append(order.toString()).append("\n");
        }
        return sb.toString();
    }
}
