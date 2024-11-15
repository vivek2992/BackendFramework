@Maps
@all
Feature: Google Maps API

  @qa
  @TC_GoogleMaps_01
  Scenario Outline: Search a location and get coordinates
    When I hit the search places api with "<location>"
    Then I should get coordinates of the location
    Examples:
      | location        |
      | Kyalasanahalli  |