import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.checkpoint.CheckpointFactory as CheckpointFactory
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as MobileBuiltInKeywords
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testcase.TestCaseFactory as TestCaseFactory
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testdata.TestDataFactory as TestDataFactory
import com.kms.katalon.core.testobject.ObjectRepository as ObjectRepository
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WSBuiltInKeywords
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUiBuiltInKeywords
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil

WebUI.openBrowser('')

WebUI.navigateToUrl('http://testing-ground.scraping.pro/table')

'Expected value from Table'
//String ExpectedYear = '2000'

//String ExpectedQ = 'Q4'

Double sumQ = 0

WebDriver driver = DriverFactory.getWebDriver()

WebElement Table = driver.findElement(By.xpath('//table/tbody'))

List<WebElement> Rows = Table.findElements(By.tagName('tr'))

Integer sectionStart = 0

table: for (int i = 0; i < Rows.size(); i++) {
    List<WebElement> Cols = Rows.get(i).findElements(By.tagName('td'))
    for (int j = 0; j < Cols.size(); j++) {
        if (Cols.get(j).getText().equalsIgnoreCase(year)) {
            sectionStart = i
			break
        }
    }
}

println('sectionStart: ' + sectionStart)

table: for (int i = sectionStart; i < (sectionStart + 6); i++) {
    List<WebElement> Cols = Rows.get(i).findElements(By.tagName('td'))

	while (Cols.get(0).getText() == quarter) {
		for (int j = 0; j < Cols.size(); j++) {
			println('ColsSize: ' + Cols.size().toString())
			if (Cols.get(j).getText().contains('$') && j < Cols.size() - 1) {
				println('Actual: ' + Cols.get(j).getText())
				sumQ += Double.parseDouble(Cols.get(j).getText().replaceAll('[^\\d.]', ''))
			}
			else if (j == Cols.size() - 1) {
				println('Actual: ' + Cols.get(j).getText() + "      " + sumQ)
				if (sumQ == Double.parseDouble(Cols.get(j).getText().replaceAll('[^\\d.]', ''))) {
					KeywordUtil.markPassed('Amount equals')
					break
				}
				else {
					KeywordUtil.markFailed('Amount is not equal')
					break
				}
			}
		}
		break
	}
}

WebUI.closeBrowser()
