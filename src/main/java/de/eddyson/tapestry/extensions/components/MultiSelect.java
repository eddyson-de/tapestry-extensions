package de.eddyson.tapestry.extensions.components;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.tapestry5.Binding;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.FieldValidationSupport;
import org.apache.tapestry5.FieldValidator;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.SelectModelVisitor;
import org.apache.tapestry5.ValidationException;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.BeforeRenderTemplate;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.RequestParameter;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.corelib.base.AbstractField;
import org.apache.tapestry5.corelib.data.BlankOption;
import org.apache.tapestry5.internal.util.CaptureResultCallback;
import org.apache.tapestry5.internal.util.SelectModelRenderer;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.internal.util.GenericsUtils;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ValueEncoderSource;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.slf4j.Logger;

/**
 * (Multi)Selection component for Tapestry based on Select2
 * (see https://select2.github.io/ ).
 *
 * Borrows heavily from the Tapestry "Select" core component.
 *
 * @author Fabian Kretzer
 */
@Import(stylesheet = "webjars:select2:$version/dist/css/select2.css")
public class MultiSelect  extends AbstractField {

  public static final String EVENT_CHANGED = "changed";
  public static final String SELECTION_CHANGED = "selection_changed";
  public static final String BLANK_OPTION_VALUE = "-1";
  @Inject
  ComponentResources componentResources;

  @Inject
  JavaScriptSupport javaScriptSupport;

  @Property
  @Parameter(required = true)
  Collection selected;

  @Property
  @Parameter(required = true, allowNull = false)
  ValueEncoder encoder;

  @Inject
  Request request;

  @Inject
  Logger logger;

  @Parameter(value = "true")
  boolean multiple;

  @Property
  @Parameter(autoconnect = true)
  SelectModel model;


  @Inject
  private FieldValidationSupport fieldValidationSupport;

  @Inject
  private ValueEncoderSource valueEncoderSource;
  
  /**
   * Performs input validation on the value supplied by the user in the form submission.
   */
  @Parameter(defaultPrefix = BindingConstants.VALIDATE)
  private FieldValidator<Object> validate;

  /**
   * Controls whether an additional blank option is provided. The blank option precedes all other options and is never
   * selected. The value for the blank option is always the empty string, the label may be the blank string; the
   * label is from the blankLabel parameter (and is often also the empty string).
   */
  @Parameter(value = "auto", defaultPrefix = BindingConstants.LITERAL)
  private BlankOption blankOption;

  /**
   * The label to use for the blank option, if rendered. If not specified, the container's message catalog is
   * searched for a key, <code><em>id</em>-blanklabel</code>.
   */
  @Parameter(defaultPrefix = BindingConstants.LITERAL)
  private String blankLabel;

  @Parameter(value = "false")
  private boolean raw;

  @Property
  Object currentOption;

  public String toClient(){
    return encoder.toClient(currentOption);
  }

  public boolean isSelected(String clientValue){
    return selected.contains(encoder.toValue(clientValue));
  }

  @SetupRender
  void initialize(){
    if (this.selected == null){
      this.selected = new ArrayList<>();
    }
  }

  private class Renderer extends SelectModelRenderer
  {

    public Renderer(MarkupWriter writer)
    {
      super(writer, encoder, raw);
    }

    @Override
    protected boolean isOptionSelected(OptionModel optionModel, String clientValue)
    {
      return isSelected(clientValue);
    }
  }

  /**
   * Method implemented by subclasses to actually do the work of processing the submission of the form. The element's
   * controlName property will already have been set. This method is only invoked if the field is <strong>not
   * {@link #isDisabled() disabled}</strong>.
   *
   * @param controlName the control name of the rendered element (used to find the correct parameter in the request)
   */
  @Override
  protected void processSubmission(final String controlName) {
      String[] submittedValues = request.getParameters(controlName + "[]");
      Collection<Object> selected = this.selected;
    if (selected != null){
      selected.clear();
    }


      logger.debug("Submitted value from MultiSelect with id -> {} are: {}"
              , componentResources.getCompleteId(), submittedValues);
      if (submittedValues != null){
        //Multiple values submitted

        selected = Arrays.stream(submittedValues).filter(s -> !s.equals(BLANK_OPTION_VALUE)).map(encoder::toValue)
            .collect(Collectors.toList());
      } else if(request.getParameters(controlName) != null){
        //Single value submitted

        String singleValue = request.getParameter(controlName);
        if (singleValue != BLANK_OPTION_VALUE) {
          String quotesRemoved = singleValue.substring(1, singleValue.length()-1);
          selected.add(encoder.toValue(quotesRemoved));
        }
      }
    putPropertyNameIntoBeanValidationContext("selected");

    try
    {
      fieldValidationSupport.validate(selected, resources, validate);

      this.selected = selected;
    } catch (final ValidationException e)
    {
      validationTracker.recordError(this, e.getMessage());
    }

    removePropertyNameFromBeanValidationContext();
  }
  void renderBody(MarkupWriter writer){
    writer.element("select");
    writer.end();
  }
  void afterRender() {
    javaScriptSupport.require("de/eddyson/tapestry/extensions/multi-select").with(getClientId(), multiple, isRequired
            (), blankLabel != null ? blankLabel : JSONObject.NULL);
  }

  public String getId(){
    return getClientId();
  }

  /**
   * Trigger "selection_change" event with decoded values.
   *
   * @param context
   * @param string
   * @return
   */
  @OnEvent(value = EVENT_CHANGED)
    public Object changed(final List<String> context
          ,@RequestParameter(value = "values", allowBlank = true)String string){
        logger.debug("Event <{}> from component <{}> with values: {}", EVENT_CHANGED
                ,componentResources.getCompleteId(), string);

    CaptureResultCallback<Object> callback = new CaptureResultCallback<>();

    //Single value submitted
    if(string.startsWith("\"")){
      Object[] singleContextValue = new Object[1];
      String quotesRemoved = string.substring(1, string.length()-1);
      singleContextValue[0] = encoder.toValue(quotesRemoved);
      List<Object> currentSelectedValue = new ArrayList<>();
      if(singleContextValue[0] != BLANK_OPTION_VALUE){
        currentSelectedValue.add(singleContextValue[0]);
      }
      this.selected = currentSelectedValue;
      logger.debug("Single value submitted from component <{}> : {}",componentResources.getCompleteId(), singleContextValue[0]);
      componentResources.triggerEvent(SELECTION_CHANGED, singleContextValue,callback);

      //Multiple values submitted
    } else if(string.startsWith("[")) {
      try {
        JSONArray values = new JSONArray(string);
        List<Object> sentValues =  values.toList().stream()
                .filter(s -> !s.equals(BLANK_OPTION_VALUE)).map((Object obj) -> encoder.toValue((String) obj))
                .collect(Collectors.toList());

        Object[] newContext = Stream.concat(sentValues.stream(), context.stream()).toArray();
        logger.debug("Values submitted from component <{}> -> Encoded:  {}",componentResources.getCompleteId(),newContext);

        componentResources.triggerEvent(SELECTION_CHANGED, newContext, callback);

        this.selected = sentValues;
      } catch (RuntimeException e){
        logger.error("Could not decode multiple values from <{}> event. From component: {}. Submitted values: {}. Exception: {}", EVENT_CHANGED, componentResources.getCompleteId(), string, e.getMessage());
        componentResources.triggerEvent(SELECTION_CHANGED, null, callback);
        this.selected = null;
      }
    } else {
      componentResources.triggerEvent(SELECTION_CHANGED, null, callback);
      logger.debug("Could not decode value from <{}> event. From component: {}. Submitted values: {}", EVENT_CHANGED, componentResources.getCompleteId(), string);
      this.selected = null;
    }
    return callback.getResult();
    }

  public String getChangeEventUrl(){
    return this.componentResources.createEventLink(EVENT_CHANGED).toURI();
  }



  void beginRender(MarkupWriter writer)
  {
    writer.element("select",
            "name", getControlName()+"[]",
            "id", getClientId(),
            "class", cssClass,
    "data-change-uri", getChangeEventUrl(),
            "style", "display:none;"

           );
    if (multiple){
      writer.attributes("multiple", "multiple");
    }

    putPropertyNameIntoBeanValidationContext("selected");

    validate.render(writer);

    removePropertyNameFromBeanValidationContext();

    resources.renderInformalParameters(writer);

    decorateInsideField();

  }
  /**
   * Renders the options, including the blank option.
   */
  @BeforeRenderTemplate
  void options(MarkupWriter writer)
  {
    /*selectedClientValue = tracker.getInput(this);

    // Use the value passed up in the form submission, if available.
    // Failing that, see if there is a current value (via the value parameter), and
    // convert that to a client value for later comparison.

    if (selectedClientValue == null)
      selectedClientValue = value == null ? null : encoder.toClient(value);
    */
    if (showBlankOption())
    {
      writer.element("option", "value", BLANK_OPTION_VALUE);
      writer.write(blankLabel);
      writer.end();
    }

    SelectModelVisitor renderer = new Renderer(writer);

    model.visit(renderer);
  }

  void afterRender(MarkupWriter writer)
  {
    writer.end();
  }

  private boolean showBlankOption()
  {
    switch (blankOption)
    {
      case ALWAYS:
        return true;

      case NEVER:
        return false;

      default:
        return !isRequired();
    }
  }
  Object defaultBlankLabel()
  {
    Messages containerMessages = resources.getContainerMessages();

    String key = resources.getId() + "-blanklabel";

    if (containerMessages.contains(key))
      return containerMessages.get(key);

    return null;
  }

  /**
   * Computes a default value for the "validate" parameter using
   * {@link org.apache.tapestry5.services.FieldValidatorDefaultSource}.
   */
  Binding defaultValidate()
  {
    return this.defaultProvider.defaultValidatorBinding("selected", this.resources);
  }
  
  ValueEncoder<?> defaultEncoder()
  {
    Type parameterType = resources.getBoundGenericType("selected");
    
    if (parameterType == null || !(parameterType instanceof ParameterizedType)) {
      return null;
    }
    ParameterizedType parameterizedType = (ParameterizedType) parameterType;
    Type[] typeArguments = parameterizedType.getActualTypeArguments();
    if (typeArguments.length != 1){
      return null;
    }

    return valueEncoderSource.getValueEncoder(GenericsUtils.asClass(typeArguments[0]));
  }

}
