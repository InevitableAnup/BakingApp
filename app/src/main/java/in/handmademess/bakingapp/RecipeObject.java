package in.handmademess.bakingapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Anup on 04-11-2017.
 */

public class RecipeObject implements Parcelable {
    public int id,serving;
    public String name,ingredients,steps;

    public RecipeObject() {
    }

    public RecipeObject(int id, int serving, String name, String ingredients, String steps) {
        this.id = id;
        this.serving = serving;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    public int getServing() {
        return serving;
    }

    public void setServing(int serving) {
        this.serving = serving;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.serving);
        dest.writeString(this.name);
        dest.writeString(this.ingredients);
        dest.writeString(this.steps);
    }

    protected RecipeObject(Parcel in) {
        this.id = in.readInt();
        this.serving = in.readInt();
        this.name = in.readString();
        this.ingredients = in.readString();
        this.steps = in.readString();
    }

    public static final Parcelable.Creator<RecipeObject> CREATOR = new Parcelable.Creator<RecipeObject>() {
        @Override
        public RecipeObject createFromParcel(Parcel source) {
            return new RecipeObject(source);
        }

        @Override
        public RecipeObject[] newArray(int size) {
            return new RecipeObject[size];
        }
    };
}
