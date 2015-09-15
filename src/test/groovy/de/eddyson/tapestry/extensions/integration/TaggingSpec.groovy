package de.eddyson.tapestry.extensions.integration

import de.eddyson.tapestry.extensions.integration.pages.TaggingDemo
import de.eddyson.tapestrygeb.JettyGebSpec
import org.openqa.selenium.Keys


class TaggingSpec extends JettyGebSpec{

  def "Add tags with completion"(){
    given:
    to TaggingDemo

    when:
    search << "f"
    waitFor {$(".select2-results__option", text:"Foo").displayed}
    search << Keys.ARROW_DOWN
    search << Keys.ARROW_DOWN
    search << Keys.ENTER
    submit.click()

    then:
    taglist.children().size() == 1
    taglist.children()[0].text()== "Funny"

  }
}
