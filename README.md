# SEL_HW4
تمرین چهارم آزمایشگاه مهندسی نرم‌افزار  
  درس: مهندسی نرم‌افزار  

موضوع: پیاده‌سازی و تست نرم‌افزار با رویکرد Behavior-Driven Development

## ۱. مقدمه و اهداف 

هدف اصلی این آزمایش، آشنایی عملی با رویکرد توسعه مبتنی بر رفتار (Behavior-Driven Development - BDD) در چرخه تولید نرم‌افزار است. در این روش، نیازمندی‌های سیستم به صورت سناریوهایی قابل فهم برای تمام اعضای تیم (تحلیل‌گر، توسعه‌دهنده و تستر) نوشته شده و این سناریوها مستقیماً به عنوان تست‌های پذیرش (Acceptance Tests) مورد استفاده قرار می‌گیرند.

در این پروژه، یک کلاس ماشین حساب ساده با قابلیت انجام چهار عمل اصلی ریاضی (جمع، تفریق، ضرب و تقسیم) بر روی دو عدد اعشاری پیاده‌سازی شد.

اهداف کلیدی:

۱-آشنایی با جایگاه تست در چرخه تولید نرم‌افزار.

۲-یادگیری و به‌کارگیری عملی BDD با ابزار Cucumber و زبان Gherkin.

۳-ایجاد ارتباط مستقیم بین نیازمندی‌ها و موارد آزمون.

۴-درک تفاوت‌های مفهومی و عملی بین رویکردهای TDD و BDD.

## ۲. ابزارها و پیش‌نیازها
برای انجام این آزمایش، از ابزارهای زیر استفاده شد:

زبان برنامه‌نویسی: Java 

محیط توسعه: Visual Studio Code (VS Code)

ابزار مدیریت پروژه و وابستگی‌ها: Apache Maven

چارچوب تست BDD: Cucumber

چارچوب اجرای تست: JUnit 4

## ۳. مراحل انجام آزمایش
فرآیند پیاده‌سازی بر اساس چرخه BDD شامل مراحل زیر بود:

### مرحله ۱: تعریف رفتار (نوشتن Feature File)
در ابتدا، رفتار مورد انتظار از سیستم در یک فایل با پسوند .feature و با استفاده از زبان Gherkin توصیف شد. این فایل به عنوان مستندات زنده و قابل اجرای پروژه عمل می‌کند.

src/test/resources/com/example/calculator.feature

```Gherkin

Feature: Calculator
  As a user
  I want to perform basic arithmetic operations
  So that I can get the correct result

  Scenario Outline: Perform a calculation
    Given I have a calculator
    When I enter `operand1` and `operand2` with the operator `operator`
    Then the result should be `result`

    Examples:
    | operand1 | operator | operand2 | result |
    | 10.0     | +        | 5.0      | 15.0   |
    | 20.0     | -        | 8.0      | 12.0   |
    | 7.0      | *        | 6.0      | 42.0   |
    | 100.0    | /        | 10.0     | 10.0   |

  Scenario: Division by zero
    Given I have a calculator
    When I try to divide 10.0 by 0.0
    Then the operation should throw an exception
```
### مرحله ۲: پیاده‌سازی گام‌های تست (Step Definitions)
در این مرحله، برای هر یک از گام‌های تعریف شده در فایل .feature، یک متد معادل در جاوا نوشته شد. این کدها به عنوان "چسب" (Glue Code) بین سناریوهای متنی و کد اجرایی عمل می‌کنند.

src/test/java/com/example/CalculatorStepdefs.java

```Java

package com.example;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.Assert.*;

public class CalculatorStepdefs {

    private Calculator calculator;
    private double result;
    private Exception thrownException;

    @Given("I have a calculator")
    public void i_have_a_calculator() {
        calculator = new Calculator();
    }
    // ... other methods ...
}
```

### مرحله ۳: ایجاد اجراکننده تست (Test Runner)
برای اینکه Maven و JUnit بتوانند تست‌های Cucumber را شناسایی و اجرا کنند، یک کلاس اجراکننده ایجاد شد.

src/test/java/com/example/RunCucumberTest.java
```java

package com.example;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty"})
public class RunCucumberTest {
    // This class must be empty
}
```
### مرحله ۴: اجرای اولیه تست و مشاهده شکست (گام قرمز)
پس از تکمیل مراحل بالا و قبل از نوشتن کد اصلی برنامه، تست‌ها با دستور mvn test اجرا شدند. همانطور که در رویکرد BDD انتظار می‌رود، تست‌ها به دلیل عدم وجود کلاس Calculator با خطا مواجه شدند. این شکست، صحت عملکرد زیرساخت تست را تایید می‌کند.
### مرحله ۵: پیاده‌سازی منطق اصلی برنامه (گام سبز)
در این مرحله، کلاس Calculator برای برآورده کردن نیازمندی‌های مشخص شده در سناریوها پیاده‌سازی شد.

src/main/java/com/example/Calculator.java

```Java

package com.example;

public class Calculator {
    public double calculate(double operand1, double operand2, String operator) {
        switch (operator) {
            case "+":

return operand1 + operand2;
            case "-":
                return operand1 - operand2;
            case "*":
                return operand1 * operand2;
            case "/":
                if (operand2 == 0) {
                    throw new IllegalArgumentException("Cannot divide by zero.");
                }
                return operand1 / operand2;
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }
}
```

## ۴. نتایج
پس از پیاده‌سازی کد اصلی، تست‌ها مجدداً با دستور mvn test اجرا شدند. این بار تمام سناریوها با موفقیت پاس شدند که نشان‌دهنده انطباق کامل عملکرد نرم‌افزار با رفتارهای تعریف شده است.

```Bash

[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running com.example.RunCucumberTest
Scenario Outline: Perform a calculation        # com/example/calculator.feature:13
  Given I have a calculator                    # com.example.CalculatorStepdefs.i_have_a_calculator()
  When I enter 10.0 and 5.0 with the operator "+" # com.example.CalculatorStepdefs.i_enter_and_with_the_operator(java.lang.Double,java.lang.Double,java.lang.String)
  Then the result should be 15.0               # com.example.CalculatorStepdefs.the_result_should_be(java.lang.Double)
# ... (خروجی سایر سناریوها)
Scenario: Division by zero                     # com/example/calculator.feature:18
  Given I have a calculator                    # com.example.CalculatorStepdefs.i_have_a_calculator()
  When I try to divide 10.0 by 0.0             # com.example.CalculatorStepdefs.i_try_to_divide_by(java.lang.Double,java.lang.Double)
  Then the operation should throw an exception # com.example.CalculatorStepdefs.the_operation_should_throw_an_exception()
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.580 s -- in com.example.RunCucumberTest
[INFO]
[INFO] Results:
[INFO]
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0
[INFO]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
```
خروجی BUILD SUCCESS و Failures: 0 به وضوح نشان می‌دهد که تمام ۵ سناریوی تعریف شده با موفقیت اجرا شده‌اند.  

## ۵. نتیجه‌گیری
این آزمایش به خوبی مزایای رویکرد BDD را به نمایش گذاشت. با تعریف نیازمندی‌ها در قالب سناریوهای قابل فهم، یک زبان مشترک بین تمام اعضای تیم ایجاد شد. این سناریوها نه تنها به عنوان مستندات پروژه عمل کردند، بلکه به صورت خودکار به تست‌های پذیرش تبدیل شدند که صحت عملکرد سیستم را در برابر نیازمندی‌های اولیه تضمین می‌کنند.

چرخه "تعریف رفتار -> مشاهده شکست تست -> نوشتن کد -> مشاهده موفقیت تست" باعث شد تا توسعه نرم‌افزار به صورت متمرکز، هدفمند و با اطمینان بالا از کیفیت محصول نهایی انجام شود.