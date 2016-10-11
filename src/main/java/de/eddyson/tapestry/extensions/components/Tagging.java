package de.eddyson.tapestry.extensions.components;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.RequestParameter;
import org.apache.tapestry5.corelib.base.AbstractField;
import org.apache.tapestry5.internal.util.CaptureResultCallback;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.Request;
import org.slf4j.Logger;

@Import(stylesheet = "webjars:select2:$version/dist/css/select2.css")
public class Tagging extends AbstractField {

  @Parameter
  List<String> initialTags;

  @Property
  @Parameter(autoconnect = true)
  List<String> tags;

  @Parameter(value = "literal:None...")
  String placeholder;

  @Inject
  Logger logger;

  @Inject
  Request request;

  @Inject
  TypeCoercer typeCoercer;

  @Inject
  ComponentResources componentResources;



  /**
   * Method implemented by subclasses to actually do the work of processing the submission of the form. The element's
   * controlName property will already have been set. This method is only invoked if the field is <strong>not
   * {@link #isDisabled() disabled}</strong>.
   *
   * @param controlName the control name of the rendered element (used to find the correct parameter in the request)
   */
  @Override
  protected void processSubmission(final String controlName) {

    String[] submittedTags = request.getParameters(controlName+"[]");
    if (submittedTags != null) {
      tags = Arrays.asList(submittedTags);
    } else {
      tags = null;
    }
  }

  @OnEvent("internalComplete")
  JSONObject completionValues(@RequestParameter(value = "q",allowBlank = true)String searchString){
    Object[] context = new Object[1];
    context[0] = searchString;
    CaptureResultCallback<Object> callback = CaptureResultCallback.create();
    List<Object> completionsRaw = null;
    List<String> completions = null;
    if (searchString == null || searchString.equals("")) {
      completions = initialTags;
    } else {
      componentResources.triggerEvent("completeTags", context, callback);
      completionsRaw = typeCoercer.coerce(callback.getResult(), List.class);
    }

    if (completionsRaw != null) {
      completions = completionsRaw.stream()
              .map(completion -> typeCoercer.coerce(completion, String.class)).collect(Collectors.toList());
    }
    logger.debug("Coerced tag completions for Tagging component ({}): {}", componentResources.getId(), completions);

    JSONArray completionsArray = new JSONArray();
    if (completions != null) {
      completions.stream().forEach(completion -> completionsArray.put(new JSONObject("id", completion, "text",
              completion)));
    }
    return new JSONObject("data",completionsArray );
  }

  void beginRender(MarkupWriter writer){
    writer.element("select", "style", "width: 100%; display: none;", "id", getClientId(), "name", getControlName() +
            "[]",
            "multiple","multiple");
      if (tags != null) {
        tags.stream().forEach(initialTag -> {
          writer.element("option", "value", initialTag).text(initialTag);
          writer.attributes("selected", "selected");
          writer.end();
        });
      }
    writer.end();

  }

  void afterRender(){
    javaScriptSupport.require("de/eddyson/tapestry/extensions/tagging").with(getClientId(), componentResources
            .createEventLink("internalComplete").toURI(),placeholder);
  }
}
