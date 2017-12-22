package comp5216.sydney.edu.au.cookaloud;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RecipeActivity extends AppCompatActivity {

    /**
     * A counter for the number of steps in a recipe.
     */
    private int stepCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String name = getIntent().getStringExtra(getString(R.string.name));
        setContentView(R.layout.activity_recipe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(name);
        toolbar.setTitleTextColor(Color.BLACK);
        setSupportActionBar(toolbar);

        DatabaseReference recipeRef = FirebaseDatabase.getInstance()
                .getReference(getString(R.string.Recipes))
                .child(name);

        recipeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String ingredientsString = "";
                boolean firstIngredient = true;
                for (DataSnapshot ingredient :
                        dataSnapshot.child(getString(R.string.ingredients)).getChildren()) {
                    if (!firstIngredient) {
                        ingredientsString += '\n';
                    } else {
                        firstIngredient = false;
                    }
                    ingredientsString += (String) ingredient.getValue();
                }
                TextView ingredients = (TextView) findViewById(R.id.ingredients);
                ingredients.setText(ingredientsString);

                String stepsString = "";
                stepCount = 1;
                for (DataSnapshot steps :
                        dataSnapshot.child(getString(R.string.steps)).getChildren()) {
                    stepsString += stepCount + ". " + steps.getValue() + '\n';
                    ++stepCount;
                }
                --stepCount;
                TextView steps = (TextView) findViewById(R.id.steps);
                steps.setText(stepsString);

                ImageView image = (ImageView) findViewById(R.id.image);
                image.setImageBitmap(getBitmapFromString(
                        (String) dataSnapshot.child(getString(R.string.image)).getValue()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

    }

    private Bitmap getBitmapFromString(String encodedString) {
        encodedString = encodedString.replaceAll("\\s+","");
        byte [] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
        Base64.encodeToString(encodeByte, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        return bitmap;
    }

    public void onStartCookingClicked(View view) {
        if (stepCount < 1) {
            return;
        }
        Intent intent = new Intent(RecipeActivity.this, StepActivity.class);
        if (intent != null) {
            intent.putExtra(
                    getString(R.string.name), getIntent().getStringExtra(getString(R.string.name)));
            intent.putExtra("steps", stepCount);
            intent.putExtra("index", 0);
            intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        }
    }

}
