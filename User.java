/**
 * Created by Adrian on 10/29/2016.
 */
import java.util.List;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * User class that will consist of most entries made by users.
 */
public class User extends baseUser implements Observer{
    /**
     * A user's followings, or the list of Users they are following.
     */
    private List<User> followings;
    /**
     * A user's twitter feeds, or their messages along with the messages of those they are following.
     */
    private List<String> twitterFeeds;

    /**
     * Constructor. Creates everything.
     * @param id The user's unique ID.
     */
    public User(String id){
        setUniqueID(id);
        followings=new ArrayList<>();
        twitterFeeds=new ArrayList<>();
    }

    /**
     * Follows a user and adds them to the list of followings.
     * @param user The user to follow.
     */
    public void follow(User user){
        followings.add(user);
    }

    /**
     * Updates twitterfeed with observed user's last tweet if that is what is being notified.
     * @param obs Observed user.
     * @param obj What they are changing.
     */
    public void update(Observable obs,Object obj){
        if(obj instanceof String)
            twitterFeeds.add((String) obj);
    }

    /**
     * 'Tweets' a message, adds it to twitterfeed and alerts followers of this change.
     * @param tweet New message to be added to twitterfeed.
     */
    public void tweet(String tweet){
        tweet=getUniqueID()+":"+tweet;
        twitterFeeds.add(tweet);
        setChanged();
        notifyObservers(new String (tweet));
    }

    /**
     * Gets twitterFeed
     * @return TwitterFeed
     */
    public List<String> getTwitterFeeds() {
        return twitterFeeds;
    }

    /**
     * Gets list of followings.
     * @return Followings
     */
    public List<User> getFollowings() {
        return followings;
    }

    /**
     * Accepts a visitor. Visitor records data.
     * @param vis Visitor to accept.
     */
    public void accept(Visitor vis){
        vis.record(this);
    }
}
