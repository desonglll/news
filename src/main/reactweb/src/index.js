// index.js 或 App.js

import React from "react";
import ReactDOM from "react-dom/client";
import { BrowserRouter as Router } from "react-router-dom";
import App from "./App"; // 或你的应用程序的主组件
import MainPage from "./component/MainPage";

const root = document.getElementById("root");

// 从 react-dom/client 中导入 createRoot
const rootElement = ReactDOM.createRoot(root);
rootElement.render(<App />);
