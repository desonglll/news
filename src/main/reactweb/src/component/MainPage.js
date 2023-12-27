// src/components/MainPage.js
import React, { useEffect } from "react";
import { AppBar, Toolbar, Typography, Button, Container } from "@mui/material";
import { useNavigate } from "react-router-dom";

const MainPage = () => {
  useEffect(() => {}, []);
  const navigate = useNavigate();

  const handleExitClick = () => {
    navigate("/loginPage");
  };
  return (
    <Container
      style={{
        paddingTop: "20px",
        paddingBottom: "20px",
        marginLeft: "auto",
        marginRight: "auto",
      }}
    >
      <AppBar position="static">
        <Toolbar>
          <Typography variant="h6">NewsManager</Typography>
          <div style={{ flex: 1 }}></div>
          <Button color="inherit" onClick={handleExitClick}>
            Exit
          </Button>
        </Toolbar>
      </AppBar>
    </Container>
  );
};

export default MainPage;
