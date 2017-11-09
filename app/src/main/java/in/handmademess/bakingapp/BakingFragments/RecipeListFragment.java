package in.handmademess.bakingapp.BakingFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

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
import java.util.HashMap;

import in.handmademess.bakingapp.R;
import in.handmademess.bakingapp.RecipeObject;

/**
 * Created by Anup on 04-11-2017.
 */

public class RecipeListFragment extends Fragment implements AdapterView.OnItemClickListener {

    private static final String GET_RECIPES = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    ListView lv_recipe;
    RecipeObject recipe;


    public RecipeListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_list,container,false);
        lv_recipe = (ListView) rootView.findViewById(R.id.lv_recipe);
        lv_recipe.setOnItemClickListener(this);

        get_recipe();

        return rootView;
    }

    public void get_recipe(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, GET_RECIPES, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String resp = response.toString().trim();
                Log.d("Recipes ",resp);
                try {
                    JSONArray recipeArray = new JSONArray(resp);
                    ArrayList recipeList = new ArrayList();

                    for (int i=0;i< recipeArray.length(); i++)
                    {
                        JSONObject recipeObject = recipeArray.getJSONObject(i);
                        int id =recipeObject.getInt("id");
                        String name =recipeObject.getString("name");
                        String ingredients =recipeObject.getString("ingredients");
                        String steps =recipeObject.getString("steps");

                        recipe = new RecipeObject(id,name,ingredients,steps);

                        String ing = recipe.getIngredients();
                        Log.d("Recipe Id", String.valueOf(recipe.getId())+ ing);

                        HashMap<String,String> recipeMap = new HashMap<>();
                        recipeMap.put("id", String.valueOf(id));
                        recipeMap.put("name",name);
                        recipeMap.put("ingredients",ingredients);
                        recipeMap.put("steps",steps);

                        recipeList.add(recipeMap);



                    }

                    ListAdapter adapter = new SimpleAdapter(getActivity(), recipeList,
                            R.layout.list_item, new String[]{ "id","name","ingredients","steps"},
                            new int[]{R.id.tv_serving, R.id.recipeName, R.id.tv_ingredients, R.id.tv_steps});
                    lv_recipe.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        TextView ingredients = (TextView) view.findViewById(R.id.tv_ingredients);
        TextView steps = (TextView) view.findViewById(R.id.tv_steps);
        String ingredientList = ingredients.getText().toString();
        String stepsList = steps.getText().toString();

        Bundle bundle = new Bundle();
        bundle.putString("ingredients",ingredientList);
        bundle.putString("steps",stepsList);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        RecipeStepListFragment recipeStepListFragment = new RecipeStepListFragment();
        recipeStepListFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.recipeListContainer,recipeStepListFragment);
        fragmentTransaction.addToBackStack("RecipeSteps");
        fragmentTransaction.commit();



    }
}
