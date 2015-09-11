package de.eddyson.testapp.pages;

import java.util.List;

import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.internal.services.StringValueEncoder;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;

public class MultiSelectDemo {

  @Property
  @Persist
  private List<String> selected;

  public Object getModel() {
    return CollectionFactory.newList("Bar", "Foo", "Hello", "World");
  }

  public ValueEncoder<String> getEncoder() {
    return new StringValueEncoder();
  }

}
