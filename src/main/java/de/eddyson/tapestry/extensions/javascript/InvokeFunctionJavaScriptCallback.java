package de.eddyson.tapestry.extensions.javascript;

import org.apache.tapestry5.services.ajax.JavaScriptCallback;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

/**
 * A {@link JavaScriptCallback} that invokes a function from a module
 * <p>
 * Example usage:
 *
 * <pre>
 * <code>
 * ajaxResponseRenderer.addCallback(new InvokeFunctionJavaScriptCallback("t5/core/console", "info", "Hello World!"));
 * </code>
 * </pre>
 */
public class InvokeFunctionJavaScriptCallback implements JavaScriptCallback {

  private final String moduleName;
  private final String functionName;
  private final Object[] arguments;

  public InvokeFunctionJavaScriptCallback(final String moduleName, final String functionName,
      final Object... arguments) {
    this.moduleName = moduleName;
    this.functionName = functionName;
    this.arguments = arguments;
  }

  public InvokeFunctionJavaScriptCallback(final String moduleName, final Object... arguments) {
    this(moduleName, null, arguments);
  }

  @Override
  public void run(final JavaScriptSupport javascriptSupport) {
    if (functionName != null) {
      javascriptSupport.require(moduleName).invoke(functionName).with(arguments);
    } else {
      javascriptSupport.require(moduleName).with(arguments);
    }

  }
}
