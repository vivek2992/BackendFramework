@RestfulBooker
@all
  Feature: Restful Booker API Feature

    @qa
    @TC_RestfulBooker_01
      Scenario: Verify that the API is up and running using health check api
        When I hit the health check api
        Then I should get 201 Response

    @qa
    @TC_RestfulBooker_02
      Scenario Outline: Verify E2E Operations on Booking
        Given I have the list of existing Bookings
        When I Create a new booking using "<testdata>"
        Then Verify that its not in the existing Bookings
        Then Verify that its added to the list of Bookings
        Then Verify that the booking has the correct details
        Given I Create Token to be used for PUT and DELETE
        When I Update the Booking with "<updatetestdata>"
        Then Verify that the booking has been update with the correct details
        When I Partially Update the Booking with "<partialupdate>"
        Then Verify that the booking has been update with the correct details
        When I Delete the booking
        Then I Verify that the booking has been deleted successfully
        Examples:
        | testdata     | updatetestdata | partialupdate |
        | TC_RB_02_001 | TC_RB_02_003   | TC_RB_02_005  |
        | TC_RB_02_002 | TC_RB_02_004   | TC_RB_02_006  |

