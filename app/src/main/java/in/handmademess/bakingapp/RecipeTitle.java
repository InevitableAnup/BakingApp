package in.handmademess.bakingapp;

/**
 * Created by Anup on 04-11-2017.
 */

public class RecipeTitle {
    public int id;
    public String name;
    public int servings;

    public RecipeTitle() {
    }


    public RecipeTitle(int id, String name, int servings) {
        this.id = id;
        this.name = name;
        this.servings = servings;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
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
}
