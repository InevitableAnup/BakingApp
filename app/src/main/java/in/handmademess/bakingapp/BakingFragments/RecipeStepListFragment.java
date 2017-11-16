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
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import in.handmademess.bakingapp.Adapter.DataItemAdapter;
import in.handmademess.bakingapp.Model.IngredientsObject;
import in.handmademess.bakingapp.Model.StepsObject;
import in.handmademess.bakingapp.R;
import in.handmademess.bakingapp.RecipeObject;

/**
 * Created by Anup on 09-11-2017.
 */

public class RecipeStepListFragment extends Fragment implements AdapterView.OnItemClickListener {

    public RecipeStepListFragment() {
    }

    ListView lv_ingredients, lv_steps;
    ToggleButton tb_ingredients;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_steps, container, false);

        Bundle bundle = getArguments();
        RecipeObject recipeObject = bundle.getParcelable(DataItemAdapter.RECIPE_INFO);
        if (recipeObject != null) {
            Toast.makeText(getActivity(), "Recieved Item : " + recipeObject.getName(), Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getActivity(), "Didn't recieve any data", Toast.LENGTH_SHORT).show();
        }
        String ingredientsList = recipeObject.getIngredients();
        String stepList = recipeObject.getSteps();
        lv_ingredients = (ListView) rootView.findViewById(R.id.lv_ingredients);
        lv_steps = (ListView) rootView.findViewById(R.id.lv_steps);
        lv_steps.setOnItemClickListener(this);

        tb_ingredients = (ToggleButton) rootView.findViewById(R.id.tb_ingredients);
        tb_ingredients.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                {
                    lv_ingredients.setVisibility(View.VISIBLE);

                }else{
                    lv_ingredients.setVisibility(View.GONE);
                }
            }
        });

        Log.d("Steps",stepList);

        displayIngredients(ingredientsList);
        displaySteps(stepList);

        return rootView;
    }

    private void displayIngredients(String ingredientsList) {
        try {
            JSONArray recipeArray = new JSONArray(ingredientsList);
            ArrayList ingredientList = new ArrayList();

            for (int i = 0; i < recipeArray.length(); i++) {
                JSONObject recipeObject = recipeArray.getJSONObject(i);
                double quantity = recipeObject.getDouble("quantity");
                String measure = recipeObject.getString("measure");
                String ingredients = recipeObject.getString("ingredient");


                IngredientsObject ingredientsObject = new IngredientsObject(quantity, measure, ingredients);

                double qty = ingredientsObject.getQuantity();
                String ingMeasure = ingredientsObject.getMeasure();
                String ingredient = ingredientsObject.getIngredient();
                Log.d("Measure", ingMeasure);

                HashMap<String, String> ingredientMap = new HashMap<>();
                ingredientMap.put("quantity", String.valueOf(quantity));
                ingredientMap.put("measure", measure);
                ingredientMap.put("ingredient", ingredients);

                ingredientList.add(ingredientMap);


            }

            ListAdapter adapter = new SimpleAdapter(getActivity(), ingredientList,
                    R.layout.list_ingredients, new String[]{"quantity", "measure", "ingredient"},
                    new int[]{R.id.tv_qty, R.id.tv_measure, R.id.tv_ingredient});
            lv_ingredients.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void displaySteps(String stepsList) {
        try {
            JSONArray recipeArray = new JSONArray(stepsList);
            ArrayList stepList = new ArrayList();

            for (int i = 0; i < recipeArray.length(); i++) {
                JSONObject recipeObject = recipeArray.getJSONObject(i);
                int id = recipeObject.getInt("id");
                String shortDescription = recipeObject.getString("shortDescription");
                String description = recipeObject.getString("description");
                String videoURL = recipeObject.getString("videoURL");
                String thumbnailURL = recipeObject.getString("thumbnailURL");


                StepsObject stepsObject = new StepsObject(id, shortDescription, description, videoURL, thumbnailURL);

                int ID = stepsObject.getId();
                String sd = stepsObject.getShortDescription();
                String desc = stepsObject.getDescription();
                String vurl = stepsObject.getVideoURL();
                String turl = stepsObject.getThumbnailUrl();
                Log.d("desc", desc);

                HashMap<String, String> stepsMap = new HashMap<>();
                stepsMap.put("id", String.valueOf(id));
                stepsMap.put("shortDescription", shortDescription);
                stepsMap.put("description", description);
                stepsMap.put("videoURL", videoURL);
                stepsMap.put("thumbnailURL", thumbnailURL);

                stepList.add(stepsMap);


            }

            ListAdapter adapter = new SimpleAdapter(getActivity(), stepList,
                    R.layout.list_steps, new String[]{"id", "shortDescription", "description", "videoURL", "thumbnailURL"},
                    new int[]{R.id.tv_id, R.id.tv_shortdescription, R.id.tv_description, R.id.tv_videoURL, R.id.tv_thumbnailURL});
            lv_steps.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        TextView description = (TextView) view.findViewById(R.id.tv_description);
        TextView videoUrl = (TextView) view.findViewById(R.id.tv_videoURL);
        String stepDescription = description.getText().toString();
        String videoLink = videoUrl.getText().toString();

        Bundle bundle = new Bundle();
        bundle.putString("stepDescription", stepDescription);
        bundle.putString("videoLink", videoLink);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        RecipeDetailsFragment recipeDetailsFragment = new RecipeDetailsFragment();
        recipeDetailsFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.recipeListContainer, recipeDetailsFragment);
        fragmentTransaction.addToBackStack("RecipeDetails");
        fragmentTransaction.commit();

    }
}
