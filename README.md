Setup:
IDE : Intellij Ultimate 2019.2.3
Required: Java 11 SDK, Maven

The project contains the TaxiSuppliersCLI class which will run part1 of the challenge. In order to run it:
- add Run/Debug Configuration and as main class add the com.taxi.TaxiSuppliersCLI class
- In the Run/Debug Configuration Window, add program arguments. An example is: 53.475506 -2.314775 53.360911 -2.257616 6. Note that the last number is the number of passengers. Both the API and the CLI app will go through all API's provided(Jeff, Eric, Dave), retrieve all car options, then filter them based on price and number of passengers(which is optional).
- I have also set a Connection Timeout for 2000ms and in case of a 404 or 500 response, I just send back an empty Object and move on to the next call, as instructed.

For the second part of the project, I have split the code in resource(dealing with incoming requests), service(dealing with all logic), and models(pojos). In order to start the API, add Run/Debug  Configuration with com.taxi.App as main class.
A sample request looks like this:

{env}/taxi?pickup_lat=53.455999&pickup_lon=-2.246844&dropoff_lat=53.552277&dropoff_lon=-2.323437
