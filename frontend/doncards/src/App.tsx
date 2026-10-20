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


const router = createBrowserRouter(
  [
    {
      element: <Layout />,
      children: [
        {
          index: true,
          element: <HomePage />,
        },
        {
          path: "users/:userId",
          element: <PublicProfilePage />,
        },
        {
          path: "deck/:deckId",
          element: <DeckPage />,
        },
        {
          path: "deck/:deckId/learn",
          element: <LearnPage />,
        },
        {
          path: "register",
          element: <RegisterPage />,
        },
        {
          path: "login",
          element: <LoginPage />,
        },
        {
          path: "profile",
          element: <MyProfilePage />,
        },
        {
          path: "decks/create",
          element: <CreateDeckPage />,
        },
        {
          path: "*",
          element: <NotFoundPage />,
        },
      ],
    },
  ],
  {
    basename: "/Doncards",
  }
);


function App() {
  return <RouterProvider router={router} />;
}

export default App
