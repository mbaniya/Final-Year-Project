import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class GetIp {

	public ArrayList<String> GetIpAdd() throws Exception {

		ProcessBuilder pb1 = new ProcessBuilder();
		String scriptPath = "/home/kali/fresh.sh";
		pb1.command("bash", "-c", scriptPath);
		ArrayList<String> output = new ArrayList<>();

		//Executing the bash script
		try {
			Process p1 = pb1.start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(p1.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
					output.add(line);
			}

			// Check if the script completed successfully or not
			int exitCode = p1.waitFor();
			if (exitCode != 0) {
				System.err.println("Script exited with non-zero exit code: " + exitCode);
			}
		} catch (IOException | InterruptedException e) {
			System.err.println("Error executing script: " + e.getMessage());
		}
		return output;

	}

}