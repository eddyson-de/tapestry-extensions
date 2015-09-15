package de.eddyson.tapestry.extensions.mixins;

import java.util.List;

import org.apache.tapestry5.Block;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.PropertyConduit;
import org.apache.tapestry5.PropertyOverrides;
import org.apache.tapestry5.annotations.BindParameter;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.RequestParameter;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.beaneditor.PropertyModel;
import org.apache.tapestry5.corelib.components.Any;
import org.apache.tapestry5.corelib.components.Grid;
import org.apache.tapestry5.corelib.data.GridPagerPosition;
import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.grid.SortConstraint;
import org.apache.tapestry5.internal.PropertyOverridesImpl;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.BeanBlockSource;
import org.apache.tapestry5.services.Environment;
import org.apache.tapestry5.services.PartialTemplateRenderer;
import org.apache.tapestry5.services.PropertyOutputContext;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

@Import(stylesheet = "infinigrid.css")
public class InfiniGrid {

  private final static String EMPTY_STRING = "";

  @InjectContainer
  private Grid grid;

  @BindParameter
  private GridDataSource source;

  @BindParameter
  private int rowsPerPage;

  @BindParameter
  private boolean inPlace;

  @BindParameter
  private GridPagerPosition pagerPosition;

  @Inject
  private ComponentResources resources;

  @Inject
  private JavaScriptSupport javaScriptSupport;

  @Inject
  private BeanBlockSource beanBlockSource;

  @Inject
  private PartialTemplateRenderer partialTemplateRenderer;

  @Inject
  private Environment environment;

  void setupRender() {
    if (!inPlace) {
      throw new IllegalArgumentException("The Grid's inPlace parameter must be set to true");
    }
    if (pagerPosition != GridPagerPosition.NONE) {
      throw new IllegalArgumentException("The Grid's pagerPosition parameter must be set to NONE");
    }
  }

  void afterRender() {
    if (grid.getDataSource().getAvailableRows() > 0) {
      Any table = (Any) resources.getContainerResources().getEmbeddedComponent("table");
      String clientId = table.getClientId();
      javaScriptSupport.require("de/eddyson/tapestry/extensions/infinigrid").with(clientId,
          resources.createEventLink(EventConstants.ACTION).toURI());
    }
  }

  Object onAction(@RequestParameter("from") final int startIndex) {
    GridDataSource ds = source;
    List<SortConstraint> sortConstraints = grid.getSortModel().getSortConstraints();
    int available = ds.getAvailableRows();
    int endIndex = Math.min(available, startIndex + rowsPerPage);
    ds.prepare(startIndex, endIndex, sortConstraints);

    JSONArray rows = new JSONArray();
    BeanModel<?> model = grid.getDataModel();
    final PropertyOverrides overrides = new PropertyOverridesImpl(resources.getContainerResources());

    for (int i = startIndex; i < endIndex; i++) {
      Object o = ds.getRowValue(i);
      JSONArray row = new JSONArray();
      for (String propertyName : model.getPropertyNames()) {
        final PropertyModel propertyModel = model.get(propertyName);

        PropertyConduit conduit = propertyModel.getConduit();

        final Object value = conduit != null ? conduit.get(o) : null;
        String propertyBlockOverrideParameterName = propertyName + "Cell";
        Block block = overrides.getOverrideBlock(propertyBlockOverrideParameterName);
        boolean isOverrideBlock = block != null;
        String markup;
        if (block == null) {
          String dataType = propertyModel.getDataType();
          if (beanBlockSource.hasDisplayBlock(dataType)) {
            block = beanBlockSource.getDisplayBlock(dataType);
          }

        }
        if (block != null) {
          PropertyOutputContext propertyOutputContext = new PropertyOutputContext() {

            @Override
            public Object getPropertyValue() {
              return value;
            }

            @Override
            public String getPropertyName() {
              return propertyName;
            }

            @Override
            public String getPropertyId() {
              return propertyModel.getId();
            }

            @Override
            public Messages getMessages() {
              return overrides.getOverrideMessages();
            }
          };
          environment.push(PropertyOutputContext.class, propertyOutputContext);
          try {
            markup = partialTemplateRenderer.render(block);
          } catch (Exception e) {

            if (isOverrideBlock && isCausedByNullPointerException(e)) {
              throw new IllegalStateException("Cannot render table cell for property " + propertyName
                  + ", try to replace the cell override (parameter " + propertyBlockOverrideParameterName
                  + ") by a BeanBlockSource contribution", e);
            } else {
              throw e;
            }

          } finally {
            environment.pop(PropertyOutputContext.class);
          }

        } else {
          markup = value != null ? value.toString() : EMPTY_STRING;
        }
        row.put(markup);

      }
      rows.put(row);
    }

    JSONObject result = new JSONObject("rows", rows, "more", available > endIndex);

    return result;
  }

  private static boolean isCausedByNullPointerException(final Exception e) {
    Throwable ex = e;
    while (!(ex instanceof NullPointerException) && ex.getCause() != null) {
      ex = ex.getCause();
    }
    return ex instanceof NullPointerException;
  }
}
