package in.handmademess.bakingapp.BakingFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import in.handmademess.bakingapp.Adapter.RecipeListAdapter;
import in.handmademess.bakingapp.IngredientsInfo;
import in.handmademess.bakingapp.R;
import in.handmademess.bakingapp.RecipeTitle;

/**
 * Created by Anup on 04-11-2017.
 */

public class RecipeListFragment extends Fragment {

    private static final String GET_RECIPES = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    RecipeListAdapter mAdapter;
    ArrayList<RecipeTitle> recipeList = new ArrayList<>();
    ArrayList<IngredientsInfo> ingredientsInfos = new ArrayList<>();

    public static int quantity[];
    public static  String[] measure,ingredient;

    public RecipeListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_list,container,false);
       get_recipes(rootView);
        return rootView;
    }

    public void get_recipes(final View rootView) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, GET_RECIPES, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                String resp = response.toString().trim();
                Log.d("Recipes", resp);

                //Parse id,title and servings info
                JSONArray json = null;
                try {
                    json = new JSONArray(resp);
                    for (int i = 0; i < json.length(); i++) {
                        JSONObject e = json.getJSONObject(i);

                        RecipeTitle recipeTitle = new RecipeTitle();
                        recipeTitle.setId(e.getInt("id"));
                        recipeTitle.setName(e.getString("name"));
                        recipeTitle.setServings(e.getInt("servings"));
                        recipeList.add(recipeTitle);

//                        IngredientsInfo ingredientsInfo = new IngredientsInfo();
//                        json = e.getJSONArray("ingredients");
//                        JSONObject jo = json.getJSONObject(i);
//                        ingredientsInfo.setQuantity(jo.getInt("quantity"));
//                        ingredientsInfo.setMeasure(jo.getString("measure"));
//                        ingredientsInfo.setIngredient(jo.getString("ingredient"));
//                        ingredientsInfos.add(ingredientsInfo);


                    }

                    mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recipeRecyclerView);
                    mLayoutManager = new LinearLayoutManager(getContext());
                    mRecyclerView.setLayoutManager(mLayoutManager);

                    mAdapter = new RecipeListAdapter(getContext(),recipeList);
                    mRecyclerView.setAdapter(mAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this.getContext());
        requestQueue.add(stringRequest);


    }
}
