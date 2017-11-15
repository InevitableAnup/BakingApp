package in.handmademess.bakingapp.Adapter;

/**
 * Created by Anup on 15-11-2017.
 */

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import in.handmademess.bakingapp.BakingFragments.RecipeStepListFragment;
import in.handmademess.bakingapp.MainActivity;
import in.handmademess.bakingapp.R;
import in.handmademess.bakingapp.RecipeObject;

public class DataItemAdapter extends RecyclerView.Adapter<DataItemAdapter.ViewHolder> {

    public static final String RECIPE_INFO = "recipe_info";
    private List<RecipeObject> mItems;
    private Context mContext;

    public DataItemAdapter(Context context, List<RecipeObject> items) {
        this.mContext = context;
        this.mItems = items;
    }

    @Override
    public DataItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DataItemAdapter.ViewHolder holder, int position) {
        final RecipeObject item = mItems.get(position);

        holder.tvName.setText(item.getName());
        holder.tvServing.setText(String.valueOf(item.getServing()));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(mContext, "You selected "+item.getName(), Toast.LENGTH_SHORT).show();

                String itemId = String.valueOf(item.getId());

                FragmentManager fragmentManager = ((AppCompatActivity)mContext).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putParcelable(RECIPE_INFO, item);
                RecipeStepListFragment recipeStepListFragment = new RecipeStepListFragment();
                recipeStepListFragment.setArguments(bundle);

                fragmentTransaction.replace(R.id.recipeListContainer,recipeStepListFragment);
                fragmentTransaction.addToBackStack("RecipeSteps");
                fragmentTransaction.commit();
            }
        });



    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName,tvServing;
        public View mView;
        public ViewHolder(View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.recipeName);
            tvServing = (TextView) itemView.findViewById(R.id.tv_serving);
            mView = itemView;

        }
    }
}