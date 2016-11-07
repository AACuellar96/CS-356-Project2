/**
 * Records the amount of groups there are.
 */
public class GTotalVisitor implements Visitor {
    /**
     * Total amount of groups.
     */
    private int value;

    /**
     * Does nothing as a User is not a UserGroup.
     * @param u User to record.
     */
    public void record(User u){
        return;
    }

    /**
     * Increments value by one.
     * @param u UserGroup to record.
     */
    public void record(UserGroup u){
        value++;
    }

    /**
     * Returns total amount of groups.
     * @return Amount of UserGroups.
     */
    public int getValue() {
        return value;
    }
}
