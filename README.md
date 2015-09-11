# Tapestry Extensions

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



