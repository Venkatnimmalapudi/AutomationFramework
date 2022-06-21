Sample Framework to both API and WebUI.

- Uses JAVA 11
- Uses TestNG for test execution and reports
- Uses Maven build tool for building dependencies and enable running tests from command line.
- Log4j2 for logging.
- Retry mechanism via annotation at test method level along with regular retry. Need to add Annotation : 
@RetryCount(value=3) on top of the method to utilize it.
- CSV reading is enabled in Dataprovider methods.
- JsonUtils helps to validate two json responses for equality.

- API : Rest Assured Framework.
  - Sample Command to run : mvn clean test -Dgroups=api
- WEBUI : Selenium Webdriver with Page Factory Pattern.
  - Sample Command to run : mvn clean test -Dgroups=webui -Dbrowser=chrome -Denv=staging
  - Browser logs

- Both API tests and UI tests uses groups for filtering of tests.
- Reporting : TestNG reports
- logs : 
  - application : It contains application logs built via log4j2
  - browser : If UI test fails, browser logs will be dumped here
  - screenshots : If UI test fails, screenshots are taken and dumped here.
