package cs193a.stanford.edu.dictionary16sp2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import stanford.androidlib.SimpleActivity;

public class MainMenuActivity extends SimpleActivity {
    private static final int ADD_WORD_ACTIVITY_CODE = 12345;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        setTraceLifecycle(true);
    }

    public void playGameClick(View view) {
        Intent intent = new Intent(this, DictionaryActivity.class);
        String firstWord = "abate"; // scurvy
        intent.putExtra("firstWord", firstWord);
        startActivity(intent);

        //startActivity(DictionaryActivity.class, "firstWord", firstWord);
    }

    public void addWordClick(View view) {
        Intent intent = new Intent(this, AddWordActivity.class);
        startActivityForResult(intent, ADD_WORD_ACTIVITY_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == ADD_WORD_ACTIVITY_CODE) {
            // I'm coming back from AddWordActivity
            String word = intent.getStringExtra("word");
            String defn = intent.getStringExtra("definition");

            toast("You want to add the word " + word +", with definition " + defn);
        }
    }
}
