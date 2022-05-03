package FoodDelivery;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Order implements Comparable<Order>{
    private final User user;
    private final Restaurant restaurant;
    private final Date placementDate;
    private final List<Dish> dishes;
    private final double total;
    private Courier courier;

    Order(User user, Restaurant restaurant, List<Dish> dishes, double total) {
        this.user = user.copy();
        this.restaurant = restaurant.copy();
        this.placementDate = new Date(System.currentTimeMillis());
        this.dishes = dishes.stream().map(Dish::copy).collect(Collectors.toList());
        this.total = total;
        this.courier = null;
    }

    private Order(Order order) {
        this.user = order.user.copy();
        this.restaurant = order.restaurant.copy();
        this.placementDate = new Date(order.placementDate.getTime());
        this.dishes = order.dishes.stream().map(Dish::copy).collect(Collectors.toList());
        this.total = order.total;
        this.courier = order.courier == null ? null : order.courier.copy();
    }

    Order copy() {
        return new Order(this);
    }

    void assignCourier(Courier courier) {
        this.courier = courier.copy();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Double.compare(order.total, total) == 0 &&
                Objects.equals(user, order.user) &&
                Objects.equals(restaurant, order.restaurant) &&
                Objects.equals(placementDate, order.placementDate) &&
                Objects.equals(dishes, order.dishes) &&
                Objects.equals(courier, order.courier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, restaurant, placementDate, dishes, total, courier);
    }

    String toBriefString() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        return restaurant.getName() + " order from " + timeFormat.format(placementDate)
                + "\n\t Total: $" + String.format("%.2f", total);

    }

    @Override
    public String toString() {
        String courier = "No courier has been assigned yet.";
        if (this.courier != null) {
            courier = this.courier.toString();
        }
        SimpleDateFormat timeFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        return restaurant.getName() + " order from " + timeFormat.format(placementDate) + "\n" +
                user + "\n" +
                courier + "\n" +
                IntStream.range(0, dishes.size())
                        .mapToObj(i -> (i + 1) + ". " + dishes.get(i) + "\n")
                        .collect(Collectors.joining()) +
                "\t Total: $" + String.format("%.2f", total);
    }

    @Override
    public int compareTo(Order order) {
        return (int) (this.placementDate.getTime() - order.placementDate.getTime());
    }
}
