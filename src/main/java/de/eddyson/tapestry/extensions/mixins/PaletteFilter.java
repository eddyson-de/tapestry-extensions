package de.eddyson.tapestry.extensions.mixins;

import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.corelib.components.Palette;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

/**
 * @author Fabian Kretzer
 */



public class PaletteFilter {
  @Inject
  JavaScriptSupport javaScriptSupport;

  @InjectContainer
  Palette palette;

  void afterRender(){
    javaScriptSupport.require("de/eddyson/tapestry/extensions/paletteFilter").with(palette.getClientId());
  }

}
