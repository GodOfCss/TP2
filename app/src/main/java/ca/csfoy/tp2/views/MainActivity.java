package ca.csfoy.tp2.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import ca.csfoy.tp2.R;
import ca.csfoy.tp2.database.GoDatabaseFactory;
import ca.csfoy.tp2.database.GoMove;

public class MainActivity extends AppCompatActivity {

    private GoDatabaseFactory databaseFactory;
    private SQLiteDatabase database;
    private Button playButton;
    private Button replayButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.playButton = (Button) findViewById(R.id.playButton);
        this.replayButton = (Button) findViewById(R.id.replayButton);
        this.databaseFactory = new GoDatabaseFactory(this);
        this.database = databaseFactory.getReadableDatabase();

        playButton.setOnClickListener(this::onPlayButton);
        replayButton.setOnClickListener(this::onReplayButton);
    }

    public void onPlayButton(View view){
        database.execSQL(GoDatabaseFactory.DROP_TABLE_SQL);

        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }
    public void onReplayButton(View view){

        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("SAVE_LIST", retrieveGoMoves());

        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        databaseFactory.close();
    }

    public ArrayList<GoMove> retrieveGoMoves()
    {
        GoMove goMove = null;
        ArrayList<GoMove> goMoveList = new ArrayList<GoMove>();
        Cursor cursor = null;
        try
        {
            database.beginTransaction();

            cursor = database.rawQuery(GoDatabaseFactory.SELECT_ALL_OF_SQL, new String[]{});
            cursor.moveToFirst();
            do {
                goMove = new GoMove();
                goMove.setColor(cursor.getString(1));
                goMove.setPosition(cursor.getString(2));

                goMoveList.add(goMove);
            } while (cursor.moveToNext());

            database.setTransactionSuccessful();
        }
        catch(RuntimeException e)
        {
            Snackbar.make(this, replayButton, e.getMessage(), Snackbar.LENGTH_LONG).show();
        }
        finally
        {
            if (cursor != null) {
                cursor.close();
            }
            database.endTransaction();
        }

        return goMoveList;
    }
}