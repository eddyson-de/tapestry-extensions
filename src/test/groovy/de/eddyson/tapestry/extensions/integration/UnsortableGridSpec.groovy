package de.eddyson.tapestry.extensions.integration
import de.eddyson.tapestry.extensions.integration.pages.UnsortableGridDemo
import de.eddyson.tapestrygeb.JettyGebSpec


class UnsortableGridSpec extends JettyGebSpec {

  def "Sort links are not rendered for unsortable grid"(){
    given:
    to UnsortableGridDemo
    expect:
    table.find('th').getAttribute('data-grid-column-sort') == "sortable"
    unsortableTable.find('th').getAttribute('data-grid-column-sort') == ""
  }
}