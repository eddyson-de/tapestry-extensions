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
    selectSingleContainer.click()
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
    selectSingleContainer.click()
    waitFor {
      searchFieldSingle.isPresent()
    }
    searchFieldSingle << 'Fo'
    searchFieldSingle << Keys.ENTER
    submitSingle.click()

    then:
    selectedValueSingle.text().contains('[Foo]')
    waitFor {
      selectSingleClear.displayed
    }
    when:
    selectSingleClear.click()
    selectSingleContainer.click()

    then:
    waitFor {
      liveUpdateSingle.text().contains('[]')
    }
  }

  def "MultiSelect in a BeanEditor"(){
    given:
    to MultiSelectDemo

    when:
    selectBeaneditorContainer.click()
    waitFor {
      searchFieldBeaneditor.isPresent()
    }
    searchFieldBeaneditor << 'Fo'
    searchFieldBeaneditor << Keys.ENTER
    submitBeaneditor.click()

    then:
    selectedValueBeaneditor.text().contains('[Foo]')
  }
}