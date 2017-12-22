package comp5216.sydney.edu.au.cookaloud;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecipeListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> names;
    private final ArrayList<String> durations;
    private final ArrayList<String> images;

    public RecipeListAdapter(
            Activity context,
            ArrayList<String> names,
            ArrayList<String> durations,
            ArrayList<String> images) {
        super(context, R.layout.recipe_list_item, names);

        this.context = context;
        this.names = names;
        this.images = images;
        this.durations = durations;
    }

    public String getKey(int position) {
        return names.get(position);
    }

    private Bitmap getBitmapFromString(String encodedString) {
        encodedString = encodedString.replaceAll("\\s+","");
        byte [] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        return bitmap;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.recipe_list_item, null,true);

        TextView name = rowView.findViewById(R.id.name);
        TextView duration = rowView.findViewById(R.id.duration);
        ImageView image = rowView.findViewById(R.id.image);

        name.setText(names.get(position));
        duration.setText(durations.get(position));

        name.setText(names.get(position));
        duration.setText(durations.get(position));
        image.setImageBitmap(getBitmapFromString(images.get(position)));
        return rowView;

    };
}
