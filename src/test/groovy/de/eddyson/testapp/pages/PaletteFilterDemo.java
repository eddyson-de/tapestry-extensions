package de.eddyson.testapp.pages;

import java.util.ArrayList;
import java.util.List;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ValueEncoderSource;

public class PaletteFilterDemo {

  @Property
  @Persist
  List<String> selected;

  @Inject
  private ValueEncoderSource valueEncoderSource;

  public ValueEncoder getEncoder(){
    return valueEncoderSource.getValueEncoder(String.class);
  }

  @OnEvent(EventConstants.ACTIVATE)
  public void setupList(){
    if (selected == null){
      selected = new ArrayList<>();
    }
  }

}
