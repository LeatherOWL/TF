## How to Run

- Make sure you are using JDK 11 and Maven 3.x
- mvn clean package

## About

- API documentation:

```sh
http://localhost:8080/swagger-ui.html
```

### EndPoints
Provides 1 http endpoint for storing Detection
endpoints:
- POST <host>/v1/detection

```sh
Accept: application/json
Content-Type: application/json
{
    "detectionId": 22,
    "time": "2020-10-01T11:15:11.618+00:00",
    "nameOfApp": "appName2",
    "typeOfApp": "appType2",
    "detectionType": "NO_DETECTION",
    "deviceInfo": {
        "deviceId": 3,
        "deviceType": "ANDROID",
        "deviceModel": "deviceMode2",
        "osVersion": "osVersion",
        "detectionInfos": null
    }
}
```

And 3 http endpoints for retrieving data:
- GET <host>/v1/detection/{id}:
 
```sh
Accept: application/json
Content-Type: application/json
{
    "detectionId": 1,
    "time": "2020-10-01T13:32:01.347+00:00",
    "nameOfApp": "appName",
    "typeOfApp": "appType",
    "detectionType": "NEW",
    "deviceInfo": {
        "deviceId": 1,
        "deviceType": "ANDROID",
        "deviceModel": "deviceModel",
        "osVersion": "osVersion",
        "detectionInfos": null
    }
}
```

- GET <host>/v1/device/{id}:
 
```sh
Accept: application/json
Content-Type: application/json
{
    "deviceId": 1,
    "deviceType": "ANDROID",
    "deviceModel": "deviceModel",
    "osVersion": "osVersion",
    "detectionInfos": [
        {
            "detectionId": 2,
            "time": "2020-10-01T13:32:01.385+00:00",
            "nameOfApp": "appName1",
            "typeOfApp": "appType1",
            "detectionType": "RESOLVED",
            "deviceInfo": null
        },
        {
            "detectionId": 1,
            "time": "2020-10-01T13:32:01.347+00:00",
            "nameOfApp": "appName",
            "typeOfApp": "appType",
            "detectionType": "NEW",
            "deviceInfo": null
        }
    ]
}
```

- GET <host>/v1/detection/all?nameOfApp=appName:
 
```sh
Accept: application/json
Content-Type: application/json
[
    {
        "detectionId": 1,
        "time": "2020-10-01T13:32:01.347+00:00",
        "nameOfApp": "appName",
        "typeOfApp": "appType",
        "detectionType": "NEW",
        "deviceInfo": {
            "deviceId": 1,
            "deviceType": "ANDROID",
            "deviceModel": "deviceModel",
            "osVersion": "osVersion",
            "detectionInfos": null
        }
    }
]
```

The results are shown by most recent on top. Also possible to apply query parameters to filter results. 

### Implementation details

In-memory H2 database used (console: http://localhost:8080/h2-console credentials in application.properties)
