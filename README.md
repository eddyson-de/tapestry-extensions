# Tapestry Extensions

Collection of useful(?) components and mixins for Apache Tapestry.
Disclaimer: Some may not be ready for production. Use at your own risk.

## General Usage
* Add the dependency to you `build.gradle`.
* Declare library as xml namespace and
* use the components with your defined prefix.

```html
<html xmlns:etc="tapestry-library:components">
    <etc:multiselect></etc:multiselect>
</html>
```

## Components

### MultiSelect

Multiple selections based on [Select2](https://select2.github.io/).

```html
<html xmlns:t="http://tapestry.apache.org/schema/tapestry_5_3.xsd"
      xmlns:etc="tapestry-library:components">
    <etc:multiselect blankLabel="Select..." model="model" 
    encoder="encoder" selected="selected" multiple="true"></etc:multiselect>
</html>
```

##Mixins

### PaletteFilter

Filter mixin for the "Palette" core component.

```html
<html xmlns:t="http://tapestry.apache.org/schema/tapestry_5_3.xsd">
    <t:palette t:id="palette" t:mixins="components/PaletteFilter" 
    t:selected="selected" model="model" t:encoder="encoder"/>
</html>
```


