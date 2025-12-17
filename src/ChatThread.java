/**
 * Thread for simulating chat messages between students.
 * Updates both students' message histories.
 */
public class ChatThread implements Runnable {

    private UniversityStudent sender;
    private UniversityStudent receiver;
    private String message;

    public ChatThread(UniversityStudent sender, UniversityStudent receiver, String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }

    /**
     * Adds message to histories and logs to console.
     * Uses synchronized blocks for thread safety.
     */
    @Override
    public void run() {
        synchronized (sender) {
            sender.addMessageToHistory("To " + receiver.name + ": " + message);
        }
        synchronized (receiver) {
            receiver.addMessageToHistory("From " + sender.name + ": " + message);
        }
        
        System.out.println(sender.name + " → " + receiver.name + ": " + message);
    }
}