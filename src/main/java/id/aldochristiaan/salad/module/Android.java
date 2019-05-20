package id.aldochristiaan.salad.module;

import id.aldochristiaan.salad.module.android.*;
import id.aldochristiaan.salad.module.general.*;
import id.aldochristiaan.salad.util.ChangeContext;
import id.aldochristiaan.salad.util.Direction;
import id.aldochristiaan.salad.util.FakerUtil;
import id.aldochristiaan.salad.util.LogUtil;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Objects;

import static id.aldochristiaan.salad.Salad.MAX_SWIPE_COUNT;

public class Android extends Mobile {

    protected AndroidDriver<AndroidElement> androidDriver;

    public Android(AndroidDriver<AndroidElement> androidDriver) {
        PageFactory.initElements(new AppiumFieldDecorator(androidDriver, Duration.ofSeconds(20)), this);
        this.androidDriver = androidDriver;
    }

    protected CheckElement checkElement() {
        return new CheckElement(androidDriver);
    }

    protected GetElement getElement() {
        return new GetElement(androidDriver);
    }

    protected GetMultipleElement getMultipleElement() {
        return new GetMultipleElement(androidDriver);
    }

    protected GetTextFromElement getTextFromElement() {
        return new GetTextFromElement(androidDriver);
    }

    protected LongTapElement longTapElement() {
        return new LongTapElement(androidDriver);
    }

    protected Swipe swipe() {
        return new Swipe(androidDriver);
    }

    protected TapElement tapElement() {
        return new TapElement(androidDriver);
    }

    protected ValidateElement validateElement() {
        return new ValidateElement(androidDriver);
    }

    protected ValidateValue validateValue() {
        return new ValidateValue();
    }

    protected TypeText typeText() {
        return new TypeText(androidDriver);
    }

    protected ChangeContext changeContext() {
        return new ChangeContext(androidDriver);
    }

    protected Toast toast() {
        return new Toast(androidDriver);
    }

    protected Randomize randomize() {
        return new Randomize();
    }

    protected FakerUtil fakerUtil() {
        return new FakerUtil();
    }

    protected AndroidElement findElementBy(By by) {
        AndroidElement element = null;
        for (int i = 0; i < MAX_SWIPE_COUNT; i++) {
            try {
                element = androidDriver.findElement(by);
                if (checkElement().isPresent(element)) break;
            } catch (NoSuchElementException e) {
                swipe().up();
            }
        }
        return element;
    }

    protected AndroidElement findElementBy(By by, Direction direction) {
        AndroidElement element = null;
        for (int i = 0; i < MAX_SWIPE_COUNT; i++) {
            try {
                element = androidDriver.findElement(by);
                if (checkElement().isPresent(element)) break;
            } catch (NoSuchElementException e) {
                swipe().toDirection(direction);
            }
        }
        return element;
    }

    protected AndroidElement findElementBy(By by, int timeout) {
        return (AndroidElement) (new WebDriverWait(androidDriver, timeout))
                .until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    protected List<AndroidElement> findElementsBy(By by) {
        List<AndroidElement> elements = null;
        for (int i = 0; i < MAX_SWIPE_COUNT; i++) {
            try {
                elements = androidDriver.findElements(by);
                if (checkElement().isPresent(elements.get(0))) break;
            } catch (NoSuchElementException e) {
                swipe().up();
            }
        }
        return elements;
    }

    protected List<AndroidElement> findElementsBy(By by, Direction direction) {
        List<AndroidElement> elements = null;
        for (int i = 0; i < MAX_SWIPE_COUNT; i++) {
            try {
                elements = androidDriver.findElements(by);
                if (checkElement().isPresent(elements.get(0))) break;
            } catch (NoSuchElementException e) {
                swipe().toDirection(direction);
            }
        }
        return elements;
    }

    protected List<WebElement> findElementsBy(By by, int timeout) {
        return (new WebDriverWait(androidDriver, timeout))
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
    }

    protected void hideKeyboard() {
        androidDriver.hideKeyboard();
    }

    protected void takeScreenshot(String name) {
        File scrFile = ((TakesScreenshot) androidDriver).getScreenshotAs(OutputType.FILE);
        File imageFile = new File("screenshot/" + name + ".png");
        try {
            FileUtils.copyFile(Objects.requireNonNull(scrFile), imageFile);
            LogUtil.info("Screenshot taken!");
        } catch (IOException e) {
            LogUtil.error("Failed to take screenshot!");
            e.printStackTrace();
        }
    }

    protected void takeScreenshot(String path, String name) {
        File scrFile = ((TakesScreenshot) androidDriver).getScreenshotAs(OutputType.FILE);
        File imageFile = new File(path + "/" + name + ".png");
        try {
            FileUtils.copyFile(Objects.requireNonNull(scrFile), imageFile);
            LogUtil.info("Screenshot taken!");
        } catch (IOException e) {
            LogUtil.error("Failed to take screenshot!");
            e.printStackTrace();
        }
    }
}