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
import in.handmademess.bakingapp.StepInfo;

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
    ArrayList<StepInfo> stepInfos = new ArrayList<>();
    JSONArray ingredientsArray = new JSONArray();

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
                JSONObject recipeObject= new JSONObject();

                try {
                    json = new JSONArray(resp);
                    for (int i = 0; i < json.length(); i++) {
                        recipeObject = json.getJSONObject(i);

                        RecipeTitle recipeTitle = new RecipeTitle();
                        recipeTitle.setId(recipeObject.getInt("id"));
                        recipeTitle.setName(recipeObject.getString("name"));
                        recipeTitle.setServings(recipeObject.getInt("servings"));
                        recipeList.add(recipeTitle);

                       ingredientsArray= recipeObject.getJSONArray("ingredients");

                        JSONObject ing =ingredientsArray.getJSONObject(i);
                        IngredientsInfo ingredientsInfo = new IngredientsInfo();
                        ingredientsInfo.setQuantity(ing.getInt("quantity"));
                        ingredientsInfo.setMeasure(ing.getString("measure"));
                        ingredientsInfo.setIngredient(ing.getString("ingredient"));
                        ingredientsInfos.add(ingredientsInfo);

                        IngredientsInfo ingredients = new IngredientsInfo(ing.getInt("quantity"),ing.getString("measure"),ing.getString("ingredient"));

                        JSONArray stepsArray = recipeObject.getJSONArray("steps");

                        JSONObject step = stepsArray.getJSONObject(i);
                        StepInfo stepInfo = new StepInfo();
                        stepInfo.setId(step.getInt("id"));
                        stepInfo.setShortDescription(step.getString("shortDescription"));
                        stepInfo.setDescription(step.getString("description"));
                        stepInfo.setVideoURL(step.getString("videoURL"));
                        stepInfo.setThumbnailURL(step.getString("thumbnailURL"));
                        stepInfos.add(stepInfo);

                    }

                    quantity = new int[ingredientsArray.length()];
                    measure = new String[ingredientsArray.length()];
                    ingredient = new String[ingredientsArray.length()];

                    for (int x=0; x< ingredientsArray.length(); x++)
                    {
                        JSONObject jo = ingredientsArray.getJSONObject(x);
                        quantity[x] = jo.getInt("quantity");
                        measure[x] = jo.getString("measure");
                        ingredient[x] = jo.getString("ingredient");
                        Log.d("ING",ingredient[x]);
                    }


                    ArrayList<IngredientsInfo> ingredients = prepareIngredients();

                    for (IngredientsInfo ing:ingredients
                         ) {
                        Log.d("AllIngredients",ing.getIngredient());

                    }


                    mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recipeRecyclerView);
                    mLayoutManager = new LinearLayoutManager(getContext());
                    mRecyclerView.setLayoutManager(mLayoutManager);

                    mAdapter = new RecipeListAdapter(getContext(),recipeList,ingredientsInfos,stepInfos);
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

    public ArrayList<IngredientsInfo> prepareIngredients() {
        ArrayList ingredient_ver = new ArrayList<>();
        if (ingredientsArray.length() == 0) {
            Log.d("NO_INGREDIENTS", "NO_INGREDIENTS");
        } else {
                for (int i = 0; i < ingredientsArray.length(); i++) {
                IngredientsInfo ingredientsInfo = new IngredientsInfo();
                ingredientsInfo.setQuantity(quantity[i]);
                ingredientsInfo.setMeasure(measure[i]);
                ingredientsInfo.setIngredient(ingredient[i]);

                ingredient_ver.add(ingredientsInfo);
            }
        }


        return ingredient_ver;
    }
}
