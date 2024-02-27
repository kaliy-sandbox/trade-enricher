# Service description
The service exposes 1 endpoint - `/api/v1/enrich`

It accepts a csv file with the following format:
```
date,product_id,currency,price
20160101,1,EUR,10.0
20160101,2,EUR,20.1
20160101,3,EUR,30.34
```
and gives a response with following format:
```
date,product_name,currency,price
20160101,Treasury Bills Domestic,EUR,10.0
20160101,Corporate Bonds Domestic,EUR,20.1
20160101,REPO Domestic,EUR,30.34
```

# Assumptions
1. Extensibility is a concern
2. Big files should be supported
3. Fields order matters
4. product.csv is a static file that fits into memory

Additionally, I assume that there is an error in the original requirement: for the input `20160101,1,EUR,10.0` there is an
expected response `20160101,Treasury Bills Domestic,EUR,10` which changes `10.0` to `10`.

### Extensibility
The service is designed to be easily extensible.

To add new field enricher it's enough to create a new class implementing `FieldProvider` interface.

The package structure is done with package-per-feature in mind (almost all of the classes have package-private visibility).

### Big files

The service is designed to be able to handle big files. It uses Streams to process the input request and the same 
Writer for the output. It minimizes the memory requirement to consume the InputStream from the request.

#### Load testing

I performed a simple load test with 400mb file. The service was able to handle it. The memory consumption was stable:

![load testing results](doc/load_test.png "load testing results")

### Fields order

Although the fields order is not explicitly mentioned in the requirements, I assume that it matters. That's why 
FieldProviders are ordered using Spring `@Order` annotation.

### product.csv

This is a static file. I assume that it fits into memory. If it's not the case, it's possible to use a database or 
caching layer (like Redis) to store the data. To do that, it's enough to switch the implementation of the 
`ProductNameResolver` interface.

# Further improvements

1. It's possible to use the webflux to handle the request. In this case, we will have to work with the 
   `Flux<DataBuffer>` instead of streams. However, the `opencsv` library doesn't support it so some CSV logic should 
   be implementd.
