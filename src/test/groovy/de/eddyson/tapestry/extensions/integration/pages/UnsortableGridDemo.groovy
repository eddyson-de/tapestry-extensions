package de.eddyson.tapestry.extensions.integration.pages

import de.eddyson.tapestrygeb.TapestryPage

class UnsortableGridDemo extends TapestryPage {

  static url = "unsortablegriddemo"

  static at = { title == "UnsortableGrid Demo" }

  static content = {
    table { $("table" ,0) }
    unsortableTable { $("table" ,1) }
  }
}
