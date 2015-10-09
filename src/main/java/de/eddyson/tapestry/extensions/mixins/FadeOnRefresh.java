package de.eddyson.tapestry.extensions.mixins;

import javax.inject.Inject;

import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

@Import(stylesheet = "fadeonrefresh.less")
public class FadeOnRefresh {

  @InjectContainer
  private Zone zone;

  @Inject
  JavaScriptSupport javaScriptSupport;

  void afterRender() {
    javaScriptSupport.require("de/eddyson/tapestry/extensions/fadeonrefresh").with(zone.getClientId());
  }
}
