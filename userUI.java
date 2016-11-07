import javax.swing.*;
import java.util.List;
/**
 * The userUI class. Creates a JFrame and all components in it for the UI.
 */
public class userUI extends javax.swing.JFrame{
    /**
     * The User being represented by this userUI.
     */
    private User user;
    /**
     * All of the below are swing components.
     */
    private javax.swing.JButton followUserButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList<String> listOfCurrentFollowings;
    private javax.swing.JList<String> listOfNewsFeed;
    private javax.swing.JButton postTweetButton;
    private javax.swing.JTextField tweetMessage;
    private javax.swing.JTextField userIDTextArea2;
    private javax.swing.JPanel userPanel;
    private DefaultListModel lModelCF;
    private DefaultListModel lModelNF;

    /**
     * Constructor. Creates everything.
     * @param user User being represented in this userUI.
     */
    public userUI(User user){
        this.user=user;
        initComponents(this.user);
    }

    /**
     * Updates this userUI's list of current followings.
     */
    public void updateCF(){
        lModelCF = new DefaultListModel();
        lModelCF.addElement("List View (Current Following)");
        List<User> followings = user.getFollowings();
        for(int size=0;size<followings.size();size++) {
            lModelCF.addElement("- " + followings.get(size).getUniqueID());
        }
        listOfCurrentFollowings.setModel(lModelCF);
        jScrollPane1.setViewportView(listOfCurrentFollowings);
    }
    /**
     * Updates this userUI's news feed, or twitter feed.
     */
    public void updateNF(){
        lModelNF = new DefaultListModel();
        lModelNF.addElement("List View (News Feed)");
        List<String> nFeed = user.getTwitterFeeds();
        for(int size=0;size<nFeed.size();size++) {
            lModelNF.addElement("- " + nFeed.get(size));
        }
        listOfNewsFeed.setModel(lModelNF);
        jScrollPane2.setViewportView(listOfNewsFeed);

    }

    /**
     * Handler for when the postTweetButton is pressed. Tweets the message.Updates all newsFeeds in all userUI's
     * to update any corresponding followers.
     * @param evt
     */
    private void postTweetButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String tweet = tweetMessage.getText();
        user.tweet(tweet);
        adminUI.getUI().updateALLNF();
    }
    /**
     * Handler for when the followUserButton is pressed. Follows the Use if such a user exists.
     * @param evt
     */
    private void followUserButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String uniqueName = userIDTextArea2.getText();
        if(HashTable.getTable().get(uniqueName+"USER")!=null && uniqueName!=user.getUniqueID()){
            List<User> tempL = user.getFollowings();
            for(int size=0;size<tempL.size();size++){
                if(tempL.get(size).getUniqueID().equals(uniqueName)){
                    String message = "You are already following this user.";
                    JOptionPane.showMessageDialog(null, message, "Info", JOptionPane.PLAIN_MESSAGE);
                    return;
                }
            }
            User temp = (User) HashTable.getTable().get(uniqueName+"USER");
            user.follow(temp);
            temp.addObserver(user);
            adminUI.getUI().updateALLCF();
        }
        else{
            String message = "No such user exists.";
            JOptionPane.showMessageDialog(null, message, "Info", JOptionPane.PLAIN_MESSAGE);
        }
    }

    /**
     * Handler for the tweetMessageTextArea.
     * @param evt
     */
    private void tweetMessageActionPerformed(java.awt.event.ActionEvent evt) {
    }

    /**
     * Handler for the userIDTextArea2.
     * @param evt
     */
    private void userIDTextArea2ActionPerformed(java.awt.event.ActionEvent evt) {
    }

    /**
     * Creates all of the JFrame components.
     * @param user The user being represented by this userUI.
     */
    private void initComponents(User user) {

        userPanel = new javax.swing.JPanel();
        tweetMessage = new javax.swing.JTextField();
        postTweetButton = new javax.swing.JButton();
        userIDTextArea2 = new javax.swing.JTextField();
        followUserButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        listOfCurrentFollowings = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        listOfNewsFeed = new javax.swing.JList<>();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        userPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(106, 90, 205)));
        userPanel.setPreferredSize(new java.awt.Dimension(400, 400));

        tweetMessage.setBackground(new java.awt.Color(240, 240, 240));
        tweetMessage.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        tweetMessage.setForeground(new java.awt.Color(106, 90, 205));
        tweetMessage.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tweetMessage.setText("Tweet Message");
        tweetMessage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tweetMessageActionPerformed(evt);
            }
        });

        postTweetButton.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        postTweetButton.setForeground(new java.awt.Color(106, 90, 205));
        postTweetButton.setText("Post Tweet");
        postTweetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                postTweetButtonActionPerformed(evt);
            }
        });

        userIDTextArea2.setBackground(new java.awt.Color(240, 240, 240));
        userIDTextArea2.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        userIDTextArea2.setForeground(new java.awt.Color(106, 90, 205));
        userIDTextArea2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        userIDTextArea2.setText("USER ID");
        userIDTextArea2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userIDTextArea2ActionPerformed(evt);
            }
        });

        followUserButton1.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        followUserButton1.setForeground(new java.awt.Color(106, 90, 205));
        followUserButton1.setText("Follow User");
        followUserButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                followUserButtonActionPerformed(evt);
            }
        });

        listOfCurrentFollowings.setBackground(new java.awt.Color(240, 240, 240));
        listOfCurrentFollowings.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        listOfCurrentFollowings.setForeground(new java.awt.Color(106, 90, 205));
        lModelCF = new DefaultListModel();
        lModelCF.addElement("List View (Current Following)");
        List<User> followings = user.getFollowings();
        for(int size=0;size<followings.size();size++) {
            lModelCF.addElement("- " + followings.get(size).getUniqueID());
        }
        listOfCurrentFollowings.setModel(lModelCF);
        jScrollPane1.setViewportView(listOfCurrentFollowings);

        listOfNewsFeed.setBackground(new java.awt.Color(240, 240, 240));
        listOfNewsFeed.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        listOfNewsFeed.setForeground(new java.awt.Color(106, 90, 205));
        lModelNF = new DefaultListModel();
        lModelNF.addElement("List View (News Feed)");
        List<String> nFeed = user.getTwitterFeeds();
        for(int size=0;size<nFeed.size();size++) {
            lModelNF.addElement("- " + nFeed.get(size));
        }
        listOfNewsFeed.setModel(lModelNF);
        jScrollPane2.setViewportView(listOfNewsFeed);

        javax.swing.GroupLayout userPanelLayout = new javax.swing.GroupLayout(userPanel);
        userPanel.setLayout(userPanelLayout);
        userPanelLayout.setHorizontalGroup(
                userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(userPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1)
                                        .addGroup(userPanelLayout.createSequentialGroup()
                                                .addComponent(userIDTextArea2, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(followUserButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jScrollPane2)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, userPanelLayout.createSequentialGroup()
                                                .addComponent(tweetMessage)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(postTweetButton, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );
        userPanelLayout.setVerticalGroup(
                userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(userPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(followUserButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                                        .addComponent(userIDTextArea2))
                                .addGap(13, 13, 13)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(tweetMessage)
                                        .addComponent(postTweetButton, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 470, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(userPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 372, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(userPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
    }

}
