import { useState } from "react";
import LoginPage from "./pages/LoginPage";
import ChatPage from "./pages/ChatPage";
import "./App.css";

function App() {
  const [token, setToken] = useState(sessionStorage.getItem("token"));

  const logout = () => {
    sessionStorage.clear();
    setToken(null);
  };

  return token ? (
    <ChatPage token={token} onLogout={logout} />
  ) : (
    <LoginPage onLoginSuccess={setToken} />
  );
}

export default App;
