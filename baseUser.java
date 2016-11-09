import java.util.Observable;

/**
 * Abstract class that all possible User components will have as the base.Also extends observable in case userGroups
 * is able to be observed in the future.
 */
public abstract class baseUser extends Observable{
    /**
     * String that represents a baseUser's unique name.
     */
    private String uniqueID;
    /**
     * Class that represents a baseUser's parent UserGroup, or what group it belongs to.
     */
    private UserGroup parentGroup;

    /**
     * Method that returns the baseUser's unique ID.
     * @return baseUser's unique ID.
     */
    public String getUniqueID() { return uniqueID; }

    /**
     * Method that sets a baseUser's uniqueID.
     * @param uniqueID what the baseUser's unique ID will be.
     */
    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    /**
     * Method that sets the parent Group of baseUser.
     * @param parentGroup What baseUser's parent Group will be.
     */
    public void setParentGroup(UserGroup parentGroup) {
        this.parentGroup = parentGroup;
    }

    /**
     * Gets the parent Group of baseUser.
     * @return baseUser's parent Group.
     */
    public UserGroup getParentGroup() { return parentGroup; }

    /**
     * Method for the purpose of visitor design implementation. All baseUser subclasses should have an accept method
     * to handle visitors.
     * @param vis
     */
    abstract void accept(Visitor vis);

    /**
     * Abstract methods so all baseUsers in a hashtable can have unique names for their own class, eg a user can
     * share a name with a group but not another user.
     * @return uniqueID+class
     */
    abstract String getTableID();

}
