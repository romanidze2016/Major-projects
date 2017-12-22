package comp5216.sydney.edu.au.cookaloud;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {

    /**
     * ListView for the list of recipes.
     */
    private ListView recipeListView;
    /**
     * Adapter for the list of recipes. See RecipeListAdapter for more info.
     */
    private RecipeListAdapter recipesAdapter;
    /**
     * Array of recipe names, ie "Eggplant Pizza".
     */
    private ArrayList<String> names;
    /**
     * Array of recipe durations, ie "40 mins".
     */
    private ArrayList<String> durations;
    /**
     * Array of recipe images. Images are stored on the Firebase database as Base64 images. They
     * must be decoded to bitmaps for use.
     */
    private ArrayList<String> images;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.setTitle("CookAloud");
        toolbar.setTitleTextColor(Color.BLACK);
//        setSupportActionBar(toolbar);

        recipeListView = (ListView) findViewById(R.id.recipeList);

        // Initialise arrays.
        names = new ArrayList<>();
        durations = new ArrayList<>();
        images = new ArrayList<>();

        //recipeListView.setBackgroundColor(Color.);
        recipesAdapter = new RecipeListAdapter(this, names, durations, images);

        // Set the listView with the adapter.
        recipeListView.setAdapter(recipesAdapter);

        initialiseFirebase();
        initialiseRecipeClickedListener();
    }

    /**
     *  In order to access data from Firebase a ValueEventListener must be added to the data.
     */
    private void initialiseFirebase() {
        // Get reference to Recipes node of the Firebase database.
        DatabaseReference recipesRef =
                FirebaseDatabase.getInstance().getReference(getString(R.string.Recipes));

        recipesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get the name, duration and image for each recipe.
                for (DataSnapshot recipe : dataSnapshot.getChildren()) {

                    String name = recipe.getKey();
                    String duration = (String) recipe.child(getString(R.string.duration)).getValue() + "  ";
                    String image = (String) recipe.child(getString(R.string.image)).getValue();

                    int index = names.indexOf(name);

                    if (index == -1) {
                        names.add(name);
                        durations.add(duration);
                        images.add(image);
                    } else {
                        durations.remove(index);
                        durations.add(duration);
                        images.remove(index);
                        images.add(image);
                    }
                }
                recipesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "Read failed: " + databaseError.getCode());
            }
        });
    }

    private void initialiseRecipeClickedListener() {
        // Open  a recipe when it is clicked in the recipe listView.
        recipeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, RecipeActivity.class);
                if (intent != null) {
                    // Add the name of the recipe to the intent. This is so that the RecipeActivity
                    // can find the recipe on Firebase.
                    intent.putExtra(getString(R.string.name), recipesAdapter.getKey(position));
                    intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(intent);
                }
            }
        });
    }
}
