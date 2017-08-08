package de.eddyson.tapestry.extensions.integration.pages

import de.eddyson.tapestrygeb.TapestryPage
import geb.Page

class MultiSelectDemo extends TapestryPage {

  static url = "multiselectdemo"

  static at = { title == "MultiSelect Demo" }

  static content = {
    select { $("#multi") }
    searchField { $("#multiForm").find(".select2-search__field") }
    submit { $("#multiForm").find("input[type=submit]") }
    selectedValues { $("span.selected") }
    liveUpdate { $("p.liveUpdate") }

    selectSingle { $("#single")}
    formSingle { $("#formSingle") }
    selectSingleContainer { $("#select2-single-container") }
    selectSingleClear { formSingle.find(".select2-selection__clear") }
    searchFieldSingle { $(".select2-search--dropdown").find(".select2-search__field") }
    selectedValueSingle { $("span.selectedSingle") }
    submitSingle { formSingle.find("input[type=submit]") }
    liveUpdateSingle { $("p.liveUpdateSingle")}
  }
}
