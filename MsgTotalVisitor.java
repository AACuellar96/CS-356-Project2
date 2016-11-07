/**
 * Visitor that handles amount of messages in a user's feed.
 */
public class MsgTotalVisitor implements Visitor  {
    /**
     * Total amount of messages.
     */
    private int value;

    /**
     * Adds to value according to the size of user's twitterfeeds.
     * @param u User to record.
     */
    public void record(User u){
        value+=u.getTwitterFeeds().size();
    }
    public void record(UserGroup u){
        return;
    }

    /**
     * Does nothing as a UserGroup is not a User.
     * @return
     */
    public int getValue() {
        return value;
    }
}
