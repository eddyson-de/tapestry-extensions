package de.eddyson.testapp.modules;

import de.eddyson.tapestry.extensions.modules.ExtensionsModule;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.commons.MappedConfiguration;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.ImportModule;
import org.apache.tapestry5.ioc.services.ApplicationDefaults;
import org.apache.tapestry5.ioc.services.SymbolProvider;

@ImportModule({ ExtensionsModule.class })
public final class TestModule {

  @Contribute(SymbolProvider.class)
  @ApplicationDefaults
  public static void configureApplicationDefaults(final MappedConfiguration<String, Object> configuration) {
    configuration.add(SymbolConstants.PRODUCTION_MODE, "false");
    // work around ResponseWrapper bug in Prototype implementation (TAP5-2463)
    configuration.add(SymbolConstants.JAVASCRIPT_INFRASTRUCTURE_PROVIDER, "jquery");
  }

  private TestModule() {
  }

}
