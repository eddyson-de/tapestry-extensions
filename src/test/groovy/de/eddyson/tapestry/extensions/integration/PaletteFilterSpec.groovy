package de.eddyson.tapestry.extensions.integration

import de.eddyson.tapestry.extensions.integration.pages.PaletteFilterDemo
import de.eddyson.tapestrygeb.JettyGebSpec
import org.openqa.selenium.Keys


class PaletteFilterSpec extends JettyGebSpec {
  def "Filter list"(){
    given:
    to PaletteFilterDemo
    availableList.children()[0].value() == "John"
    availableList.children()[1].value() == "Jane"
    availableList.children()[2].value() == "Joosie"
    !(john.css("display") == "none")
    !(joosie.css("display") == "none")
    !(jane.css("display") == "none")

    when:
    filter << "j"
    filter << "o"

    then:
    waitFor{
      jane.css("display") == "none"
      !(john.css("display") == "none")
      !(joosie.css("display") == "none")
    }

    when:
    filter << "o"

    then:
    waitFor{
      jane.css("display") == "none"
      john.css("display") == "none"
      !(joosie.css("display") == "none")
    }

    when:
    filter << Keys.BACK_SPACE
    filter << Keys.BACK_SPACE
    filter << Keys.BACK_SPACE
    filter << "ja"

    then:
    waitFor {
      !(jane.css("display") == "none")
      john.css("display") == "none"
      joosie.css("display") == "none"

    }
  }
}
