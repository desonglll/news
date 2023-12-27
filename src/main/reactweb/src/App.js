import logo from "./logo.svg";
import "./App.css";
import * as React from "react";
import Button from "@mui/material/Button";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import MainPage from "./component/MainPage";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<MainPage />} />
        <Route path="/mainPage" />
        <Route path="/loginPage" />
      </Routes>
    </Router>
  );
}

export default App;
