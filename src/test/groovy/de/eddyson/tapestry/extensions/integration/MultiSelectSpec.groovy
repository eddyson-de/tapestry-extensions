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

    then:
    waitFor {
      liveUpdate.text().contains("[Hello]")
    }
    when:
    searchField << 'Wor'
    searchField << Keys.ENTER

    then:
    waitFor {
      liveUpdate.text().contains("[Hello, World]")
    }

    when:
    submit.click()

    then:
    selectedValues.text().contains ('Hello, World')
  }

  def "Submit single value"(){
    given:
    to MultiSelectDemo

    when:
    $("#select2-single-container").click()
    waitFor {
      searchFieldSingle.isPresent()
    }
    searchFieldSingle << 'Fo'
    searchFieldSingle << Keys.ENTER
    submitSingle.click()

    then:
    selectedValueSingle.text().contains('[Foo]')
  }

  def "Live update event does not send blankOption value"(){
    given:
    to MultiSelectDemo

    when:
    $("#select2-single-container").click()
    waitFor {
      searchFieldSingle.isPresent()
    }
    searchFieldSingle << 'Fo'
    searchFieldSingle << Keys.ENTER
    submitSingle.click()

    then:
    selectedValueSingle.text().contains('[Foo]')

    when:
    $(".select2-selection__clear").click()
    $("#select2-single-container").click()

    then:
    waitFor {
      liveUpdateSingle.text().contains('[]')
    }
  }

}