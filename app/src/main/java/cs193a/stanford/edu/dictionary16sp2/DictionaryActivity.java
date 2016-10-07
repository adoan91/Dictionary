package cs193a.stanford.edu.dictionary16sp2;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

import stanford.androidlib.SimpleActivity;
import stanford.androidlib.SimpleList;

public class DictionaryActivity extends SimpleActivity {

//    private static final String[] WORDS = {
//            "abate", "to lessen to subside",
//            "abeyance", "suspended action",
//            "abjure", "promise or swear to give up",
//            "abrogate", "repeal or annul by authority",
//            "abstruse", "difficult to comprehend obscure",
//            "acarpous", "effete no longer fertile worn out",
//            "accretion", "the growing of separate things into one",
//            "agog", "eager/excited",
//            "alloy", "to debase by mixing with something inferior",
//            "amortize", "end (a debt) by setting aside money"
//    };


    private HashMap<String, String> dictionary;
    private ArrayList<String> list;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> fiveDefns;
    private String theWord;
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

        setTraceLifecycle(true); // Stanford Android Library

        // extract the 'firstword' parameter that was sent to me
        /*Intent intent = getIntent();
        String firstWord = intent.getStringExtra("firstWord");*/

        String firstWord = getStringExtra("firstWord");


        dictionary = new HashMap<String, String>();
        list = new ArrayList<String>();
        fiveDefns = new ArrayList<String>();

        readWordsFromFiles();

        pickRandomWords(firstWord);

        mp = MediaPlayer.create(this, R.raw.tbmt);
        //mp.start();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        /*private ArrayList<String> fiveDefns;
        private String theWord;*/

        outState.putString("theWord", theWord);
        outState.putStringArrayList("fiveDefns", fiveDefns);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState.containsKey("theWord") && savedInstanceState.containsKey("fiveDefns")) {
            theWord = savedInstanceState.getString("theWord",/* default */ "");
            fiveDefns = savedInstanceState.getStringArrayList("fiveDefns");

            Log.d("bundle", "theWord = " + theWord);
            Log.d("bundle", "fiveDefns = " + fiveDefns);

            showWhatIPicked();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Log.d("lifecycle", "onPause was called!");
        mp.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mp.start();
    }

    /*@Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() { // not really necessary
        super.onDestroy();
        mp.stop();
        mp.release();
    }*/

    private void readWordsFromFiles() {
        Scanner scan = new Scanner(getResources().openRawResource(R.raw.grewords));
        readWordsHelper(scan);
        try {
            Scanner scan2 = new Scanner(openFileInput("added_words.txt"));
            readWordsHelper(scan2);
        } catch (Exception e) {
            // lol
        }
    }

    private void readWordsHelper(Scanner scan) {
        //scan.useDelimiter("\t");
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            String[] parts = line.split("\t");
            if (parts.length >= 2) {
                String word = parts[0];
                String defn = parts[1];
                list.add(word);
                dictionary.put(word, defn);
            }
        }

       /*for (int i = 0; i < WORDS.length; i += 2) {
           list.add(WORDS[i]);
            dictionary.put(WORDS[i], WORDS[i + 1]);
        }*/

    }

    private void pickRandomWords(String firstWord) {
        ArrayList<String> fiveWords = new ArrayList<String>();
        Collections.shuffle(list);

        int wordsToAdd = 5;
        if (firstWord != null) {
            fiveWords.add(firstWord);
            wordsToAdd--;
        }
        for (int i = 0; i < wordsToAdd; i++) {
            fiveWords.add(list.get(i));
        }
        theWord = fiveWords.get(0);


        fiveDefns.clear();
        for (String word : fiveWords) {
            fiveDefns.add(dictionary.get(word));
        }
        Collections.shuffle(fiveDefns);


        showWhatIPicked();
        // have to make adapter for ListView
    }

     private void showWhatIPicked() {

        ListView listView = (ListView) findViewById(R.id.word_list);
        TextView theWordView = (TextView) findViewById(R.id.the_word);
        theWordView.setText(theWord);
        SimpleList.with(this)
                .setItems(listView, fiveDefns);

        /*if (adapter == null) {
            adapter =
                    new ArrayAdapter<String>(this,
                            android.R.layout.simple_list_item_1,
                            fiveDefns);
        } else {
            adapter.notifyDataSetChanged();
        }*/

        //listView.setAdapter(adapter);

        listView.setOnItemClickListener(this);/*new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String defnClicked = fiveDefns.get(position);
                String rightAnswer = dictionary.get(theWord);
                if (defnClicked.equals(rightAnswer)) {
                    //Toast.makeText(DictionaryActivity.this, "You are awesome!", Toast.LENGTH_SHORT).show();
                    toast("You are awesome!");
                } else {
                    //Toast.makeText(DictionaryActivity.this, "YOU SUCK.", Toast.LENGTH_SHORT).show();
                    toast("YOU SUCK");
                }

                pickRandomWords();

                *//*String definition = WORDS[position * 2 + 1];
                Log.d("list", "the user clicked item" + position);

                TextView defnView = (TextView) findViewById(R.id.definition);
                defnView.setText(definition);*//*
            }
        });*/
    }

    @Override
    public void onItemClick(ListView list, int index) {
        String defnClicked = fiveDefns.get(index);
        String rightAnswer = dictionary.get(theWord);
        if (defnClicked.equals(rightAnswer)) {
            //Toast.makeText(DictionaryActivity.this, "You are awesome!", Toast.LENGTH_SHORT).show();
            toast("You are awesome!");
        } else {
            //Toast.makeText(DictionaryActivity.this, "YOU SUCK.", Toast.LENGTH_SHORT).show();
            toast("YOU SUCK");
        }

        pickRandomWords(null);
    }
}
