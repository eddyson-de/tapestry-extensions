package de.eddyson.tapestry.extensions.integration.pages

import de.eddyson.tapestrygeb.TapestryPage

class FadeOnRefreshDemo extends TapestryPage {

  static url = "fadeonrefreshdemo"

  static at = { title == "FadeOnRefresh Demo" }

  static content = {
    zone { $("#zone") }
  }
}
