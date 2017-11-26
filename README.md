# parking-system
Web application build with Spring Boot for managing the city parking spaces.

## Running
To run application open it's root directory in terminal and type:

```bash
./start.sh
```

## REST API
### Starting parking meter
Starts measuring parking time for car with given license number, optionally takes vipCode parameter which can apply 
vip parking pricing.
* URL
`meter/start`
* Method
`POST`
* Data params
    ```text
    {
      licenseNumber: [string],
      vipCode: [string] [optional]
    }
    ```
### Stoping parking meter
Stops measuring parking time and returns parking price.
* URL
`meter/stop`
* Method
`POST`
* Data params
    ```text
    {
      licenseNumber: [string]
    }
    ```
### Checking if car with given license number is on parking
* URL
`state/car`
* Method
`GET`
* URL params

    `licenseNumber [string]`
### Calculating income for given day
* URL
`income`
* Method
`GET`
* URL params

    `licenseNumber [date in format yyyy-MM-dd]`

