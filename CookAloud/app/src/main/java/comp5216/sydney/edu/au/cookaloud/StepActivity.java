package comp5216.sydney.edu.au.cookaloud;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
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

import java.util.ArrayList;
import java.util.Locale;

public class StepActivity extends AppCompatActivity
        implements RecognitionListener, TextToSpeech.OnInitListener {

    private SpeechRecognizer sr;
    TextToSpeech tts;
    String step = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Step " + Integer.toString(getIntent().getIntExtra("index", 0) + 1));
        toolbar.setTitleTextColor(Color.BLACK);
        setSupportActionBar(toolbar);

        tts = new TextToSpeech(this, this);

        sr = SpeechRecognizer.createSpeechRecognizer(this);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference recipeRef =
                database.getReference(
                        getString(R.string.Recipes))
                        .child(getIntent().getStringExtra(getString(R.string.name)));

        recipeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                TextView stepView = (TextView) findViewById(R.id.step);
                step = (String) dataSnapshot
                        .child(getString(R.string.steps))
                        .child(Integer.toString(getIntent().getIntExtra("index", 0)))
                        .getValue();
                stepView.setText(step);

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

    private void textToSpeech() {
        sr.destroy();
        tts.setLanguage(Locale.US);
        tts.speak(step, TextToSpeech.QUEUE_ADD, null, step);
        initialize();
    }

    private void initialize() {
        sr.setRecognitionListener(this);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (tts.isSpeaking()) {

                }
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);
                intent.putExtra(
                        RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(
                        RecognizerIntent.EXTRA_CALLING_PACKAGE, getApplication().getPackageName());
                sr.startListening(intent);
            }
        };

        runnable.run();
    }

    @Override
    public void onReadyForSpeech(Bundle params) {

    }

    @Override
    public void onBeginningOfSpeech() {

    }

    @Override
    public void onRmsChanged(float rmsdB) {

    }

    @Override
    public void onBufferReceived(byte[] buffer) {

    }

    @Override
    public void onEndOfSpeech() {

    }

    @Override
    public void onError(int error) {
        if (error == SpeechRecognizer.ERROR_NO_MATCH
                || error == SpeechRecognizer.ERROR_NETWORK_TIMEOUT) {
            initialize();
        } else {
            sr.destroy();
        }
    }

    @Override
    public void onResults(Bundle results) {
        analyseSpeechResults(results);
        initialize();
    }

    @Override
    public void onPartialResults(Bundle partialResults) {
        analyseSpeechResults(partialResults);
    }

    public void analyseSpeechResults(Bundle bundle) {
        ArrayList data = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        for (int i = 0; i < data.size(); i++)
        {
            String [] dataArray = data.get(i).toString().split(" ");
            for (int j = 0; dataArray.length > j; ++j) {
                if (dataArray[j].equals("next")) {
                    next();
                } else if (dataArray[j].equals("back")) {
                    back();
                } else if (dataArray[j].equals("speak")) {
                    textToSpeech();
                }
            }
        }
    }

    @Override
    public void onEvent(int eventType, Bundle params) {

    }

    private Bitmap getBitmapFromString(String encodedString) {
        encodedString = encodedString.replaceAll("\\s+","");
        byte [] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        return bitmap;
    }

    public void onNextClicked(View view) {
        next();
    }

    private void next() {
        sr.destroy();
        tts.shutdown();
        int count = getIntent().getIntExtra(getString(R.string.steps), -1);
        int index = getIntent().getIntExtra("index", -1);
        ++index;
        if (index >= count) {
            startRecipeActivity();
        } else {
            startStepActivity(count, index);
        }
    }

    public void onBackClicked(View view) {
        back();
    }

    private void back() {
        sr.destroy();
        tts.shutdown();
        int count = getIntent().getIntExtra(getString(R.string.steps), -1);
        int index = getIntent().getIntExtra("index", -1);
        --index;
        if (index < 0) {
            startRecipeActivity();
        } else {
            startStepActivity(count, index);
        }
    }

    private void startStepActivity(int count, int index) {
        Intent intent = new Intent(StepActivity.this, StepActivity.class);
        if (intent != null) {
            intent.putExtra(
                    getString(R.string.name), getIntent().getStringExtra(getString(R.string.name)));
            intent.putExtra(getString(R.string.steps), count);
            intent.putExtra("index", index);
            intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
            sr.destroy();
            startActivity(intent);
        }
    }

    private void startRecipeActivity() {
        Intent intent = new Intent(StepActivity.this, RecipeActivity.class);
        if (intent != null) {
            intent.putExtra(
                    getString(R.string.name), getIntent().getStringExtra(getString(R.string.name)));
            intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
            sr.destroy();
            startActivity(intent);
        }
    }

    @Override
    public void onInit(int status) {
        textToSpeech();
    }
}
