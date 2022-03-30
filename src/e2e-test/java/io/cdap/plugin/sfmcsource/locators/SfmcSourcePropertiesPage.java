/*
 * Copyright © 2022 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package io.cdap.plugin.sfmcsource.locators;

import io.cdap.e2e.utils.SeleniumDriver;
import io.cdap.plugin.utils.enums.Sobjects;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

/**
 * Represents Salesforce Marketing Cloud - Source plugin - Properties page - Locators.
 */
public class SfmcSourcePropertiesPage {
  @FindBy(how = How.XPATH, using = "//div[contains(@class, 'label-input-container')]//input")
  public static WebElement labelInput;

  // Basic section
  @FindBy(how = How.XPATH, using = "//input[@data-cy='referenceName']")
  public static WebElement referenceNameInput;

  @FindBy(how = How.XPATH, using = "//div[@data-cy='select-queryMode']")
  public static WebElement dataRetrievalModeDropdown;

  // Single Object Retrieval mode section
  @FindBy(how = How.XPATH, using = "//div[@data-cy='select-objectName']")
  public static WebElement objectDropdownForSIngleObjectMode;

  @FindBy(how = How.XPATH, using = "//button[@data-cy='get-schema-btn']")
  public static WebElement getSchemaButton;

  @FindBy(how = How.XPATH, using = "//input[@data-cy='dataExtensionKey']")
  public static WebElement dataExtensionExternalKeyInputForSingleObjectMode;

  // Multi Object Retrieval mode section
  @FindBy(how = How.XPATH, using = "//div[@data-cy='multiselect-objectList']")
  public static WebElement objectDropdownForMultiObjectMode;

  @FindBy(how = How.XPATH, using = "//div[@data-cy='dataExtensionKeyList']")
  public static WebElement dataExtensionExternalKeyInputForMultiObjectMode;

  @FindBy(how = How.XPATH, using = "//input[@data-cy='tableNameField']")
  public static WebElement tableNameFieldInput;

  // Filter section
  @FindBy(how = How.XPATH, using = "//button[@data-cy='filter']")
  public static WebElement filterInput;

  // Authentication section
  @FindBy(how = How.XPATH, using = "//input[@data-cy='clientId']")
  public static WebElement clientIdInput;

  @FindBy(how = How.XPATH, using = "//input[@data-cy='clientSecret']")
  public static WebElement clientSecretInput;

  @FindBy(how = How.XPATH, using = "//input[@data-cy='authEndpoint']")
  public static WebElement authenticationBaseUriInput;

  @FindBy(how = How.XPATH, using = "//input[@data-cy='soapEndpoint']")
  public static WebElement soapApiEndpointInput;

  @FindBy(how = How.XPATH, using = "//input[@data-cy='restEndpoint']")
  public static WebElement restApiBaseUriInput;

  // Common
  public static WebElement getDropdownOptionElement(String option) {
    String xpath = "//li[@role='option'][normalize-space(text()) = '" + option + "']";
    return SeleniumDriver.getDriver().findElement(By.xpath(xpath));
  }

  public static WebElement getObjectCheckBox(String sobjects) {
    String xpath = "//li[@data-cy='multioption-" + sobjects + "']";
    return SeleniumDriver.getDriver().findElement(By.xpath(xpath));
  }


}
