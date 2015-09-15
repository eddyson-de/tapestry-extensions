import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver

reportsDir = 'build/reports/geb'
baseUrl = "http://localhost:${System.properties['jettyPort']}/"
environments {
  'travis' {
    def sauce_username = System.properties['SAUCE_USERNAME']
    def sauce_access_key = System.properties['SAUCE_ACCESS_KEY']
    def tunnel_id = System.properties['TRAVIS_JOB_NUMBER']
    baseUrl = "http://${sauce_username}:${sauce_access_key}@localhost:4445/wd/hub"
    def capa = DesiredCapabilities.chrome()
    capa['tunnel-identifier'] = tunnel_id
    driver = {
      new RemoteWebDriver(baseUrl,capa)
    }
  }
}