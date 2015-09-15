package de.eddyson.tapestry.extensions.integration
import de.eddyson.tapestry.extensions.integration.pages.InfiniGridDemo
import de.eddyson.tapestrygeb.JettyGebSpec


class InfiniGridSpec extends JettyGebSpec {

  def scrollBody(to){
    def from = infinigridBody.jquery.scrollTop()
    (from..to).findAll{it % 10 == 0}.each{infinigridBody.jquery.scrollTop(it)}
  }

  def "Scoll through an InfiniGrid"(){
    given:
    to InfiniGridDemo

    expect:
    waitFor {
      rows.size() == 10
    }
    when:
    scrollBody(150)
    then:
    waitFor {
      rows.size() == 15
    }
    when:

    scrollBody(300)
    then:
    waitFor {
      rows.size() == 20
    }
  }
}