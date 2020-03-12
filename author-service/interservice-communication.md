# Synchronous Communication

We have a couple options in our current environment.  They are:

1. RestTemplate
2. FeignClient

## RestTemplate
Is a lower level, Http Client used to send requests to some network location.
RestTemplate is not specifically designed for use with Microservices but can
be use in MSA.

## FeignClient
Is a highly abstracted utility for sending requests between microservices 
applications.  They abstract the implementation of the request handling by
inferring information using a Spring MVC-like controller annotation to indicate
the request and response parameters of the foreign service API.

*Synchronous interservice communication should generally be paired with some kind
of fallback logic and circuit breaking behavior. In our environment this was 
provided by Hystrix*

# Asynchronous Communication
Synchronous communication is what we will usually use when we need extra data
from another service for the response. Asynchronous communication is very common
when operations produce side affects that do not necessarily need to be handled 
by the time of the response, but absolutely need to occur at some point.

In this scenario, instead of the services communicating directly, we place a
centralized messaging queue between them, which brokers requests (messages) between
services.  These messages can stay in the queue until the consuming service is
ready to process them.  The message producing service does not need to rely on
the consuming service to be available.  Additionally, the Message Queue can 
smoothly transition the ownership of handling the side affect between the affected
parties.