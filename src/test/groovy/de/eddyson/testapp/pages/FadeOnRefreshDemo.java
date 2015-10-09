package de.eddyson.testapp.pages;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;

public class FadeOnRefreshDemo {

  @OnEvent(EventConstants.REFRESH)
  void refreshZone() throws InterruptedException {
    Thread.sleep(600l);
  }

}
