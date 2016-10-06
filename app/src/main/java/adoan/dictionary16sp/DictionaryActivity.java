package adoan.dictionary16sp;

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

public class DictionaryActivity extends AppCompatActivity {
    private static final String[] WORDS = {
            "abate", "to lessen to subside",
            "abeyance", "suspended action",
            "abjure", "promise or swear to give up",
            "abrogate", "repeal or annul by authority",
            "abstruse", "difficult to comprehend obscure",
            "acarpous", "effete no longer fertile worn out",
            "accretion", "the growing of separate things into one",
            "agog", "eager/excited",
            "alloy", "to debase by mixing with something inferior",
            "amortize", "end (a debt) by setting aside money"
    };
    private HashMap<String, String> dictionary;
    private ArrayList<String> list;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> fiveDefns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

        dictionary = new HashMap<String, String>();
        list = new ArrayList<String>();
        for (int i = 0; i < WORDS.length; i += 2) {
            list.add(WORDS[i]);
            dictionary.put(WORDS[i], WORDS[i + 1]);
        }
        fiveDefns = new ArrayList<String>();

        pickRandomWords();
    }

    private void pickRandomWords() {


        ArrayList<String> fiveWords = new ArrayList<String>();
        Collections.shuffle(list);
        for (int i = 0; i < 5; i++) {
            fiveWords.add(list.get(i));
        }
        final String theWord = fiveWords.get(0);
        TextView theWordView = (TextView) findViewById(R.id.the_word);
        theWordView.setText(theWord);

        fiveDefns.clear();
        for (String word : fiveWords) {
            fiveDefns.add(dictionary.get(word));
        }
        Collections.shuffle(fiveDefns);

        // have to make adapter for ListView
        if (adapter == null) {
            adapter =
                    new ArrayAdapter<String>(this,
                            android.R.layout.simple_list_item_1,
                            fiveDefns);
        } else {
            adapter.notifyDataSetChanged();
        }


        ListView listView = (ListView) findViewById(R.id.word_list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String defnClicked = fiveDefns.get(position);
                String rightAnswer = dictionary.get(theWord);
                if (defnClicked.equals(rightAnswer)) {
                    Toast.makeText(DictionaryActivity.this, "You are awesome!", Toast.LENGTH_SHORT);
                } else {
                    Toast.makeText(DictionaryActivity.this, "YOU SUCK.", Toast.LENGTH_SHORT);
                }

                pickRandomWords();

                /*String definition = WORDS[position * 2 + 1];
                Log.d("list", "the user clicked item" + position);

                TextView defnView = (TextView) findViewById(R.id.definition);
                defnView.setText(definition);*/
            }
        });
    }

}
