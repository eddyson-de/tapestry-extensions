package de.eddyson.tapestry.extensions.integration

import de.eddyson.tapestry.extensions.integration.pages.FadeOnRefreshDemo
import de.eddyson.tapestrygeb.JettyGebSpec


class FadeOnRefreshSpec extends JettyGebSpec {
  def "Zone fades in and out when refreshing list"(){
    given:
    to FadeOnRefreshDemo

    expect:
    waitFor(5, 0.2){
      zone.css("opacity").toDouble() < 1
    }
    waitFor(5, 0.2){
      zone.css("opacity") == "1"
    }
  }
}
