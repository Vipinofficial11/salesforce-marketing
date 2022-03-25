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

package io.cdap.plugin.sfmcsource.stepsdesign;

import io.cdap.e2e.utils.CdfHelper;
import io.cdap.plugin.sfmcsource.actions.SfmcSourcePropertiesPageActions;
import io.cdap.plugin.sfmcsource.locators.SfmcSourcePropertiesPage;
import io.cdap.plugin.utils.enums.DataRetrievalMode;
import io.cdap.plugin.utils.enums.Sobjects;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.RandomStringUtils;
import org.datanucleus.store.rdbms.autostart.SchemaTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents Salesforce Marketing Cloud - Source plugin - Properties page - steps.
 */
public class DesignTimeSteps implements CdfHelper {

  List<Sobjects> objectsList = new ArrayList<>();

  @When("fill Reference Name property")
  public void fillReferenceNameProperty() {
    String referenceName = "TestSF" + RandomStringUtils.randomAlphanumeric(7);
    SfmcSourcePropertiesPageActions.fillReferenceName(referenceName);
  }


  @And("fill Authentication properties for Salesforce Admin user")
  public void fillAuthenticationPropertiesForSalesforceAdminUser() {
    SfmcSourcePropertiesPageActions.fillAuthenticationPropertiesForSalesforceAdminUser();

  }

  @And("configure source plugin for Object: {string} in the Single Object mode")
  public void configureSourcePluginForObjectInTheSingleObjectMode(String objectName) {
    SfmcSourcePropertiesPageActions.configureSourcePluginForObjectNameInSingleObjectMode(Sobjects.valueOf(objectName));
  }

  @And("fill Authentication properties with invalid values")
  public void fillAuthenticationPropertiesWithInvalidValues() {
      SfmcSourcePropertiesPageActions.fillAuthenticationPropertiesWithInvalidValues();
  }

  @And("fill Object List with below listed Objects in the Multi Object mode:")
  public void selectObjectsInObjectsList(DataTable table) throws InterruptedException {
    List<String> list = table.asList();

    for (String object : list) {
      objectsList.add(Sobjects.valueOf(object));
    }
    SfmcSourcePropertiesPageActions.selectObjectNamesInMultiObjectMode(objectsList);
  }



}
