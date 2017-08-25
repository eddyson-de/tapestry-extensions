import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver

reportsDir = 'build/reports/geb'
baseUrl = "http://localhost:${System.properties['jettyPort']}/"

def sauce_username = System.properties['SAUCE_USERNAME']
def sauce_access_key = System.properties['SAUCE_ACCESS_KEY']
def tunnel_id = System.properties['TRAVIS_JOB_NUMBER']

driver = {
  DesiredCapabilities.firefox().with {
    setCapability("marionette", false);
    new FirefoxDriver(it)
  }
}

environments {
  'sauce_chrome' {
    URL baseUrl = new URL("http://${sauce_username}:${sauce_access_key}@localhost:4445/wd/hub")
    DesiredCapabilities capa = DesiredCapabilities.chrome()
    capa.setVersion("latest")
    capa.setCapability("tunnel-identifier", tunnel_id)

    driver = {
      new RemoteWebDriver(baseUrl,capa)
    }
  }
  'sauce_ie' {
    URL baseUrl = new URL("http://${sauce_username}:${sauce_access_key}@localhost:4445/wd/hub")
    DesiredCapabilities capa = DesiredCapabilities.internetExplorer()
    capa.setCapability("platform", "Windows 7");
    capa.setCapability("version", "11.0");
    capa.setCapability("tunnel-identifier", tunnel_id)

    driver = {
      new RemoteWebDriver(baseUrl,capa)
    }
  }
  'sauce_firefox' {
    URL baseUrl = new URL("http://${sauce_username}:${sauce_access_key}@localhost:4445/wd/hub")
    DesiredCapabilities capa = DesiredCapabilities.firefox()
    capa.setVersion("latest")
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
    capa.setCapability("platform", "OS X 10.10");
    capa.setCapability("version", "8.0");
    capa.setCapability("tunnel-identifier", tunnel_id)

    driver = {
      new RemoteWebDriver(baseUrl,capa)
    }
  }
}
environments.each{
  env ->
    env.waiting {
      timeout = 30
      retryInterval = 1.0
    }
}
