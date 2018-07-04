package olympicsMedals;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class OlympicsMedals {
	WebDriver driver;

	public Object getKeyFromValue(Map m, Object value) {
		for (Object o : m.keySet()) {
			if (m.get(o).equals(value)) {
				return o;
			}
		}
		return null;
	}

	@BeforeClass
	public void setup() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// driver.manage().window().fullscreen();
	}

	@BeforeMethod
	public void getUrl() {
		driver.get("https://en.wikipedia.org/wiki/2016_Summer_Olympics#Medal_table");

	}

	@Test(priority = 1) // sorted by rank task 2
	public void sortTest() {
		// driver.get("https://en.wikipedia.org/wiki/2016_Summer_Olympics#Medal_table");

		Set<Integer> setT = new HashSet<>();
		SortedSet<Integer> sorted = new TreeSet<>();
		List<WebElement> ranks = driver.findElements(
				By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']//tr/td[1]"));
		for (int i = 0; i < ranks.size() - 1; i++) {
			int a = Integer.parseInt(ranks.get(i).getText());
			setT.add(a);
			sorted.add(a);
		}

		assertEquals(setT, sorted);

		// ===================================================================
		// scrolling down
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,800)");
		// clicking noc, task 3
		WebElement noc = driver.findElement(By.xpath("(//th[.='NOC'])[2]"));
		noc.click();
		// task 4
		List<WebElement> nocCountries = driver.findElements(
				By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']//th/a"));
		List<String> setCountries = new ArrayList<>();
		SortedSet<String> sortedCountries = new TreeSet<>();
		for (int i = 0; i < nocCountries.size() - 1; i++) {
			setCountries.add(nocCountries.get(i).getText());
			sortedCountries.add(nocCountries.get(i).getText());
		}

		assertEquals(setCountries, sortedCountries);

		// =====================================================================================
		// task 5
		List<Integer> setRank = new ArrayList<>();
		List<WebElement> ranksNotAscending = driver.findElements(
				By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']//tr/td[1]"));
		for (int i = 0; i < ranksNotAscending.size() - 1; i++) {
			int rankNotAscendingInt = Integer.parseInt(ranksNotAscending.get(i).getText());
			setRank.add(rankNotAscendingInt);
		}
		assertNotEquals(setT, setRank);
	}

	@Test(priority = 2) // test case 2
	public void theMost() throws InterruptedException {
		// driver.get("https://en.wikipedia.org/wiki/2016_Summer_Olympics");
		// task 2
		// scrolling down
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,800)");

		List<WebElement> gold = driver.findElements(
				By.xpath("//table[@class='wikitable sortable plainrowheaders " + "jquery-tablesorter']//tr/td[2]"));

		List<WebElement> nocCountries = driver.findElements(
				By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']//th/a"));

		Map<String, Integer> maxGold = new HashMap<>();

		for (int i = 0; i < 10; i++) {
			maxGold.put(nocCountries.get(i).getText(), Integer.valueOf(gold.get(i).getText()));
		}

		Integer maxGoldMedals = Collections.max(maxGold.values());

		String country = (String) this.getKeyFromValue(maxGold, maxGoldMedals);

		System.out.println(country + " : " + maxGoldMedals + " - is the country with the most number of gold medals");

		// task 3 country with most silver medals
		Thread.sleep(3000);
		List<WebElement> silverNum = driver.findElements(
				By.xpath("//table[@class='wikitable sortable plainrowheaders " + "jquery-tablesorter']//tr/td[3]"));

		Map<String, Integer> silverMap = new HashMap<>();
		for (int i = 0; i < 10; i++) {
			silverMap.put(nocCountries.get(i).getText(), Integer.valueOf(silverNum.get(i).getText()));
		}
		Integer maxSilverMedals = Collections.max(silverMap.values());
		String countrySilver = (String) this.getKeyFromValue(silverMap, maxSilverMedals);
		System.out.println(
				countrySilver + " : " + maxSilverMedals + " - is the country with the most number of silver medals");

		// task 4, country with most bronze medals

		List<WebElement> bronzeNum = driver.findElements(
				By.xpath("//table[@class='wikitable sortable plainrowheaders " + "jquery-tablesorter']//tr/td[4]"));
		Map<String, Integer> bronzeMap = new HashMap<>();
		for (int i = 0; i < 10; i++) {
			bronzeMap.put(nocCountries.get(i).getText(), Integer.valueOf(bronzeNum.get(i).getText()));
		}
		Integer maxBronzeMedals = Collections.max(bronzeMap.values());
		String countryBronze = (String) this.getKeyFromValue(bronzeMap, maxBronzeMedals);
		System.out.println(
				countryBronze + " : " + maxBronzeMedals + " - is the country with the most number of bronze medals");
		// task 5, country with the most number of total medals
		List<WebElement> totalNum = driver.findElements(
				By.xpath("//table[@class='wikitable sortable plainrowheaders " + "jquery-tablesorter']//tr/td[5]"));
		Map<String, Integer> totalMap = new HashMap<>();
		for (int i = 0; i < 10; i++) {
			totalMap.put(nocCountries.get(i).getText(), Integer.valueOf(totalNum.get(i).getText()));
		}
		Integer maxTotalMedals = Collections.max(totalMap.values());
		String countryTotal = (String) this.getKeyFromValue(totalMap, maxTotalMedals);
		System.out.println(
				countryTotal + " : " + maxTotalMedals + " - is the country with the most number of total medals");

		// task 6, testng for this testcase
		assertEquals(maxGoldMedals + "", "46");
		assertEquals(maxSilverMedals + "", "37");
		assertEquals(maxBronzeMedals + "", "38");
		assertEquals(maxTotalMedals + "", "121");

	}

	@Test(priority = 3)
	public void countryByMedal() {
		List<WebElement> nocCountries = driver.findElements(
				By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']//th/a"));
		List<WebElement> silverNum = driver.findElements(
				By.xpath("//table[@class='wikitable sortable plainrowheaders " + "jquery-tablesorter']//tr/td[3]"));
		Map<String, Integer> silver18Map = new HashMap<>();
		for (int i = 0; i < 10; i++) {
			if (Integer.parseInt(silverNum.get(i).getText()) == 18) {
				silver18Map.put(nocCountries.get(i).getText(), Integer.valueOf(silverNum.get(i).getText()));
				System.out.println(nocCountries.get(i).getText() + " is the country that has "
						+ Integer.valueOf(silverNum.get(i).getText()) + " silver medals");
			}
		}
	}

	@Test(priority = 4)
	public void getIndex() throws InterruptedException {
		System.out.println(getIndex("Japan"));
	}

	public String getIndex(String country) throws InterruptedException {
		String name = "";
		int row = 0;
		int column = 0;
		for (int i = 1; i < 2; i++) {
//			Thread.sleep(1000);
			for (int j = 1; j < 11; j++) {
//				Thread.sleep(1000);
				WebElement element = driver.findElement(
						By.xpath("(//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody//tr)["
								+ j + "]//th[" + i + "]/a"));
//				Thread.sleep(1000);
				if (country.equals(element.getText())) {
//					Thread.sleep(1000);
					name = "[Country name: " + country + "]   [Row: " + j + "]  [Column:  " + i + "]";
					break;
				}
			}
		}
		return name;
	}

	public String getCountry(String xpath) {
		List<WebElement> numberOfMedals = driver.findElements(By.xpath(xpath));
		List<WebElement> nocCountries = driver.findElements(
				By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']//th/a"));
		Map<String, Integer> numberAndCountryMap = new HashMap<>();
		for (int i = 0; i < 10; i++) {
			numberAndCountryMap.put(nocCountries.get(i).getText(), Integer.parseInt(numberOfMedals.get(i).getText()));
		}

		Integer maxMedals = Collections.max(numberAndCountryMap.values());

		String country = (String) this.getKeyFromValue(numberAndCountryMap, maxMedals);

		return country;
	}

	@Test(priority = 5) // test case 5
	public void sumOfBronzeMedals18() {
		List<WebElement> nocCountries = driver.findElements(
				By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']//th/a"));

		// ========================================================
		List<WebElement> bronzeNum = driver.findElements(
				By.xpath("//table[@class='wikitable sortable plainrowheaders " + "jquery-tablesorter']//tr/td[4]"));
		List<String> listCountries = new ArrayList<>();
		
		for(WebElement e:nocCountries) {
			System.out.println(e.getText());
		}
		Map<String, Integer> map1 = new HashMap<>();
		Map<String, Integer> map2 = new HashMap<>();
		int num = 0;
		for (int i = 0; i < nocCountries.size(); i++) {
			map1.put(nocCountries.get(i).getText(), Integer.parseInt(bronzeNum.get(i).getText()));
			map2.put(nocCountries.get(i).getText(), Integer.parseInt(bronzeNum.get(i).getText()));
		}
		
		System.out.println(Arrays.toString(map1.keySet().toArray()));
		
		System.out.println(Arrays.toString(map1.entrySet().toArray()));
		
		outer:for (Entry<String, Integer> ent : map1.entrySet()) {
			
			for (Entry<String, Integer> ent1 : map2.entrySet()) {
				if ((ent.getValue() + ent1.getValue() == 18) && (!(ent.getKey().equals(ent1.getKey())))) {
					listCountries.add(ent.getKey());
					listCountries.add(ent1.getKey());
					break outer;
				}
			}
		}
		for (String s : listCountries) {
			System.out.println("A list of two countries whose sum of bronze medals is 18: " + s);
		}
	}
}
