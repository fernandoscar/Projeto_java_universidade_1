import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class Test {
    public static void main(String[] args) {
        // Redirect standard input to a stream of predefined inputs
        String input = "1\n4\ninvalid_id\n1\nJohn Doe\n-100\n123\n2\n0\n0";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // Run the main application
        Main.main(args);
    }
}
