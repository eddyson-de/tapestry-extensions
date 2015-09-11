package de.eddyson.tapestry.extensions.mixins;

import java.util.List;

import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.corelib.components.Grid;

public class UnsortableGrid {

  @InjectContainer
  private Grid grid;

  void setupRender() {

    BeanModel<?> model = grid.getDataModel();
    List<String> propNames = model.getPropertyNames();
    for (String prop : propNames) {
      model.get(prop).sortable(false);
    }
  }

}
