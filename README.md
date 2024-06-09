SauceDemo Test Automation
This repository contains a test automation framework for the SauceDemo application. The framework uses Selenium for browser automation, Cucumber for behavior-driven development (BDD), and Extent Reports for reporting test results.


## Table of Contents

Prerequisites

Installation

Configuration

Running Tests

Reporting



## Prerequisites
Before you begin, ensure you have the following installed:

Java JDK 8 or higher

Maven

Git

Chrome browser (or any other browser you wish to test on)





## Installation

1. Clone the repository

        git clone https://github.com/your-username/saucedemo-test-automation.git

cd saucedemo-test-automation

2. Install dependencies

        mvn clean install
## Running Tests

You can run the tests using Maven commands.
    
Run all tests
        
        mvn test


## Extent Reports
After running the tests, reports are generated in the test-output directory under ExtentReport(with date and time of execution)/Report.html.