package de.eddyson.tapestry.extensions.integration
import de.eddyson.tapestry.extensions.integration.pages.DefaultGridSortDemo
import de.eddyson.tapestrygeb.JettyGebSpec


class DefaultGridSortSpec extends JettyGebSpec {

  def "Sort table ascending and descending by default"(){
    given:
    to DefaultGridSortDemo
    expect:

    upperTable.find('th').getAttribute('data-grid-column-sort') == "ascending"
    upperTable.find('td').text() == "Jane"
    lowerTable.find('th').getAttribute('data-grid-column-sort') == "descending"
    lowerTable.find('td').text() == "John"
  }
}