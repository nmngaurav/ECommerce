Order Api:
1) /order/add
Request: 
{
  "emailid": "string",
  "orderQuantity": 0,
  "productName": "string"
}

2) /order/delete
Request: 
{
  "id": 0
}

3) /order/listAll
Request: 
{}

4) /order/update
Request: 
{
  "emailid": "string",
  "id": 0,
  "orderQuantity": 0
}

5) /order/viewDetails
Request: 
{
  "id": 0
}

Product Api:
1) /product/add
Request:
{
  "availableQuantity": 0,
  "description": "string",
  "imageUrl": "string",
  "name": "string",
  "price": 0
}

2) /product/delete
Request:
{
  "id": 0
}

3) /product/listAll
Request: 
{}

4) /product/update
Request:
{
  "availableQuantity": 0,
  "description": "string",
  "id": 0,
  "imageUrl": "string",
  "name": "string",
  "price": 0
}

5) /product/viewDetails
Request:
{
  "id": 0
}
