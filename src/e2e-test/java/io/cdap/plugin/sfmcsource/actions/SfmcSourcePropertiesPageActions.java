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

package io.cdap.plugin.sfmcsource.actions;

import io.cdap.cdap.api.data.schema.Schema;
import io.cdap.e2e.pages.locators.CdfPluginPropertiesLocators;
import io.cdap.e2e.utils.AssertionHelper;
import io.cdap.e2e.utils.ElementHelper;
import io.cdap.e2e.utils.PluginPropertyUtils;
import io.cdap.e2e.utils.SeleniumDriver;
import io.cdap.e2e.utils.SeleniumHelper;
import io.cdap.plugin.sfmc.source.MarketingCloudSource;
import io.cdap.plugin.sfmc.source.MarketingCloudSourceConfig;
import io.cdap.plugin.sfmc.source.util.SourceQueryMode;
import io.cdap.plugin.sfmcsource.locators.SfmcSourcePropertiesPage;
import io.cdap.plugin.utils.enums.DataRetrievalMode;
import io.cdap.plugin.utils.enums.Sobjects;
import org.apache.commons.lang3.RandomStringUtils;
import org.datanucleus.store.rdbms.autostart.SchemaTable;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Represents - Salesforce Marketing Cloud - Source plugin - Properties page - Actions.
 */
public class SfmcSourcePropertiesPageActions {
  private static final Logger logger = LoggerFactory.getLogger(SfmcSourcePropertiesPageActions.class);

  static {
    SeleniumHelper.getPropertiesLocators(SfmcSourcePropertiesPage.class);
  }

  public static void fillReferenceName(String referenceName) {
    ElementHelper.sendKeys(SfmcSourcePropertiesPage.referenceNameInput, referenceName);
  }

  public static void selectDataRetrievalMode(DataRetrievalMode mode) {

    ElementHelper.selectDropdownOption(SfmcSourcePropertiesPage.dataRetrievalModeDropdown,
                                       CdfPluginPropertiesLocators.locateDropdownListItem(mode.value));
  }

  public static void fillAuthenticationProperties(String clientId, String clientSecret, String authenticationBaseUri,
                                                  String soapApiEndpoint, String restApiBaseUri) {

    ElementHelper.sendKeys(SfmcSourcePropertiesPage.clientIdInput, clientId);
    ElementHelper.sendKeys(SfmcSourcePropertiesPage.clientSecretInput, clientSecret);
    ElementHelper.sendKeys(SfmcSourcePropertiesPage.authenticationBaseUriInput, authenticationBaseUri);
    ElementHelper.sendKeys(SfmcSourcePropertiesPage.soapApiEndpointInput, soapApiEndpoint);
    ElementHelper.sendKeys(SfmcSourcePropertiesPage.restApiBaseUriInput, restApiBaseUri);
  }


  public static void fillAuthenticationPropertiesForSalesforceAdminUser() {
    SfmcSourcePropertiesPageActions.fillAuthenticationProperties(
      PluginPropertyUtils.pluginProp("admin.clientid"),
      PluginPropertyUtils.pluginProp("admin.clientsecret"),
      PluginPropertyUtils.pluginProp("admin.authenticationbase.uri"),
      PluginPropertyUtils.pluginProp("admin.soapapi.endpoint"),
      PluginPropertyUtils.pluginProp("admin.restapibase.uri"));


  }


  public static void configureSourcePluginForObjectNameInSingleObjectMode(Sobjects objectName) {
    String referenceName = "TestSF" + RandomStringUtils.randomAlphanumeric(7);
    fillReferenceName(referenceName);
    selectDataRetrievalMode(DataRetrievalMode.SINGLE_OBJECT);
    ElementHelper.selectDropdownOption(SfmcSourcePropertiesPage.objectDropdownForSIngleObjectMode,
                                       CdfPluginPropertiesLocators.locateDropdownListItem(objectName.value));

    if (objectName.value.equals("Data Extension")) {
       SfmcSourcePropertiesPageActions.fillDataExtensionExternalKeyForSingleObjectMode();
    }
  }
  public static void fillDataExtensionExternalKeyForSingleObjectMode() {
    String dataExtensionExternalKey = PluginPropertyUtils.pluginProp("singleobject.externalkey");
    ElementHelper.sendKeys(SfmcSourcePropertiesPage.dataExtensionExternalKeyInputForSingleObjectMode,
                           dataExtensionExternalKey);
  }



  public static void fillAuthenticationPropertiesWithInvalidValues() {
    SfmcSourcePropertiesPageActions.fillAuthenticationProperties(
      PluginPropertyUtils.pluginProp("invalid.clientid"),
      PluginPropertyUtils.pluginProp("invalid.clientsecret"),
      PluginPropertyUtils.pluginProp("invalid.authenticationbase.uri"),
      PluginPropertyUtils.pluginProp("invalid.soapapi.endpoint"),
      PluginPropertyUtils.pluginProp("invalid.restapibase.uri"));
  }


  public static void selectObjectNamesInMultiObjectMode(List<Sobjects> objectNames) throws InterruptedException {
    int totalSObjects = objectNames.size();

    String referenceName = "TestSF" + RandomStringUtils.randomAlphanumeric(7);
    fillReferenceName(referenceName);
    selectDataRetrievalMode(DataRetrievalMode.MULTI_OBJECT);
    SfmcSourcePropertiesPage.objectDropdownForMultiObjectMode.click();

    for (int i = 0; i < totalSObjects; i++) {
      //SfmcSourcePropertiesPage.getObjectCheckBox(objectNames.get(i).value).click();
      ElementHelper.selectCheckbox(SfmcSourcePropertiesPage.getObjectCheckBox(objectNames.get(i).value));

      //write the condition when "Data extension" is selcted as option we need to fill a text box
       }
//    Actions action = new Actions(SeleniumDriver.getDriver());
//    action.sendKeys(Keys.ESCAPE);

     //SfmcSourcePropertiesPage.optionemail.click();

      ElementHelper.clickOnElementUsingJsExecutor(SfmcSourcePropertiesPage.dismissCheckbox);
    //Thread.sleep(5000);

      //SfmcSourcePropertiesPage.dismissCheckbox.click();

  }



}
