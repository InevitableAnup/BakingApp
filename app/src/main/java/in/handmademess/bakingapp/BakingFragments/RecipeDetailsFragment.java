package in.handmademess.bakingapp.BakingFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import in.handmademess.bakingapp.R;

/**
 * Created by Anup on 04-11-2017.
 */

public class RecipeDetailsFragment extends Fragment {

    TextView tv_videoLink,tv_stepDescription;
    public RecipeDetailsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details,container,false);

        tv_stepDescription = (TextView) rootView.findViewById(R.id.tv_stepDescription);
        tv_videoLink = (TextView) rootView.findViewById(R.id.tv_videoLink);

        Bundle bundle =getArguments();
        String stepDescription =bundle.getString("stepDescription");
        String videoLink = bundle.getString("videoLink");

        tv_stepDescription.setText(stepDescription);
        tv_videoLink.setText(videoLink);

        return rootView;
    }
}
