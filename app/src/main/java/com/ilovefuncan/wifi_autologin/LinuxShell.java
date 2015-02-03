package com.ilovefuncan.wifi_autologin;

import android.util.Log;
import java.io.*;


public class LinuxShell {

    public static void runCommands(String ... strings) {

        try {
            Process shProcess = Runtime.getRuntime().exec("sh");
            DataOutputStream shStdInStream = new DataOutputStream(shProcess.getOutputStream());
            BufferedReader shStdOut = new BufferedReader(new InputStreamReader(shProcess.getInputStream()));
            BufferedReader shStdErr = new BufferedReader(new InputStreamReader(shProcess.getErrorStream()));

            for (String s : strings) {
                shStdInStream.writeBytes(s + "\n");
                shStdInStream.flush();
            }

            shStdInStream.writeBytes("exit\n");
            shStdInStream.flush();

            while (true) {
                boolean stdoutFinished = false;

                String line = shStdOut.readLine();
                if (line == null) stdoutFinished = true;
                else Log.d("sh-stdout", line);

                line = shStdErr.readLine();
                if (line == null && stdoutFinished) break;
                if (line != null) Log.d ("sh-stderr", line);
            }
            shStdOut.close();

            try { shProcess.waitFor(); }
            catch (InterruptedException e) {
                e.printStackTrace();
            }

            shStdInStream.close();
            shStdOut.close();
            shStdErr.close();

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
