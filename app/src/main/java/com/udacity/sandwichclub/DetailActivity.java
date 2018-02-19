package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView mIngredientsIv = findViewById(R.id.image_iv);


        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(mIngredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        // Could use data binding but follow your convention!
        TextView mAlsoKnownAsTextView = findViewById(R.id.also_known_tv);
        StringBuilder builder = new StringBuilder();
        for (String item : sandwich.getAlsoKnownAs()) {
             builder.append(item + ", ");
        }
        String alsoKnownAs = builder.toString();
        if (alsoKnownAs.length() > 0)
            alsoKnownAs = alsoKnownAs.substring(0, alsoKnownAs.length() - 2);
        mAlsoKnownAsTextView.setText(builder.toString());
        TextView mIngredientsTextView = findViewById(R.id.ingredients_tv);
        builder = new StringBuilder();
        for (String item : sandwich.getIngredients())
            builder.append(item + ", ");
        String ingredients = builder.toString();
        if (ingredients.length() > 0)
            ingredients = ingredients.substring(0, ingredients.length() - 2);
        mIngredientsTextView.setText(ingredients);
        TextView mPlaceOfOriginTextView = findViewById(R.id.origin_tv);
        mPlaceOfOriginTextView.setText(sandwich.getPlaceOfOrigin());
        TextView mDescriptionTextView = findViewById(R.id.description_tv);
        mDescriptionTextView.setText(sandwich.getDescription());
    }
}
