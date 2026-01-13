import { useState } from "react";
import { loginApi } from "../api/authApi";

export default function LoginPage({ onLoginSuccess }) {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);

  const login = async () => {
    if (!username || !password) {
      alert("Please enter username and password");
      return;
    }

    try {
      setLoading(true);

      const result = await loginApi(username, password);

      if (result.code === 200 && result.data?.token) {
        sessionStorage.setItem("token", result.data.token);
        sessionStorage.setItem("username", username);
        onLoginSuccess(result.data.token);
      } else {
        alert("Login failed");
      }
    } catch (e) {
      alert("Login error");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="login-container">
      <h2>Login</h2>

      <input
        placeholder="Username"
        value={username}
        onChange={(e) => setUsername(e.target.value)}
      />

      <input
        type="password"
        placeholder="Password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
      />

      <button onClick={login} disabled={loading}>
        {loading ? "Logging in..." : "Login"}
      </button>
    </div>
  );
}
