import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Ping {
	private String arg;

	public Ping(String ar) {
		this.arg = ar;
	}

	public void execute() {
		ProcessBuilder pb2 = new ProcessBuilder();
		pb2.command("bash", "-c", this.arg);

		// Executing bash script through Buffer reader
		try {
			Process p2 = pb2.start();
			BufferedReader reader2 = new BufferedReader(new InputStreamReader(p2.getInputStream()));
			String line;

			while ((line = reader2.readLine()) != null) {
				System.out.println(line);
			}

			// Check if the script completed successfully or not
			p2.waitFor();
			int exit = p2.waitFor();
			if (exit != 0) {
				System.err.println("Script exited with non-zero exit code for ping(): " + exit);
			}
		} catch (IOException | InterruptedException e) {
			System.err.println("Error executing script: " + e.getMessage());
		}

	}
}
