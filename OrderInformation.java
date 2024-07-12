public class OrderInformation 
{
    private int orderId;
    private String itemName;
    private double itemPrice;
    private int quantity;
    private String orderTime;

    public OrderInformation(int orderId, String itemName, double itemPrice, int quantity, String orderTime) {
        this.orderId = orderId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.quantity = quantity;
        this.orderTime = orderTime;
    }

    @Override
    public String toString() {
        return "\nOrder ID: " + orderId + "\nItem Name: " + itemName + "\nItem Price: " + itemPrice + "\nQuantity: " + quantity + "\nOrder Time: " + orderTime;
    }
}
