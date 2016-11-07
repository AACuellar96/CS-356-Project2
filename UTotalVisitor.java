/**
 * Visitor that records the amount of Users there are.
 */
public class UTotalVisitor implements Visitor{
    /**
     * Amount of users recorded.
     */
    private int value;

    /**
     * Increments value
     * @param u User to record.
     */
    public void record(User u){
        value++;
    }

    /**
     * Does nothing as a UserGroup is not a user.
     * @param u UserGroup to record.
     */
    public void record(UserGroup u){
        return;
    }

    /**
     * Gets value.
     * @return Amount of user's recorded.
     */
    public int getValue() {
        return value;
    }
}
