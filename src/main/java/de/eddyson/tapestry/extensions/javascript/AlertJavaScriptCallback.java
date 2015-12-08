package de.eddyson.tapestry.extensions.javascript;

import org.apache.tapestry5.alerts.Alert;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.alerts.Duration;
import org.apache.tapestry5.alerts.Severity;
import org.apache.tapestry5.services.ajax.JavaScriptCallback;

/**
 * A {@link JavaScriptCallback} that shows a client-side alert
 * <p>
 * Example usage:
 *
 * <pre>
 * <code>
 * ajaxResponseRenderer.addCallback(new AlertJavaScriptCallback(Duration.TRANSIENT, Severity.INFO, "Hello World!"));
 * </code>
 * <strong>Please note that this circumvents {@link AlertManager} and does not work with {@link Duration#UNTIL_DISMISSED}.
 *
 * </pre>
 */
public class AlertJavaScriptCallback extends InvokeFunctionJavaScriptCallback {

  public AlertJavaScriptCallback(final Duration duration, final Severity severity, final String message,
      final boolean markup) {
    super("t5/core/alert", new Alert(duration, severity, message, markup).toJSON());
  }

  public AlertJavaScriptCallback(final Duration duration, final Severity severity, final String message) {
    this(duration, severity, message, false);
  }

}