package de.eddyson.tapestry.extensions.integration.pages

import de.eddyson.tapestrygeb.TapestryPage

class DefaultGridSortDemo extends TapestryPage {

  static url = "defaultgridsortdemo"

  static at = { title == "DefaultGridSort Demo" }

  static content = {
    upperTable { $("table" ,0) }
    lowerTable { $("table" ,1) }
  }
}
