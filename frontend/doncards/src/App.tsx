import { Outlet, RouterProvider, createBrowserRouter } from 'react-router-dom';
import './App.css'
import HomePage from './pages/homePage/HomePage';
import PublicProfilePage from './pages/PublicProfilePage/PublicProfilePage';
import DeckPage from './pages/DeckPage/DeckPage';
import LearnPage from './pages/LearnPage/LearnPage';
import RegisterPage from './pages/RegisterPage/RegisterPage';
import LoginPage from './pages/LoginPage/LoginPage';
import Navbar from './components/Navbar/Navbar';
import MyProfilePage from './pages/MyProfilePage/MyProfilePage';
import CreateDeckPage from './pages/CreateDeckPage/CreateDeckPage';
import NotFoundPage from './pages/NotFoundPage/NotFoundPage';

function Layout() {
  return (
    <>
      <Navbar />
      <Outlet />
    </>
  );
}

const router = createBrowserRouter([
  {
    path: "/Doncards/",
    element: <Layout />,
    children: [
      {
        path: "/Doncards/",
        element: <HomePage />
      },
      {
        path: "/Doncards/users/:userId",
        element: <PublicProfilePage />
      },
      {
        path: "/Doncards/deck/:deckId",
        element: <DeckPage />
      },
      {
        path: "/Doncards/deck/:deckId/learn",
        element: <LearnPage />
      },
      {
        path: "/Doncards/register",
        element: <RegisterPage />
      },
      {
        path: "/Doncards/login",
        element: <LoginPage />
      },
      {
        path: "/Doncards/profile",
        element: <MyProfilePage />,
      },
      {
        path: "/Doncards/decks/create",
        element: <CreateDeckPage />,
      },
      {
        path: "*",
        element: <NotFoundPage />
      }
    ]
  }
]);

function App() {
  return <RouterProvider router={router} />;
}

export default App
