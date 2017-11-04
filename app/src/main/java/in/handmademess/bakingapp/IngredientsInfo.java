package in.handmademess.bakingapp;

/**
 * Created by Anup on 04-11-2017.
 */

public class IngredientsInfo {
    public int quantity;
    public String measure,ingredient;
    private IngredientsInfo[] ingredients;


    public IngredientsInfo() {
    }

    public IngredientsInfo(int quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    public IngredientsInfo[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(IngredientsInfo[] ingredients) {
        this.ingredients = ingredients;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }
}
