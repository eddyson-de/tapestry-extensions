package de.eddyson.tapestry.extensions.integration.pages

import de.eddyson.tapestrygeb.TapestryPage

class MultiSelectDemo extends TapestryPage {

  static url = "multiselectdemo"

  static content = {
    select { $("select") }
    searchField { $("input[type=search]") }
    submit { $("input[type=submit]") }
    selectedValues { $("span.selected") }
  }
}
