package com.hometopia.scheduledservice.util;

import com.hometopia.commons.enumeration.AssetCategory;
import com.hometopia.commons.message.Vendor;
import com.hometopia.proto.district.DistrictGrpcServiceGrpc;
import com.hometopia.proto.district.GetDistrictRequest;
import com.hometopia.proto.district.GetDistrictResponse;
import com.hometopia.proto.province.GetProvinceRequest;
import com.hometopia.proto.province.GetProvinceResponse;
import com.hometopia.proto.province.ProvinceGrpcServiceGrpc;
import com.hometopia.proto.ward.GetWardRequest;
import com.hometopia.proto.ward.GetWardResponse;
import com.hometopia.proto.ward.WardGrpcServiceGrpc;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

public class RepairMaintenanceVendorCrawler {

    public static List<Vendor> getListVendors(String query,
                                              ProvinceGrpcServiceGrpc.ProvinceGrpcServiceBlockingStub provinceGrpcServiceStub,
                                              DistrictGrpcServiceGrpc.DistrictGrpcServiceBlockingStub districtGrpcServiceStub,
                                              WardGrpcServiceGrpc.WardGrpcServiceBlockingStub wardGrpcServiceStub) throws InterruptedException {
        List<Vendor> vendors = new ArrayList<>();

        System.setProperty("webdriver.chrome.driver", "E:\\chromedriver-win64\\chromedriver.exe");

        // Set Chrome options (optional)
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--remote-allow-origins=*");

        WebDriver driver = new ChromeDriver(options);
        driver.get("https://www.google.com/maps/?hl=vi");

        WebElement searchBox = driver.findElement(By.id("searchboxinput"));
        searchBox.sendKeys(query);

        WebElement searchButton = driver.findElement(By.id("searchbox-searchbutton"));
        searchButton.click();

        Thread.sleep(5000);

        for (int i = 0; i < 1; i++) {
            List<WebElement> elements = driver.findElements(By.cssSelector(".Nv2PK"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elements.get(elements.size() - 1));
            Thread.sleep(5000);
        }

        List<WebElement> results = driver.findElements(By.cssSelector(".Nv2PK"));

        for (WebElement ele : results) {
            try {
                ele.click();
            } catch (Exception e) {
                break;
            }

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            List<WebElement> info = driver.findElements(By.cssSelector(".Io6YTe.fontBodyMedium"));

            vendors.add(new Vendor(
                    tryGet(() -> ele.findElement(By.tagName("a")).getAttribute("href"), ""),
                    tryGet(() -> driver.findElement(By.cssSelector(".DUwDvf")).getText(), ""),
                    tryGet(() -> {
                        String line = info.get(0).getText().matches("^\\+\\d{1,3}\\s\\d{3}\\s\\d{3}\\s\\d{3}$")
                                ? "" : info.get(0).getText();
                        AtomicReference<Long> provinceCode = new AtomicReference<>();
                        AtomicReference<String> provinceName = new AtomicReference<>();
                        AtomicReference<Long> districtCode = new AtomicReference<>();
                        AtomicReference<String> districtName = new AtomicReference<>();
                        AtomicReference<Long> wardCode = new AtomicReference<>();
                        AtomicReference<String> wardName = new AtomicReference<>();
                        Arrays.stream(line.split(",")).toList().forEach(part -> {
                            try {
                                GetProvinceResponse getProvinceResponse = provinceGrpcServiceStub.getProvince(GetProvinceRequest.newBuilder()
                                        .setName(part.replaceAll("[0-9]", "").trim()).setCountryCode("VN").build());
                                provinceCode.set(getProvinceResponse.getCode());
                                provinceName.set(getProvinceResponse.getName());
                            } catch (Exception e) {

                            }

                            try {
                                GetDistrictResponse getDistrictResponse = districtGrpcServiceStub.getDistrict(GetDistrictRequest.newBuilder()
                                        .setName(part.trim()).setCountryCode("VN").build());
                                districtCode.set(getDistrictResponse.getCode());
                                districtName.set(getDistrictResponse.getName());
                            } catch (Exception e) {

                            }

                            try {
                                GetWardResponse getWardResponse = wardGrpcServiceStub.getWard(GetWardRequest.newBuilder()
                                        .setName(part.trim()).setCountryCode("VN").build());
                                wardCode.set(getWardResponse.getCode());
                                wardName.set(getWardResponse.getName());
                            } catch (Exception e) {

                            }
                        });
                        return new Vendor.Address(line, provinceCode.get(), provinceName.get(), districtCode.get(),
                                districtName.get(), wardCode.get(), wardName.get());
                    }, null),
                    tryGet(() -> driver.findElement(By.cssSelector("a.CsEnBe")).getAttribute("href"), ""),
                    tryGet(() -> info.stream()
                            .filter(c -> c.getText().matches("^\\+\\d{1,3}\\s\\d{3}\\s\\d{3}\\s\\d{3}$"))
                            .findFirst().map(WebElement::getText).orElse(""), ""),
                    AssetCategory.LAPTOP
            ));
        }

        driver.quit();

        return vendors;
    }

    private static <T> T tryGet(Supplier<T> supplier, T defaultValue) {
        try {
            return supplier.get();
        } catch (Exception e) {
            return defaultValue;
        }
    }
}
