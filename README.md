# My Beer Partner

This app helps you to find which is the best beer partner for each character of Rick and Morty's universe. Some of the meetings could be ashtonishingly!

## Libraries and APIs

- __Retrofit 2.9.0__: Requests to REST services of the Rick and Morty's online API.

- __GSon converter 2.9.0__: Handling and managing JSON format of the API responses.

- __Room 2.4.2__: Handling and managing the SQLite database.

- __Dagger 2.41__: Handling and managing dependency injection.

- __MockK 1.12.3__: Mocking and verifying of elements for tests.

- __Turbine 0.6.1__: Library for testing flows.

## Architecture

The architecture used is a MVVVM following the next schema:

																				
VIEW <-> VIEWMODEL <-> DOMAIN (Use Cases) <-> DATA (Datasources + Repositories)

The DATA section is divided into two parts: 

- Local -> Using Room for storing locally the information in a database.

- Remote -> Using Retrofit to connect with the REST services of the API to download the information.