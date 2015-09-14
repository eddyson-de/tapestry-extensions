package de.eddyson.tapestry.extensions.integration.pages

import de.eddyson.tapestrygeb.TapestryPage

class PaletteFilterDemo extends TapestryPage {

  static url = "palettefilterdemo"

  static at = { title == "PaletteFilter Demo" }

  static content = {
    availableList { $("div.palette-available > select") }
    selectedList { $("div.palette-selected > select") }
    selectButton { $("button[data-action='select']") }
    filter {$("#filter-input-for-palette")}
    jane { $("option", value:"Jane") }
    john { $("option", value:"John") }
    joosie { $("option", value:"Joosie") }
  }
}
