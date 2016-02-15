package de.eddyson.tapestry.extensions.integration
import de.eddyson.tapestry.extensions.integration.pages.DefaultGridSortDemo
import de.eddyson.tapestrygeb.JettyGebSpec


class DefaultGridSortSpec extends JettyGebSpec {

  def "Sort table ascending and descending by default"(){
    given:
    to DefaultGridSortDemo
    expect:

    upperTable.find('th').first().getAttribute('data-grid-column-sort') == "ascending"
    upperTable.find('td').first().text() == "Jane"
    lowerTable.find('th').first().getAttribute('data-grid-column-sort') == "descending"
    lowerTable.find('td').first().text() == "John"
  }
}