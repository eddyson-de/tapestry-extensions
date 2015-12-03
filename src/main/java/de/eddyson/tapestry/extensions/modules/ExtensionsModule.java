package de.eddyson.tapestry.extensions.modules;

import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.services.LibraryMapping;
import org.apache.tapestry5.services.javascript.JavaScriptModuleConfiguration;
import org.apache.tapestry5.services.javascript.ModuleManager;

/**
 * @author Fabian Kretzer
 */
public class ExtensionsModule {

  public static final String PATH_PREFIX = "EddysonTapestryExtensions";

  public static void contributeComponentClassResolver(final Configuration<LibraryMapping> configuration) {
    configuration.add(new LibraryMapping(PATH_PREFIX, "de.eddyson.tapestry.extensions"));
  }

  @Contribute(ModuleManager.class)
  public static void setupJSModules(final MappedConfiguration<String, JavaScriptModuleConfiguration> configuration,
                                    @Path("webjars:select2:$version/dist/js/select2.js") final org.apache.tapestry5.ioc.Resource select2) {

    //For MultiSelect
    configuration.add("select2", new JavaScriptModuleConfiguration(select2));
  }

  private ExtensionsModule() {
  }
}
