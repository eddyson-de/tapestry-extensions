package de.eddyson.tapestry.extensions.integration.pages

import de.eddyson.tapestrygeb.TapestryPage

class InfiniGridDemo extends TapestryPage {

  static url = "infinigriddemo"

  static at = { title == "InfiniGrid Demo" }

  static content = {
    table { $("#table") }
    rows { table.find('tbody tr') }
    infinigridBody { $(".infinigridBody") }
  }
}
