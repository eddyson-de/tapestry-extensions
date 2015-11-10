![travis](https://travis-ci.org/eddyson-de/tapestry-extensions.svg?branch=master) Chrome & Firefox (current beta) & Internet Explorer 11

# Tapestry Extensions

[![Join the chat at https://gitter.im/eddyson-de/tapestry-extensions](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/eddyson-de/tapestry-extensions?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

Collection of useful(?) components and mixins for Apache Tapestry.
Disclaimer: Some may not be ready for production. Use at your own risk.

## General Usage
* Add the dependency to your `build.gradle`.
* Declare library as xml namespace and
* use the components with your defined prefix.

```html
<html xmlns:etc="tapestry-library:EddysonTapestryExtensions">
    <etc:multiselect></etc:multiselect>
</html>
```

## Components

### MultiSelect

Multiple selections based on [Select2](https://select2.github.io/).

```html
<html xmlns:t="http://tapestry.apache.org/schema/tapestry_5_4.xsd"
      xmlns:etc="tapestry-library:EddysonTapestryExtensions">
    <etc:multiselect blankLabel="Select..." model="model" 
    encoder="encoder" selected="selected" multiple="true"></etc:multiselect>
</html>
```

### Tagging

Tagging component with ajax search based on [Select2](https://select2.github.io/).

Template `initialTags` parameter optional)
```html
<t:form>
	<etc:tagging t:id="tagging1" tags="tags" initialTags="['First','Second']" ></etc:tagging>
	<t:submit />
</t:form>
```

Containing Page
```java
public class TaggingDemo {
  //Bound "tags" parameter will contain submitted tags.
  @Property
  @Persist
  List<String> tags;

  //Event gets fired while typing into the field.
  //Evalutate query string and return suggestions.
  @OnEvent(value = "completeTags")
  Object completions(String query){
    //Return Strings based on query
    //List<String> list = dao.search(query);
    return list;
  }
}
```

##Mixins

### PaletteFilter

Filter mixin for the "Palette" core component.

![small](https://cloud.githubusercontent.com/assets/5182212/9811523/1bd3f4f0-5878-11e5-80f5-7d02e22c6d63.gif)

```html
<html xmlns:t="http://tapestry.apache.org/schema/tapestry_5_4.xsd">
    <t:palette t:id="palette" t:mixins="EddysonTapestryExtensions/PaletteFilter" 
    t:selected="selected" model="model" t:encoder="encoder"/>
</html>
```

### DefaultGridSort

Sort a Grid by a specific column by default

```html
<html xmlns:t="http://tapestry.apache.org/schema/tapestry_5_4.xsd">
    <t:grid source="..." t:mixins="EddysonTapestryExtensions/DefaultGridSort" DefaultGridSort.sortColumn="firstName" DefaultGridSort.sortOrder="ascending"/>
</html>
```

### UnsortableGrid

Make a grid unsortable

```html
<html xmlns:t="http://tapestry.apache.org/schema/tapestry_5_4.xsd">
    <t:grid source="..." t:mixins="EddysonTapestryExtensions/UnsortableGrid" />
</html>
```

### InfiniGrid

Use AJAX to load new Grid rows dynamically while scrolling, requires `pagerPosition="none"` and `inplace="true"` parameters to be set for the Grid.


```html
<html xmlns:t="http://tapestry.apache.org/schema/tapestry_5_4.xsd">
    <t:grid source="..." inplace="true" pagerPosition="none" t:mixins="EddysonTapestryExtensions/InfiniGrid" />
</html>
```

### FadeOnRefresh

Fade out a zone when it is updating and fade it back in afterwards.


```html
<html xmlns:t="http://tapestry.apache.org/schema/tapestry_5_4.xsd">
    <t:zone t:id="zone" t:mixins="ZoneRefresh,EddysonTapestryExtensions/FadeOnRefresh" ZoneRefresh.period="2" >
				I am a zone
    </t:zone>
</html>
```