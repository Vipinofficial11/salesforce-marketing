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

  @BATCH-TS-SFMC-DSGN-01
  Scenario Outline: Verify user should be able to get Output Schema for Single Object Data Retrieval mode
    When Open Datafusion Project to configure pipeline
    And Select data pipeline type as: "Batch"
    And Select plugin: "Salesforce Marketing" from the plugins list as: "Source"
    And Navigate to the properties page of plugin: "Salesforce Marketing"
    And Enter input plugin property: "referenceName" with value: "Referencename"
    And configure source plugin for Object: "<ObjectName>" in the Single Object mode
    And Enter input plugin property: "clientId" with value: "admin.clientid"
    And Enter input plugin property: "clientSecret" with value: "admin.clientsecret"
    And Enter input plugin property: "authEndpoint" with value: "admin.authenticationbase.uri"
    And Enter input plugin property: "soapEndpoint" with value: "admin.soapapi.endpoint"
    And Enter input plugin property: "restEndpoint" with value: "admin.restapibase.uri"
    Then Validate output schema with expectedSchema "<ExpectedSchema>"
    Examples:
      | ObjectName    | ExpectedSchema                |
      | BOUNCE_EVENT  | sfmcSourceSchema.bounceevent  |
      | EMAIL         | sfmcSourceSchema.email        |
      | MAILING_LIST  | sfmcSourceSchema.mailinglist  |
      | NOTSENT_EVENT | sfmcSourceSchema.notsentevent |
      | OPEN_EVENT    | sfmcSourceSchema.openevent    |
      | SENT_EVENT    | sfmcSourceSchema.sentevent    |
      | UNSUB_EVENT   | sfmcSourceSchema.unsubevent   |

  @BATCH-TS-SFMC-DSGN-02
  Scenario: Verify user should be able to validate the plugin for Multi Object Data Retrieval mode
    When Open Datafusion Project to configure pipeline
    And Select data pipeline type as: "Batch"
    And Select plugin: "Salesforce Marketing" from the plugins list as: "Source"
    And Navigate to the properties page of plugin: "Salesforce Marketing"
    And Enter input plugin property: "referenceName" with value: "Referencename"
    And Enter input plugin property: "clientId" with value: "admin.clientid"
    And Enter input plugin property: "clientSecret" with value: "admin.clientsecret"
    And Enter input plugin property: "authEndpoint" with value: "admin.authenticationbase.uri"
    And Enter input plugin property: "soapEndpoint" with value: "admin.soapapi.endpoint"
    And Enter input plugin property: "restEndpoint" with value: "admin.restapibase.uri"
    And fill Object List with below listed Objects in the Multi Object mode:
      | BOUNCE_EVENT | NOTSENT_EVENT |
    And Enter input plugin property: "tableNameField" with value: "TableName"
    Then Validate "Salesforce Marketing" plugin properties


  @BATCH-TS-SFMC-DSGN-03
  Scenario Outline:Verify user should be able to get Output Schema when plugin is configured with object 'Data Extension' in Single Object Data Retrieval mode
    When Open Datafusion Project to configure pipeline
    And Select data pipeline type as: "Batch"
    And Select plugin: "Salesforce Marketing" from the plugins list as: "Source"
    And Navigate to the properties page of plugin: "Salesforce Marketing"
    And Enter input plugin property: "referenceName" with value: "Referencename"
    And Enter input plugin property: "clientId" with value: "admin.clientid"
    And Enter input plugin property: "clientSecret" with value: "admin.clientsecret"
    And Enter input plugin property: "authEndpoint" with value: "admin.authenticationbase.uri"
    And Enter input plugin property: "soapEndpoint" with value: "admin.soapapi.endpoint"
    And Enter input plugin property: "restEndpoint" with value: "admin.restapibase.uri"
    And configure source plugin for Object: "<ObjectName>" in the Single Object mode
    And Enter input plugin property: "dataExtensionKey" with value: "Key121"
    Then Validate output schema with expectedSchema "<ExpectedSchema>"
    And Click on the Validate button
    Examples:
      | ObjectName     | ExpectedSchema                 |
      | DATA_EXTENSION | sfmcSourceSchema.dataextension |

#Not working
  @BATCH-TS-SFMC-DSGN-04
  Scenario:Verify user should be able validate the plugin when configured with object 'Data Extension' in Multi Object Data Retrieval mode
    When Open Datafusion Project to configure pipeline
    And Select data pipeline type as: "Batch"
    And Select plugin: "Salesforce Marketing" from the plugins list as: "Source"
    And Navigate to the properties page of plugin: "Salesforce Marketing"
    And Enter input plugin property: "referenceName" with value: "Referencename"
    And Enter input plugin property: "clientId" with value: "admin.clientid"
    And Enter input plugin property: "clientSecret" with value: "admin.clientsecret"
    And Enter input plugin property: "authEndpoint" with value: "admin.authenticationbase.uri"
    And Enter input plugin property: "soapEndpoint" with value: "admin.soapapi.endpoint"
    And Enter input plugin property: "restEndpoint" with value: "admin.restapibase.uri"
    And fill Object List with below listed Objects in the Multi Object mode:
      | DATA_EXTENSION | NOTSENT_EVENT |
    And Enter input plugin property: "  " with value: "Key122"
    And Enter input plugin property: "tableNameField" with value: "TableName"
    And Enter input plugin property: "key" with value: "key1234"
    Then Click on the Validate button


  @BATCH-TS-SFMC-DSGN-05
  Scenario Outline: Verify user should be able to get Output Schema when configured with filter property
    When Open Datafusion Project to configure pipeline
    And Select data pipeline type as: "Batch"
    And Select plugin: "Salesforce Marketing" from the plugins list as: "Source"
    And Navigate to the properties page of plugin: "Salesforce Marketing"
    And Enter input plugin property: "referenceName" with value: "Referencename"
    And configure source plugin for Object: "<ObjectName>" in the Single Object mode
    And Enter input plugin property: "clientId" with value: "admin.clientid"
    And Enter input plugin property: "clientSecret" with value: "admin.clientsecret"
    And Enter input plugin property: "authEndpoint" with value: "admin.authenticationbase.uri"
    And Enter input plugin property: "soapEndpoint" with value: "admin.soapapi.endpoint"
    And Enter input plugin property: "restEndpoint" with value: "admin.restapibase.uri"
    And Enter input plugin property: "filter" with value: "<Filter>"
    Then Validate output schema with expectedSchema "<ExpectedSchema>"
    And Click on the Validate button
    Examples:
      | ObjectName   | ExpectedSchema               | Filter       |
      | BOUNCE_EVENT | sfmcSourceSchema.bounceevent | filter.value |
