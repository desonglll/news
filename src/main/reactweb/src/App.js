import logo from "./logo.svg";
import "./App.css";
import * as React from "react";
import Button from "@mui/material/Button";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import MainPage from "./component/MainPage";
import LoginPage from "./component/LoginPage";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<MainPage />} />
        <Route path="/mainPage" element={<MainPage />} />
        <Route path="/loginPage" element={<LoginPage />} />
      </Routes>
    </Router>
  );
}

export default App;
