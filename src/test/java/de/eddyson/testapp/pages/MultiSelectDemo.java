package de.eddyson.testapp.pages;

import java.util.List;


import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.internal.services.StringValueEncoder;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.apache.tapestry5.services.PropertyEditContext;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.slf4j.Logger;
import de.eddyson.tapestry.extensions.components.MultiSelect;
import de.eddyson.testapp.Parrot;

public class MultiSelectDemo {

  @Property
  @Persist
  private List<String> selected;

  @Property
  @Persist
  private List<String> selectedSingle;
  
  @Property
  @Persist
  private Parrot parrot;

  @Inject
  AjaxResponseRenderer ajaxResponseRenderer;

  @InjectComponent
  Zone eventZone;

  @InjectComponent
  Zone eventZoneSingle;

  @Inject
  Logger logger;
  
  @Environmental
  @Property(write=false)
  private PropertyEditContext propertyEditContext;

  public Object getModel() {
    return CollectionFactory.newList("Bar", "Foo", "Hello", "World");
  }

  public ValueEncoder<String> getEncoder() {
    return new StringValueEncoder();
  }

  @OnEvent(value = MultiSelect.SELECTION_CHANGED, component = "multi")
  void updateView(List<String> list){
    logger.debug("Event triggered: {} -> Values: {}", MultiSelect.SELECTION_CHANGED, list);
    selected = list;
    ajaxResponseRenderer.addRender(eventZone);

  }

  @OnEvent(value = MultiSelect.SELECTION_CHANGED, component = "single")
  void updateSingleView(List<String> list){
    logger.debug("Event triggered: {} -> Values: {}", MultiSelect.SELECTION_CHANGED, list);
    selectedSingle = list;
    ajaxResponseRenderer.addRender(eventZoneSingle);

  }

  public Object getModelSingle() {
    return CollectionFactory.newList("Bar", "Foo", "Hello", "World");
  }
  
  public Object getModelBeanEditor() {
    return CollectionFactory.newList("Bar", "Foo", "Hello", "World");
  }
  
  void setupRender() {
    if (parrot == null) {
      parrot = new Parrot();
    }
    
  }
  
}
