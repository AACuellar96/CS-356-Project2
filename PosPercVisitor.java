import java.util.ArrayList;
import java.util.List;

/**
 * Visitor that handles the amount of positive messages in a user's feed.
 */
public class PosPercVisitor  implements Visitor {
    /**
     * Amount of positive messages.
     */
    private int pos;
    /**
     * Total amount of messages.
     */
    private int total;

    /**
     * Records all positive messages and total amount of messages.
     * @param u User to record.
     */
    public void record(User u){
        List<String> twitterFeeds = u.getTwitterFeeds();
        for(int size=0;size<twitterFeeds.size();size++){
            if(twitterFeeds.get(size).toLowerCase().contains("nice") || twitterFeeds.get(size).toLowerCase().contains("good") || twitterFeeds.get(size).toLowerCase().contains("great")
                    || twitterFeeds.get(size).toLowerCase().contains("excellent") || twitterFeeds.get(size).toLowerCase().contains("cool")
                    || twitterFeeds.get(size).toLowerCase().contains("happy") || twitterFeeds.get(size).toLowerCase().contains("awesome")|| twitterFeeds.get(size).toLowerCase().contains("radical")
                    || twitterFeeds.get(size).toLowerCase().contains("best")|| twitterFeeds.get(size).toLowerCase().contains("fantastic")|| twitterFeeds.get(size).toLowerCase().contains("super")
                    || twitterFeeds.get(size).toLowerCase().contains("superb"))
                pos++;
        }
        total+=twitterFeeds.size();
    }

    /**
     * Does nothing as a UserGroup is not a user.
     * @param u UserGroup to record.
     */
    public void record(UserGroup u){
        return;
    }

    /**
     * Gets the percentage of positive messages out of all messages.
     * @return
     */
    public float getValue(){
        if(total!=0) {
            float res = (float) ((pos * 100) / total);
            pos = 0;
            total = 0;
            return res;
        }
        else
            return 0;
    }
}
