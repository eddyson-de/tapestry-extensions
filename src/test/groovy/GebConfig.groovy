import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.firefox.FirefoxDriver

reportsDir = "build/reports/geb"
baseUrl = "http://localhost:${System.properties['jettyPort']}/"

environments {
  'chrome-headless' {
    driver = {
      WebDriverManager.chromedriver().setup()
      ChromeOptions options = new ChromeOptions()
      options.setHeadless(true)
      options.addArguments('disable-gpu')
      new ChromeDriver(options)
    }
  }
  'firefox' {
    driver = {
      WebDriverManager.firefoxdriver().setup()
      new FirefoxDriver()
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
