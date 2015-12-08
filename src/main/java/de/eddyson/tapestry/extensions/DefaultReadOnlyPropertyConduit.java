package de.eddyson.tapestry.extensions;

import java.lang.annotation.Annotation;
import java.util.function.Function;

import org.apache.tapestry5.PropertyConduit;

/**
 * Default {@link PropertyConduit} implementation that can be used to display a
 * read-only property
 * <p>
 * For example, the following code creates a property conduit that displays a
 * File's size in engineering notation:
 *
 * <pre>
 * <code>
 *   PropertyConduit fileSizeAsEngineeringString = DefaultReadOnlyPropertyConduit
 *     .createStringConduit(instance -> BigDecimal.valueOf(((File) instance).length()).toEngineeringString());
 * </code>
 * </pre>
 *
 */
public interface DefaultReadOnlyPropertyConduit extends PropertyConduit {

  public static <T> PropertyConduit createStringConduit(final Function<Object, String> accessor) {
    return create(String.class, accessor);
  }

  public static <T> PropertyConduit create(final Class<T> returnType, final Function<Object, ? extends T> accessor) {
    return new DefaultReadOnlyPropertyConduitImpl<T>(returnType, accessor);
  }

  @Override
  public default <T extends Annotation> T getAnnotation(final Class<T> annotationClass) {
    return null;
  }

  @Override
  public default void set(final Object instance, final Object value) {
    throw new UnsupportedOperationException("Cannot set this property");
  }

  class DefaultReadOnlyPropertyConduitImpl<T> implements DefaultReadOnlyPropertyConduit {
    private final Class<T> returnType;
    private final Function<Object, ? extends T> accessor;

    private DefaultReadOnlyPropertyConduitImpl(final Class<T> returnType,
        final Function<Object, ? extends T> accessor) {
      this.returnType = returnType;
      this.accessor = accessor;
    }

    @Override
    public Class<T> getPropertyType() {
      return returnType;
    }

    @Override
    public Object get(final Object instance) {
      return accessor.apply(instance);
    }
  }

}