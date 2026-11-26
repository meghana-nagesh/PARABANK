# ParaBank - Selenium Automation (Java + TestNG + Maven)

Automates 32 of the 55 manual test cases of the ParaBank banking demo site (https://parabank.parasoft.com).
Every test method starts with its manual TC ID. Test types: smoke, functional, negative, boundary, integration, security, UI and cross-browser.

Tools: Java 17, Selenium WebDriver 4, TestNG, Maven. Design: Page Object Model, explicit waits, failure screenshots through a TestNG listener, settings in `config.properties`.

## Project structure
```
pom.xml                       libraries and Maven settings
testng.xml                    full suite (all 32 tests)
testng-smoke.xml              quick suite (tests in group "smoke")
testng-crossbrowser.xml       cross-browser suite (group "crossbrowser")
src/main/java/.../pages       one class per ParaBank page (locators and actions)
src/main/java/.../utils       Config (reads config.properties), UserData
src/test/java/.../base        BaseTest (opens and closes the browser, login helper)
src/test/java/.../listeners   TestListener (screenshot of every failed test)
src/test/java/.../tests       the test classes
src/test/resources            config.properties
```

## Automated test cases
| TC ID | Scenario | Type | Test method | Status |
|---|---|---|---|---|
| PB_TC_001 | Register a new customer | Functional | RegisterTests.PB_TC_001_registerNewCustomer | Passed |
| PB_TC_002 | Registration with all fields blank | Negative | RegisterTests.PB_TC_002_registerWithBlankFields | Passed |
| PB_TC_003 | Registration with mismatched passwords | Negative | RegisterTests.PB_TC_003_registerPasswordMismatch | Passed |
| PB_TC_004 | Registration with an existing username | Negative | RegisterTests.PB_TC_004_registerExistingUsername | Failed - script (stale element), fixed |
| PB_TC_007 | Login with valid credentials | Smoke | LoginTests.PB_TC_007_loginWithValidCredentials | Passed |
| PB_TC_008 | Login with wrong password | Negative | LoginTests.PB_TC_008_loginWithInvalidCredentials | Failed - script (stale element), fixed |
| PB_TC_009 | Login with an invalid username | Negative | LoginTests.PB_TC_009_loginWithInvalidUsername | Failed - site logged in with an unknown username (possible defect) |
| PB_TC_010 | Login with blank username and password | Negative | LoginTests.PB_TC_010_loginWithBlankCredentials | Passed |
| PB_TC_012 | Password is case sensitive | Negative | LoginTests.PB_TC_012_passwordIsCaseSensitive | Failed - site accepted a wrong-case password (possible defect) |
| PB_TC_013 | Log out | Smoke | LoginTests.PB_TC_013_logout | Passed |
| PB_TC_016 | Accounts Overview table and total | Functional | AccountTests.PB_TC_016_accountsOverviewTable | Passed |
| PB_TC_018 | Open a new SAVINGS account | Functional | AccountTests.PB_TC_018_openSavingsAccount | Passed |
| PB_TC_019 | Open a new CHECKING account | Smoke | AccountTests.PB_TC_019_openCheckingAccount | Passed |
| PB_TC_020 | New account is listed in Accounts Overview | Integration | AccountTests.PB_TC_020_newAccountListed | Passed |
| PB_TC_021 | Transfer funds between two own accounts | Smoke | TransferTests.PB_TC_021_transferFunds | Passed |
| PB_TC_022 | Balances updated after a transfer | Integration | TransferTests.PB_TC_022_balancesUpdatedAfterTransfer | Passed |
| PB_TC_023 | Transfer with a blank amount | Negative | TransferTests.PB_TC_023_transferBlankAmount | Passed |
| PB_TC_024 | Transfer with a non-numeric amount | Negative | TransferTests.PB_TC_024_transferNonNumericAmount | Passed |
| PB_TC_025 | Pay a bill with valid payee details | Smoke | BillPayTests.PB_TC_025_billPay | Passed |
| PB_TC_026 | Bill pay with all fields blank | Negative | BillPayTests.PB_TC_026_billPayBlankFields | Passed |
| PB_TC_027 | Bill pay with mismatched account numbers | Negative | BillPayTests.PB_TC_027_billPayAccountMismatch | Passed |
| PB_TC_031 | Find transactions by amount | Functional | FindTransactionsTests.PB_TC_031_findTransactionsByAmount | Passed |
| PB_TC_033 | Update address, city and phone | Functional | ProfileTests.PB_TC_033_updateContactInfo | Passed |
| PB_TC_035 | Updated details saved after login again | Integration | ProfileTests.PB_TC_035_updatedDetailsSaved | Failed - script wait, fixed |
| PB_TC_036 | Request a loan with valid details | Functional | LoanTests.PB_TC_036_requestLoan | Passed |
| PB_TC_038 | Loan denied for a very high down payment | Boundary | LoanTests.PB_TC_038_loanDeniedForHighDownPayment | Passed |
| PB_TC_039 | All left menu links open the correct pages | Smoke | NavigationTests.PB_TC_039_leftMenuLinks | Passed |
| PB_TC_041 | Transfer with zero amount | Boundary | TransferTests.PB_TC_041_transferZeroAmount | Failed - zero transfer accepted (defect) |
| PB_TC_043 | Transfer with a decimal amount | Boundary | TransferTests.PB_TC_043_transferDecimalAmount | Passed |
| PB_TC_050 | Account page cannot be opened without login | Security | SecurityTests.PB_TC_050_accountPageNotOpenWithoutLogin | Passed |
| PB_TC_051 | Home page UI elements and links | UI | UiTests.PB_TC_051_homePageUi | Passed |
| PB_TC_054 | Login and transfer in another browser | Compatibility | CrossBrowserTests.PB_TC_054_mainFlowInAnotherBrowser | Passed |

Status is from the last full run: 26 passed, 6 failed. Update the Status column after every run.

## Before you run - create your customer ONCE by hand
1. Open https://parabank.parasoft.com/parabank/index.htm in Chrome and click **Register**.
2. Fill the form with any details and a username and password of your choice, and click Register (tick the 'verify you are human' box if it appears).
3. Open `src/test/resources/config.properties` and put that username and password in `parabank.username` and `parabank.password`.
4. Do not push your real password to GitHub: put `your_username` / `your_password` back before you commit.
If login tests start failing with 'could not be verified', the demo site may have reset its data. Register the customer again.

## If a 'verify you are human' box appears
Tick it by hand. The waiting time is 60 seconds (`timeout` in config.properties). Do not try to bypass it.
The registration tests (PB_TC_001 to 004) open the registration page themselves, so they may need this too.

## How to run
1. Install JDK 17 or above, Maven and Google Chrome.
2. In the project folder run: `mvn clean test`
3. Smoke tests only: `mvn clean test -DsuiteFile=testng-smoke.xml`
4. Another browser: `mvn clean test -DsuiteFile=testng-crossbrowser.xml -Dbrowser=firefox` (or `edge`)
5. Without a browser window: set `headless=true` in `src/test/resources/config.properties`, or add `-Dheadless=true`.
6. Screenshots of failed tests are in `target/screenshots`. The TestNG report is in `target/surefire-reports`.

## Import into Eclipse
File > Import > Maven > Existing Maven Projects > select the `parabank-automation` folder > Finish.
Wait for Maven to download the libraries, then right-click `testng.xml` > Run As > TestNG Suite.

## Notes
* Every test registers nothing new except the registration tests. All other tests log in with the customer from `config.properties`.
* Tests that need two accounts open a SAVINGS account first if the customer has fewer than two, so the account list grows a little on every run.
* A failure is not always a bug. First check the script (locators are in the page classes), then reproduce by hand. Only report it in the Bug Report if it also fails manually.
* PB_TC_041 failed because the site accepts a zero-amount transfer. Reported as a defect.
* PB_TC_009 and PB_TC_012 fail when the site logs in with an unknown username or a wrong-case password. Confirm by hand before reporting.
