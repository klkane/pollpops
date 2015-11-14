package com.csc535.app.pollpops;

public class PollHelper {
    public static PollPopsDB popsDB;
    public static boolean created = false;

    public static PollPopsDB getPollPopsDB() {
        if( PollHelper.created ) {
            return PollHelper.popsDB;
        } else {
            PollHelper.popsDB = new PollPopsDB();
            PollHelper.created = true;
            return PollHelper.popsDB;
        }
    }
}