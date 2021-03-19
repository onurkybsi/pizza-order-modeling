# pizza-order-modeling
It is a simple implementation of pizza order modeling. Brings the menu from the database to the user interface. It complete the order according to the business rules. All order details are entered in the database.

#### UserInterface: It is a Node.js application that serve a SPA frontend implementation coded with React.
#### ModelDataProducer: Checks business rules for data received from the UserInterface and passes the data to the Mediator application for storing.
#### Mediator: It is responsible for storing the pizza store data in Elasticsearch and serving it on the REST API.

## Installation

Use the [docker-compose](https://docs.docker.com/compose/) to install pizza-order-modeling.

```bash
docker-compose up
```

### ModelDataProducer REST API

The ModelDataProducer REST API usage is described below.

#### Get PizzaStore menu from the database

#### Request

`GET api/getMenu`

    curl -H "Accept: application/json" http://localhost:8080/api/getMenu

#### Response

    HTTP/1.1 200 OK
    Date: Fri, 19 March 2021 15:09:14 GMT
    Content-Length: 1697
    
    [{"id":"c33c376d-d0b5-4ba0-b04e-7d306a8f9ee5","name":"Marinara","type":"PIZZA","requiredMaterials":[{"id":"b4a11116-e370-49db-a6d3-5b40bb086a70","name":"Features tomatoes"},{"id":"88ff4a32-cd06-462f-8a7b-bd1dc778e2d9","name":"Oregano"},{"id":"065dc16c-1d74-452b-a5cb-e5f8a0be1edf","name":"Extra virgin olive oil"}],"preparationTimeInMinute":10,"price":7.0},{"id":"7cf86a49-3f0d-4942-8b9f-f0328c833850","name":"Greek Pizza","type":"PIZZA","requiredMaterials":[{"id":"55864da9-b16a-4a7e-afaf-96b696e21f5e","name":"Black olives"},{"id":"a24dbca3-1656-4c13-95de-815441a53955","name":"Mozzarella"},{"id":"18eb5fb0-1811-4154-981d-762476768739","name":"Red onion"}],"preparationTimeInMinute":8,"price":10.0},{"id":"94269afe-75f9-4a12-84b1-c33cc612e34e","name":"Detroit Pizza","type":"PIZZA","requiredMaterials":[{"id":"65230b81-422d-44a4-83a3-4735dbc267da","name":"Brick cheese"},{"id":"bf44cf24-1496-4f6b-804d-b8e8188a4e5c","name":"Tomato sauce"},{"id":"065dc16c-1d74-452b-a5cb-e5f8a0be1edf","name":"Extra virgin olive oil"}],"preparationTimeInMinute":9,"price":9.0},{"id":"7f57cfe8-22da-43f1-9b22-73b7b89345fc","name":"Margherita","type":"PIZZA","requiredMaterials":[{"id":"b4a11116-e370-49db-a6d3-5b40bb086a70","name":"Features tomatoes"},{"id":"a24dbca3-1656-4c13-95de-815441a53955","name":"Mozzarella"},{"id":"065dc16c-1d74-452b-a5cb-e5f8a0be1edf","name":"Extra virgin olive oil"}],"preparationTimeInMinute":6,"price":5.0},{"id":"2fb14e14-657d-4346-98be-29564c802ea2","name":"Cola","type":"DRINK","requiredMaterials":[],"preparationTimeInMinute":0,"price":2.0},{"id":"ad983e8d-bdeb-47f3-a031-8a69edf4b83f","name":"Soda","type":"DRINK","requiredMaterials":[],"preparationTimeInMinute":0,"price":2.0}]
    
#### Create order

#### Request

`POST api/createOrder`

    curl -v -d '{"creditCardInfo": {"nameOnCard": "Onur Kayabasi","cardNumber": "1234123412341234","expirationMonth": 1,"expirationYear": 2022,"cvv": 123},"orderedMenuItems": [{"id": "c33c376d-d0b5-4ba0-b04e-7d306a8f9ee5","quantity": 1}]}' -H "Content-Type: application/json" -X POST http://localhost:8080/api/createOrder

#### Response

    HTTP/1.1 200 OK
    Date: Fri, 19 March 2021 15:11:16 GMT
    Content-Length: 50
    
    {"isSuccess":true,"message":"ORDER_IS_SUCCESSFUL"}
