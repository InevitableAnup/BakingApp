package in.handmademess.bakingapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import in.handmademess.bakingapp.IngredientsInfo;
import in.handmademess.bakingapp.R;
import in.handmademess.bakingapp.RecipeTitle;

/**
 * Created by Anup on 04-11-2017.
 */

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<RecipeTitle> recipeList;
    private ArrayList<IngredientsInfo> ingredientsInfos;

    public RecipeListAdapter(Context mContext, ArrayList<RecipeTitle> recipeList) {
        this.mContext = mContext;
        this.recipeList = recipeList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.recipe_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        int id = recipeList.get(position).getId();
        final String name = recipeList.get(position).getName();
        int servings = recipeList.get(position).getServings();

//        final String ingredient = ingredientsInfos.get(position).getIngredient();

        holder.recipe_title.setText(name);
        holder.tv_servings.setText(String.valueOf(servings)+" Servings");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Ingredient : "+name, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView recipe_title,tv_servings;
        public ViewHolder(View itemView) {
            super(itemView);
            recipe_title = (TextView) itemView.findViewById(R.id.recipe_title);
            tv_servings = (TextView) itemView.findViewById(R.id.tv_servings);
        }
    }
}
