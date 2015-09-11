package de.eddyson.tapestry.extensions.integration
import de.eddyson.tapestry.extensions.integration.pages.MultiSelectDemo
import de.eddyson.tapestrygeb.JettyGebSpec


class MultiSelectSpec extends JettyGebSpec {

  def "Select some values"(){
    given:
    to MultiSelectDemo
    when:
    searchField << 'Hel\nWor\n'
    submit.click()
    then:
    selectedValues.text().contains ('Hello, World')
  }
}