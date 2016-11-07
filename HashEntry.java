/**
 * Class that is stored in hashtable. Holds a baseUser.
 */
public class HashEntry {
    // The baseUser in the HashEntry
    private baseUser value;
    // The HashEntry following this one
    private HashEntry next;

    public HashEntry(baseUser value) {
        this.value = value;
    }

    //Returns the key of HashEntry
    public String getKey() {
        if(value instanceof User)
            return value.getUniqueID()+"USER";
        else
            return value.getUniqueID()+"GROUP";
    }

    // Get the value of HashEntry
    public baseUser getValue() {
        return this.value;
    }

    // Set the value of HashEntry
    public void setValue(baseUser value) {
        this.value = value;
    }

    // Get the next HashEntry after this HashEntry
    public HashEntry getNext() {
        return this.next;
    }

    // Set the next HashEntry after this HashEntry
    public void setNext(HashEntry next) {
        this.next = next;
    }
    //Removes the next HashEntry after this HashEntry
    public void removeNext(){
        next = null;
    }
}
