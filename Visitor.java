/**
 * Visitor interface. Sets out what every visitor needs to have.
 */
public interface Visitor {
    /**
     * Method that handles a User.
     * @param u User to record.
     */
    public void record(User u);

    /**
     * Method that handles a userGroup.
     * @param u UserGroup to record.
     */
    public void record(UserGroup u);

}
