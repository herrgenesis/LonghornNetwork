/**
 * Thread for simulating friend requests between students.
 */
public class FriendRequestThread implements Runnable {

    private UniversityStudent sender;
    private UniversityStudent receiver;

    public FriendRequestThread(UniversityStudent sender, UniversityStudent receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    /**
     * Logs the friend request to console.
     */
    @Override
    public void run() {
        System.out.println(sender.name + " sent a friend request to " + receiver.name);
    }
}
