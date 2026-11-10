# AI Usage Report (REPORT_AI.md)

## 1. AI Tools Used

* **Gemini (Google)** – Used for code review, identifying logical and maintainability issues, and suggesting refactoring improvements for TestNG and Selenium best practices.

## 2. Prompts / Requests Made

1. **Code Analysis & Issue Identification:**  
   > "Review the `OldTest` Java test class. Identify issues related to performance, readability, test flakiness, and logical errors."

2. **Refactoring & Best Practices:**  
   > "Provide a refactored version of the code adhering to Selenium and TestNG best practices, including proper test lifecycle management (`@BeforeMethod` / `@AfterMethod`) and reliable element locators."

## 3. Utilization of AI Responses & Decision Making

### A. Accepted & Implemented Suggestions:

* **Logic Order Fix:** The AI correctly identified that price elements were fetched *before* applying the dropdown sort (`selectByValue("lohi")`). Reordered the sequence to extract prices only after the DOM updates following the sort action.
* **Locator Optimization:** Replaced the fragile absolute XPath (`/html/body/...`) with a stable ID locator (`By.id("user-name")`).
* **Test Lifecycle Management:** Removed the `static` WebDriver instantiation and introduced `@BeforeMethod` and `@AfterMethod` annotations to ensure clean browser initialization and closure for each test run.

### B. Rejected Suggestion (Mandatory Rejection Documentation):

* **Suggested Approach:** The AI recommended using complex Java Streams with custom string parsing Comparators in memory, as well as executing JavaScript snippets via `JavascriptExecutor` to extract price data directly from the DOM elements.
* **Reason for Rejection:** The suggested approach introduced unnecessary complexity and overhead to a simple validation task. A clean, straightforward `for` loop converting text to `double` and comparing consecutive values maintains high code readability, reduces maintainability overhead, and ensures the test remains easy to debug and understand for the QA team without relying on complex functional streams.