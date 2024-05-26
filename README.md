<p align="center"> 
  <img width="500" src="https://imageio.forbes.com/specials-images/imageserve/649f20bcbeac799af7f8d8cd/Display-of-Stock-market-quotes--Stock-exchange-board--Led-digital-display-effect-/1960x0.jpg?format=jpg&width=1440"/>
</p>

# Information
The is the repository for the Softwaretechnology in Cyber-physical Systems portfolio.

The project is a web application that collects data from specific stocks/crypto-currencies and visualizes how it changes over time.

Created by Jeppe Holgaard Thomsen.

# Running the application
## Backend (Spring Boot, Java and MySQL)
The backend is a Java Spring Boot application that uses a MySQL database to store API data. The database is run in a Docker container.

To run the backend:
1. From the project root directory, navigate to the backend directory
```console
cd backend
```
2. Run the docker-compose file to start the MySQL database
```console
docker-compose up -d
```
3. Run the backend
```console
./gradlew bootRun
```
## Frontend (React, JavaScript and Node.js)
The frontend is a React application that visualizes the data from the backend.

To run the frontend:
1. From the project root directory, navigate to the frontend directory
```console
cd frontend
```
2. Install node dependencies
```console
npm install
```
4. Run the frontend
```console
npm start
```