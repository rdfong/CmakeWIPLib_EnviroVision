package edu.wpi.first.networktables;

import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;

public class LoggerTest extends TestCase {

  NetworkTableInstance clientInst;

  @Override
  protected void setUp() throws Exception {
    clientInst = NetworkTableInstance.create();
  }

  @Override
  protected void tearDown() throws Exception {
    clientInst.free();
  }

  public void testLogger() {
    List<LogMessage> msgs = new ArrayList<>();
    clientInst.addLogger((msg) -> msgs.add(msg), LogMessage.kInfo, 100);

    clientInst.startClient("127.0.0.1", 10000);

    // wait for client to report it's started, then wait another 0.1 sec
    try {
      while ((clientInst.getNetworkMode() & NetworkTableInstance.kNetModeStarting) != 0) {
        Thread.sleep(100);
      }
      Thread.sleep(100);
    } catch (InterruptedException ex) {
      fail("interrupted while waiting for client to start");
    }

    assertFalse(msgs.isEmpty());
  }
}
