import java.util.List;

public class Records {

    record ItemLine(String item, int count){
        // compact constructor
        ItemLine{
            // validate arguments
            if (count <= 0){
                throw new IllegalArgumentException("Item count must be greater than zero.");
            }
        }
    }
    record ShoppingCart(List<ItemLine> products){

        // canonical constructor
        ShoppingCart(List<ItemLine> products){
            // defensive copy
            this.products = List.copyOf(products);
        }
    }
    public static void main(String[] args) {
        try {
            ItemLine tv = new ItemLine("tv", 0);
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }

        ItemLine tv = new ItemLine("tv", 1);
        List<ItemLine> itemLines = List.of(tv);
        ShoppingCart cart = new ShoppingCart(itemLines);

        System.out.println(cart.products());
    }
}