Feature: the version can be retrieved

  Scenario: client makes call to GET /version
    When the client calls /version
    Then the client receives status code of 200
    And the client receives server version 1.0

  Scenario: client makes call to POST /version
    When the client calls post request to with version 4.0
    Then the client receives POST status code of 201
    And the client receives POST server version 4.0

  Scenario: client makes call to PUT /user
    When the client calls put request with name Johnny Banansson
    Then the client receives PUT status code of 200
    And the client will get result with name Johnny Banansson

  Scenario: client makes call to DELETE /user
    When the client calls delete request
    Then the client receives DELETE status code of 204
