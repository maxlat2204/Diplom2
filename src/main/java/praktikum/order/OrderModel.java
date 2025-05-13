package praktikum.order;

import java.util.List;

public class OrderModel {
    private List<String> ingredients;

    public OrderModel(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public OrderModel() {
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}
