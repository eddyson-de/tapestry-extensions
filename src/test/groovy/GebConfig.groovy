import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver

reportsDir = 'build/reports/geb'
baseUrl = "http://localhost:${System.properties['jettyPort']}/"
environments {
  'travis' {
    def sauce_username = System.properties['SAUCE_USERNAME']
    def sauce_access_key = System.properties['SAUCE_ACCESS_KEY']
    def tunnel_id = System.properties['TRAVIS_JOB_NUMBER']
    URL baseUrl = new URL("http://${sauce_username}:${sauce_access_key}@localhost:4445/wd/hub")
    DesiredCapabilities capa = DesiredCapabilities.chrome()
    capa.setCapability("tunnel-identifier", tunnel_id)
x
    driver = {
      new RemoteWebDriver(baseUrl,capa)
    }
  }
}