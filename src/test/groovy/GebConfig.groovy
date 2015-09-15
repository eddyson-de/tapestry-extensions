import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver

reportsDir = 'build/reports/geb'
baseUrl = "http://localhost:${System.properties['jettyPort']}/"

def sauce_username = System.properties['SAUCE_USERNAME']
def sauce_access_key = System.properties['SAUCE_ACCESS_KEY']
def tunnel_id = System.properties['TRAVIS_JOB_NUMBER']
environments {
  'sauce_chrome' {
    URL baseUrl = new URL("http://${sauce_username}:${sauce_access_key}@localhost:4445/wd/hub")
    DesiredCapabilities capa = DesiredCapabilities.chrome()
    capa.setVersion("beta")
    capa.setCapability("tunnel-identifier", tunnel_id)

    driver = {
      new RemoteWebDriver(baseUrl,capa)
    }
  }
  'sauce_ie' {
    URL baseUrl = new URL("http://${sauce_username}:${sauce_access_key}@localhost:4445/wd/hub")
    DesiredCapabilities capa = DesiredCapabilities.internetExplorer()
    capa.setVersion("11")
    capa.setCapability("tunnel-identifier", tunnel_id)

    driver = {
      new RemoteWebDriver(baseUrl,capa)
    }
  }
  'sauce_firefox' {
    URL baseUrl = new URL("http://${sauce_username}:${sauce_access_key}@localhost:4445/wd/hub")
    DesiredCapabilities capa = DesiredCapabilities.firefox()
    capa.setVersion("beta")
    capa.setCapability("tunnel-identifier", tunnel_id)

    driver = {
      new RemoteWebDriver(baseUrl,capa)
    }
  }
  'sauce_ipad' {
    URL baseUrl = new URL("http://${sauce_username}:${sauce_access_key}@localhost:4445/wd/hub")
    DesiredCapabilities capa = DesiredCapabilities.ipad()
    capa.setCapability("tunnel-identifier", tunnel_id)

    driver = {
      new RemoteWebDriver(baseUrl,capa)
    }
  }
  'sauce_safari' {
    URL baseUrl = new URL("http://${sauce_username}:${sauce_access_key}@localhost:4445/wd/hub")
    DesiredCapabilities capa = DesiredCapabilities.safari()
    capa.setVersion("7")
    capa.setCapability("tunnel-identifier", tunnel_id)

    driver = {
      new RemoteWebDriver(baseUrl,capa)
    }
  }

}