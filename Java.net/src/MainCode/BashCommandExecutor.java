package MainCode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BashCommandExecutor {

    public static void main(String[] args) {
        String command = "ls -R";

        try {
            ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", command);

            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            int exitCode = process.waitFor();
            System.out.println("Exit Code: " + exitCode);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
