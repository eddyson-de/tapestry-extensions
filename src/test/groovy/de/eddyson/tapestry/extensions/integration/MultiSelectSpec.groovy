package de.eddyson.tapestry.extensions.integration
import de.eddyson.tapestry.extensions.integration.pages.MultiSelectDemo
import de.eddyson.tapestrygeb.JettyGebSpec
import org.openqa.selenium.Keys


class MultiSelectSpec extends JettyGebSpec {

  def "Select some values"(){
    given:
    to MultiSelectDemo
    when:
    searchField << 'Hel'
    searchField << Keys.ENTER
    searchField << 'Wor'
    searchField << Keys.ENTER
    submit.click()
    then:
    selectedValues.text().contains ('Hello, World')
  }
}