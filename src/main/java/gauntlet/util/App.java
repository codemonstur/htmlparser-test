package gauntlet.util;

public enum App {;

    public interface CheckedRunnable {
        void run() throws Exception;
    }

    public static void runApp(final CheckedRunnable program) {
        try {
            program.run();
        } catch (Exception e) {
            System.err.println("An error occurred during execution, error follows");
            e.printStackTrace();
        }
    }

}
