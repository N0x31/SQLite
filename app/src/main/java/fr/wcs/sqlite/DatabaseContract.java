package fr.wcs.sqlite;

import android.provider.BaseColumns;

public final class DatabaseContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private DatabaseContract() {}

    /* Inner class that defines the table contents */
    public static class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_ID = "user_id";
        public static final String COLUMN_NAME_EMAIL = "email";
    }

    public static class OrganizationEntry implements BaseColumns {
        public static final String TABLE_NAME = "organization";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_ID = "organization_id";
        public static final String COLUMN_NAME_WEBSITE = "website";
    }

    public static class TweetEntry implements BaseColumns {
        public static final String TABLE_NAME = "tweet";
        public static final String COLUMN_NAME_ID = "tweet_id";
        public static final String COLUMN_NAME_CONTENT = "content";
        public static final String COLUMN_NAME_USER_ID = "user_id";
    }

    public static class BelongEntry implements BaseColumns {
        public static final String TABLE_NAME = "belong";
        public static final String COLUMN_NAME_USER_ID = "user_id";
        public static final String COLUMN_NAME_ORGANIZATION_ID = "organization_id";
    }

    //TWEET
    public static final String SQL_CREATE_TWEET =
            "CREATE TABLE IF NOT EXISTS " + TweetEntry.TABLE_NAME + " (" +
                    TweetEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    TweetEntry.COLUMN_NAME_CONTENT + " TEXT NOT NULL," +
                    TweetEntry.COLUMN_NAME_USER_ID + " INTEGER NOT NULL," +
                    " FOREIGN KEY (" + TweetEntry.COLUMN_NAME_USER_ID + ") REFERENCES " +
                    UserEntry.TABLE_NAME + "(" + UserEntry.COLUMN_NAME_ID + "));";

    public static final String SQL_DELETE_TWEET =
            "DROP TABLE IF EXISTS " + TweetEntry.TABLE_NAME;

    //USER
    public static final String SQL_CREATE_USER =
            "CREATE TABLE IF NOT EXISTS " + UserEntry.TABLE_NAME + " (" +
                    UserEntry.COLUMN_NAME_NAME + " TEXT NOT NULL," +
                    UserEntry.COLUMN_NAME_EMAIL + " TEXT NOT NULL," +
                    UserEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT);";

    public static final String SQL_DELETE_USER =
            "DROP TABLE IF EXISTS " + UserEntry.TABLE_NAME;

    //ORGA
    public static final String SQL_CREATE_ORGANIZATION =
            "CREATE TABLE IF NOT EXISTS " + UserEntry.TABLE_NAME + " (" +
                    OrganizationEntry.COLUMN_NAME_NAME + " TEXT NOT NULL," +
                    OrganizationEntry.COLUMN_NAME_WEBSITE + " TEXT NOT NULL," +
                    OrganizationEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT);";

    public static final String SQL_DELETE_ORGANIZATION =
            "DROP TABLE IF EXISTS " + OrganizationEntry.TABLE_NAME;

    //BELONG
    public static final String SQL_CREATE_BELONG =
            "CREATE TABLE IF NOT EXISTS " + BelongEntry.TABLE_NAME + " (" +
                    BelongEntry.COLUMN_NAME_USER_ID + " INTEGER," +
                    BelongEntry.COLUMN_NAME_ORGANIZATION_ID + " INTEGER," +
                    " PRIMARY KEY (" + BelongEntry.COLUMN_NAME_USER_ID + "," +
                    BelongEntry.COLUMN_NAME_ORGANIZATION_ID + ")," +
                    " FOREIGN KEY (" + BelongEntry.COLUMN_NAME_USER_ID + ") REFERENCES " +
                    UserEntry.TABLE_NAME + "(" + UserEntry.COLUMN_NAME_ID + ")," +
                    " FOREIGN KEY (" + BelongEntry.COLUMN_NAME_ORGANIZATION_ID + ") REFERENCES " +
                    OrganizationEntry.TABLE_NAME + "(" + OrganizationEntry.COLUMN_NAME_ID + "));";

    public static final String SQL_DELETE_BELONG =
            "DROP TABLE IF EXISTS " + TweetEntry.TABLE_NAME;
}

