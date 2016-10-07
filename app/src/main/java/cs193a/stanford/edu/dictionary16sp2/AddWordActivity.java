package cs193a.stanford.edu.dictionary16sp2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.PrintStream;

import stanford.androidlib.SimpleActivity;

public class AddWordActivity extends SimpleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTraceLifecycle(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);
    }

    public void okClick(View view) {
        EditText wordTextBox = (EditText) findViewById(R.id.new_word);
        EditText defnTextBox = (EditText) findViewById(R.id.new_defn);
        String word = wordTextBox.getText().toString();
        String defn = defnTextBox.getText().toString();

        // write the word/defn to the file
        PrintStream output = new PrintStream(openFileOutput("added_words.txt", MODE_APPEND));
        output.println(word + "\t" + defn);
        output.close();

        Intent intent = new Intent();
        intent.putExtra("word", word);
        intent.putExtra("definition", defn);
        setResult(RESULT_OK, intent);
        finish();

        //finish("word", word, "definition", defn);
    }
}
