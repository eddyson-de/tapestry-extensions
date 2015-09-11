package de.eddyson.tapestry.extensions.mixins;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.corelib.components.Grid;
import org.apache.tapestry5.grid.ColumnSort;
import org.apache.tapestry5.grid.GridSortModel;

public class DefaultGridSort {

  @InjectContainer
  private Grid grid;

  @Parameter(required = true, allowNull = false, defaultPrefix = BindingConstants.LITERAL)
  private String sortColumn;

  @Parameter(allowNull = false, defaultPrefix = BindingConstants.LITERAL)
  private ColumnSort sortOrder = ColumnSort.ASCENDING;

  void setupRender() {

    GridSortModel sortModel = grid.getSortModel();
    if (sortModel.getSortConstraints().isEmpty()) {

      while (sortModel.getColumnSort(sortColumn) != sortOrder) {
        sortModel.updateSort(sortColumn);
      }
    }
  }

}
