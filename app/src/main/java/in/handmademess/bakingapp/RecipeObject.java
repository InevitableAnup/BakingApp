package in.handmademess.bakingapp;

/**
 * Created by Anup on 04-11-2017.
 */

public class RecipeObject {
    public int id;
    public String name,ingredients,steps;

    public RecipeObject() {
    }

    public RecipeObject(int id, String name, String ingredients, String steps) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    @Override
    public String toString() {
        return "RecipeObject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ingredients='" + ingredients + '\'' +
                ", steps='" + steps + '\'' +
                '}';
    }
}
