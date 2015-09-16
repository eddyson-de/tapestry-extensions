package de.eddyson.tapestry.extensions.integration.pages

import de.eddyson.tapestrygeb.TapestryPage
import geb.Page

class MultiSelectDemo extends TapestryPage {

  static url = "multiselectdemo"

  static at = { title == "MultiSelect Demo" }

  static content = {
    select { $("select") }
    searchField { $(".select2-search__field") }
    submit { $("input[type=submit]") }
    selectedValues { $("span.selected") }
  }
}
