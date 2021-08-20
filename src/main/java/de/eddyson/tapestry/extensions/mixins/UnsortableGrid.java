package de.eddyson.tapestry.extensions.mixins;

import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.beanmodel.BeanModel;
import org.apache.tapestry5.corelib.components.Grid;

import java.util.List;

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
