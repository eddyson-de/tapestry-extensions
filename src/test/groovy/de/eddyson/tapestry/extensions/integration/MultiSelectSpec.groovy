import de.eddyson.tapestrygeb.JettyGebSpec;

class MultiSelectSpec extends JettyGebSpec {
  
  def "Select some values"(){
    given:
    go "multiselectdemo"
    when:
    $('select') << 'Hel\n'
    $('select') << 'Wor\n'
    $('submit').click()
    then:
    $('.selected').text().contains ('Hello, World')
  }
  
}