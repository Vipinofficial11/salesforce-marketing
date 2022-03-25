# Copyright © 2022 Cask Data, Inc.
#
# Licensed under the Apache License, Version 2.0 (the "License"); you may not
# use this file except in compliance with the License. You may obtain a copy of
# the License at
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
# License for the specific language governing permissions and limitations under
# the License.

@SFMarketingCloud
@SFMCSource
@Smoke
@Regression
Feature: Salesforce Marketing Cloud Source - Design time scenarios

  #add filters scenario for both run time and
  @BATCH-TS-SFMC-DSGN-01
  Scenario Outline: Verify user should be able to get Output Schema for Single Object Data Retrieval mode
    When Open Datafusion Project to configure pipeline
    And Select data pipeline type as: "Batch"
    And Select plugin: "Salesforce Marketing" from the plugins list as: "Source"
    And Navigate to the properties page of plugin: "Salesforce Marketing"
    And configure source plugin for Object: "<ObjectName>" in the Single Object mode
    And fill Authentication properties for Salesforce Admin user
    Then Validate output schema with expectedSchema "sfmcSourceSchema.unsubevent"
    Examples:
      | ObjectName          | ExpectedSchema |
      | UNSUB_EVENT         |                |

  @BATCH-TS-SFMC-DSGN-02
  Scenario: Verify user should be able to get Output Schema for Multi Object Data Retrieval mode
    When Open Datafusion Project to configure pipeline
    And Select data pipeline type as: "Batch"
    And Select plugin: "Salesforce Marketing" from the plugins list as: "Source"
    And Navigate to the properties page of plugin: "Salesforce Marketing"
    And fill Authentication properties for Salesforce Admin user
    And fill Object List with below listed Objects in the Multi Object mode:
      | BOUNCE_EVENT | EMAIL |
    And Enter input plugin property: "tableNameField" with value: "TableName"
    Then Validate "Salesforce Marketing" plugin properties
#checkbox issue not resolved


  # We need to create seperate test for select Data extension, because we have to fill Data extension input
  #   external keys at that time or we should do it with function
  #because It will create extra cases in Run time too

  #Filling data Extension key using function
  @BATCH-TS-SFMC-DSGN-03
  Scenario Outline:Verify user should be able to get Output Schema when plugin is configured with object 'Data Extension'  in Single Object Data Retrieval mode
    When Open Datafusion Project to configure pipeline
    And Select data pipeline type as: "Batch"
    And Select plugin: "Salesforce Marketing" from the plugins list as: "Source"
    And Navigate to the properties page of plugin: "Salesforce Marketing"
    And fill Authentication properties for Salesforce Admin user
    And configure source plugin for Object: "<ObjectName>" in the Single Object mode
    And Enter input plugin property: "dataExtensionKeyList" with value: "Key121"
    Then Click on the Validate button
      #verify schema
    Examples:
      | ObjectName          |
      | DATA_EXTENSION      |

#Filling directly from step for now
  @BATCH-TS-SFMC-DSGN-04
  Scenario:Verify user should be able to get Output Schema when plugin is configured with object 'Data Extension'  in Multi Object Data Retrieval mode
    When Open Datafusion Project to configure pipeline
    And Select data pipeline type as: "Batch"
    And Select plugin: "Salesforce Marketing" from the plugins list as: "Source"
    And Navigate to the properties page of plugin: "Salesforce Marketing"
    And fill Reference Name property
    And fill Authentication properties for Salesforce Admin user
    And fill Object List with below listed Objects in the Multi Object mode:
      | DATA_EXTENSION | EMAIL |
    And Enter input plugin property: "dataExtensionKeyList" with value: "Key121"
    Then Click on the Validate button

