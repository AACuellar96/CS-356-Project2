import java.util.List;
import java.util.ArrayList;

/**
 * UserGroup Class.
 */
public class UserGroup extends baseUser {
    /**
     * The baseUser's that belong to this group which for now is only Users or other UserGroups.
     */
    private List<baseUser> memberUsers;

    /**
     * Constructor. Creates everything.
     * @param id This userGroups unique ID.
     */
    public UserGroup(String id){
        setUniqueID(id);
        memberUsers=new ArrayList<>();
    }

    /**
     * Only works when the baseUser already has no parent UserGroup. Ads the baseUser to the list of baseUser in this
     * group.
     * @param user baseUser to add to list.
     */
    public void addToGroup(baseUser user){
        if(user.getParentGroup()==null) {
            user.setParentGroup(this);
            memberUsers.add(user);
        }
    }

    /**
     * Gets the members of this group.
     * @return memberUsers.
     */
    public List<baseUser> getMemberUsers() {
        return memberUsers;
    }

    /**
     * Accepts a visitor. Then has all of the baseUser in memberUsers accept the visitor also.
     * @param vis Visitor to accept.
     */
    public void accept(Visitor vis){
         vis.record(this);
        for(int size=0;size<memberUsers.size();size++)
            memberUsers.get(size).accept(vis);
    }
}
