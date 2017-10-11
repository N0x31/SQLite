package fr.wcs.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import static android.content.ContentValues.TAG;
import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("fr.wcs.sqlite", appContext.getPackageName());
    }

    //Test écriture

    @Test
    public void write() throws Exception {
        // Context of the app under test.
        Context context = InstrumentationRegistry.getTargetContext();
        context.deleteDatabase(DatabaseContract.UserEntry.TABLE_NAME);
        TweetDbHelper dBHelper = new TweetDbHelper(context);

        // Gets the data repository in write mode
        SQLiteDatabase db = dBHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys

        //User
        ContentValues user = new ContentValues();
        user.put(DatabaseContract.UserEntry.COLUMN_NAME_NAME, "User name");
        user.put(DatabaseContract.UserEntry.COLUMN_NAME_EMAIL, "name@wcs.com");

        // Insert the new row, returning the primary key value of the new row
        long newUserId = db.insert(DatabaseContract.UserEntry.TABLE_NAME, null, user);

        /*Pour vérifier qu'il n'y a pas eu d'erreur à l'écriture il suffit de vérifier
        que newUserId est différent de -1*/
        assertNotEquals(-1, newUserId);

        //Organization
        ContentValues organization = new ContentValues();
        organization.put(DatabaseContract.OrganizationEntry.COLUMN_NAME_NAME, "Orgnization name");
        organization.put(DatabaseContract.OrganizationEntry.COLUMN_NAME_WEBSITE, "http://www.namewcs.com");
        long newOrganizationId = db.insert(DatabaseContract.OrganizationEntry.TABLE_NAME, null, organization);
        assertNotEquals(-1, newOrganizationId);

        //Belong
        ContentValues belong = new ContentValues();
        belong.put(DatabaseContract.BelongEntry.COLUMN_NAME_USER_ID, newUserId);
        belong.put(DatabaseContract.BelongEntry.COLUMN_NAME_ORGANIZATION_ID, newOrganizationId);
        long newBelongId = db.insert(DatabaseContract.BelongEntry.TABLE_NAME, null, belong);
        assertNotEquals(-1, newBelongId);

        //Tweet
        String content = "Nouveau tweet n° : ";
        for (int i = 0; i < 10;i++) {
            ContentValues tweet = new ContentValues();
            tweet.put(DatabaseContract.TweetEntry.COLUMN_NAME_CONTENT, content + String.valueOf(i + 1));
            tweet.put(DatabaseContract.TweetEntry.COLUMN_NAME_USER_ID, newUserId);
            long newTweetId = db.insert(DatabaseContract.TweetEntry.TABLE_NAME, null, tweet);
            assertNotEquals(-1, newTweetId);
        }
    }

    //Test lecture

    @Test
    public void testReadTweet() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        context.deleteDatabase(DatabaseContract.UserEntry.TABLE_NAME);
        TweetDbHelper dBHelper = new TweetDbHelper(context);
        SQLiteDatabase db = dBHelper.getWritableDatabase();

        // récupération de l'id du dernier user ajouté
        Cursor cursor = db.rawQuery("SELECT user_id FROM " + DatabaseContract.UserEntry.TABLE_NAME +
                " ORDER BY user_id DESC LIMIT 1", null);
        long lastUserId = 0;
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            lastUserId = cursor.getLong(cursor.getColumnIndex("user_id"));
        }

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                DatabaseContract.TweetEntry.COLUMN_NAME_ID,
                DatabaseContract.TweetEntry.COLUMN_NAME_CONTENT,
                DatabaseContract.TweetEntry.COLUMN_NAME_USER_ID
        };
        // on filtre sur l'id du dernier user
        String selection = DatabaseContract.TweetEntry.COLUMN_NAME_USER_ID + " = ?";
        String[] selectionArgs = {
                String.valueOf(lastUserId)
        };

        // How you want the results sorted in the resulting Cursor
        cursor = db.query(
                DatabaseContract.TweetEntry.TABLE_NAME, // The table to query
                projection,                             // The columns to return
                selection,                              // The columns for the WHERE clause
                selectionArgs,                          // The values for the WHERE clause
                null,                                   // don't group the rows
                null,                                   // don't filter by row groups
                null                                    // The sort order
        );

        while(cursor.moveToNext()) {
            String content = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseContract.TweetEntry.COLUMN_NAME_CONTENT));
            assertNotEquals(null, content);
            Log.i(TAG, "testReadTweet: " + content);
        }
        cursor.close();
    }
}


