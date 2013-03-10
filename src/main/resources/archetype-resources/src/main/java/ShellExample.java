package instamo.example;

import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.lang.InterruptedException;
import java.lang.Runnable;
import java.util.Arrays;

import org.apache.accumulo.core.util.shell.Shell;
import org.apache.accumulo.test.MiniAccumuloCluster;

public class ShellExample implements Runnable {
  
  @Override
  public void run() {
    File tempDir = null;
    MiniAccumuloCluster mac = null;
    
    try {
      tempDir = Files.createTempDir();
      tempDir.deleteOnExit();

      final String PASSWORD = "pass1234";

      mac = new MiniAccumuloCluster(tempDir, PASSWORD);

      mac.start();

      String[] args = new String[] {"-u", "root", "-p", PASSWORD, "-z",
        mac.getInstanceName(), mac.getZooKeepers()};

      Shell.main(args);

    } catch (InterruptedException e) {
      System.err.println("Error starting MiniAccumuloCluster: " + e.getMessage());
    } catch (IOException e) {
      System.err.println("Error starting MiniAccumuloCluster: " + e.getMessage());
    } finally {
      if (null != tempDir) {
        tempDir.delete();
      }

      if (null != mac) {
        try {
          mac.stop();
        } catch (InterruptedException e) {
          System.err.println("Error stopping MiniAccumuloCluster: " + e.getMessage());
        } catch (IOException e) {
          System.err.println("Error stopping MiniAccumuloCluster: " + e.getMessage());
        }
      }
    }
  }

  public static void main(String[] args) {
    System.out.println("\n   ---- Initializing Accumulo Shell\n");

    ShellExample shell = new ShellExample();
    shell.run();
  }
}
