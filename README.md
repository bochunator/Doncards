# Flash Cards

## About the project

My main goal was to create an Android application using a local SQLite database. The database is initially empty, so you'll need to fill it with your own vocabulary. The Daily mode fetches up to 30 vocabulary words and displays them. This functionality is specifically designed for the Android version.

The application currently does not require an internet connection.

I am also working on developing a website, including both the frontend and backend. In the future, I hope to connect the mobile application with the backend.

## Download

Offline version for android, no internet connection required:

https://github.com/bochunator/Doncards/releases/download/1.0/app-release.apk

## Website

Still in development (In the future, it will enable integration with the mobile app, allowing for
dynamic vocabulary downloads from a database):

https://bochunator.github.io/Doncards/

## Screenshots
![alt text](https://i.ibb.co/0qdVhtT/Screenshot-20230712-220804.png)
![alt text](https://i.ibb.co/92tJ2xM/Screenshot-20230712-221048.png)
![alt text](https://i.ibb.co/n7fYZ1L/Screenshot-20230712-212620.png)
![alt text](https://i.ibb.co/rvn8wKK/Screenshot-20230712-212814.png)
![alt text](https://i.ibb.co/RDcjSrh/Screenshot-20230712-212837.png)
![alt text](https://i.ibb.co/FJQHHYR/Screenshot-20230712-213052.png)
![alt text](https://i.ibb.co/k15yw0h/Screenshot-20230712-213652.png)
![alt text](https://i.ibb.co/XbPS7n4/Screenshot-20230712-213519.png)
![alt text](https://i.ibb.co/b6bp26j/Screenshot-20230712-213831.png)
![alt text](https://i.ibb.co/TMgB0Lz/Screenshot-20230712-214136.png)
![alt text](https://i.ibb.co/DD2PgZ9/Screenshot-20230712-214143.png)
![alt text](https://i.ibb.co/P4YGzM7/Screenshot-20230712-214151.png)
![alt text](https://i.ibb.co/svTt5pn/Screenshot-20230712-215012.png)
![alt text](https://i.ibb.co/sC9vhkb/Screenshot-20230712-215027.png)

### Dark theme
![alt text](https://i.ibb.co/dtGDcc3/Screenshot-20230712-212914.png)
![alt text](https://i.ibb.co/2nXx7Yg/Screenshot-20230712-212931.png)
![alt text](https://i.ibb.co/rtxvw00/Screenshot-20230712-212939.png)
![alt text](https://i.ibb.co/xGwkCmL/Screenshot-20230712-213011.png)
![alt text](https://i.ibb.co/cT3cYcG/Screenshot-20230712-213022.png)
![alt text](https://i.ibb.co/mJ8Lxvx/Screenshot-20230712-213106.png)
![alt text](https://i.ibb.co/vPCZVV9/Screenshot-20230712-213602.png)
![alt text](https://i.ibb.co/hBjg2Nm/Screenshot-20230712-213627.png)
![alt text](https://i.ibb.co/25W58mR/Screenshot-20230712-213847.png)
![alt text](https://i.ibb.co/CBnJMTQ/Screenshot-20230712-213928.png)
![alt text](https://i.ibb.co/q98wsZX/Screenshot-20230712-213935.png)
![alt text](https://i.ibb.co/RPbjHRC/Screenshot-20230712-214038.png)
![alt text](https://i.ibb.co/1JDnmsq/Screenshot-20230712-215121.png)
![alt text](https://i.ibb.co/G5bzpz8/Screenshot-20230712-215134.png)

## Technologies Used

### Android App
- **Programming Language:** Java
- **Database:** SQLite
- **IDE:** Android Studio

### Web Application
- **Frontend:**
   - **Framework:** React
   - **Language:** TypeScript
   - **State Management:** Redux Toolkit
   - **Additional Technology:** Electron

- **Backend:**
   - **Framework:** Spring Boot
   - **Language:** Java
   - **Database:** PostgreSQL

# Local Testing

### Prerequisites
- Ensure you have [Java JDK 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html) installed for the backend.
- Ensure you have [Node.js](https://nodejs.org/) installed for the frontend.

### Backend
1. Navigate to the backend directory:
    ```bash
    cd backend/doncards
    ```
2. Run the following command to start the backend server:
    ```bash
    ./mvnw spring-boot:run
    ```
   This will start the Spring Boot application, which will serve your backend API at `http://localhost:8080` by default.

   *Note:* The `application.properties` file in the backend has been configured with default environment variables, so you shouldn't encounter any issues starting the program.

### Frontend
1. Navigate to the frontend directory:
    ```bash
    cd frontend/doncards
    ```
2. Run the following command to start the frontend development server:
    ```bash
    npm run dev
    ```
   This will start the Vite development server, which will serve your frontend application at `http://localhost:5173` by default.

   *Note:* The frontend project includes two environment configuration files, `.env` and `.env.production`. The `.env` file is set up with a default URL for requests (`VITE_DONCARDS_BACKEND_URL="http://localhost:8080"`), so you don't need to manually set this environment variable. The application will use the appropriate configuration depending on the environment.
 