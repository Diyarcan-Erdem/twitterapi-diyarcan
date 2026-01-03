import { useState } from "react";

const API = "http://localhost:3200"; // Spring Boot portun 3200

function App() {
  // register form state
  const [regUsername, setRegUsername] = useState("");
  const [regPassword, setRegPassword] = useState("");

  // login form state
  const [loginUsername, setLoginUsername] = useState("");
  const [loginPassword, setLoginPassword] = useState("");

  const [message, setMessage] = useState("");

  async function register() {
    setMessage("");
    try {
      const res = await fetch(`${API}/auth/register`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username: regUsername, password: regPassword }),
      });

      const text = await res.text(); // backend String dönüyor
      if (!res.ok) throw new Error(text);

      setMessage(text);
    } catch (err) {
      setMessage(`Register error: ${err.message}`);
    }
  }

  async function login() {
    setMessage("");
    try {
      const res = await fetch(`${API}/auth/login`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username: loginUsername, password: loginPassword }),
      });

      const text = await res.text();
      if (!res.ok) throw new Error(text);

      setMessage(text);
    } catch (err) {
      setMessage(`Login error: ${err.message}`);
    }
  }

  return (
    <div style={{ padding: 20, fontFamily: "Arial" }}>
      <h2>Auth Test (Register / Login)</h2>

      <div style={{ display: "flex", gap: 30 }}>
        <div style={{ border: "1px solid #ccc", padding: 16, width: 300 }}>
          <h3>Register</h3>
          <input
            placeholder="username"
            value={regUsername}
            onChange={(e) => setRegUsername(e.target.value)}
            style={{ width: "100%", marginBottom: 8 }}
          />
          <input
            placeholder="password"
            type="password"
            value={regPassword}
            onChange={(e) => setRegPassword(e.target.value)}
            style={{ width: "100%", marginBottom: 8 }}
          />
          <button onClick={register} style={{ width: "100%" }}>
            Register
          </button>
        </div>

        <div style={{ border: "1px solid #ccc", padding: 16, width: 300 }}>
          <h3>Login</h3>
          <input
            placeholder="username"
            value={loginUsername}
            onChange={(e) => setLoginUsername(e.target.value)}
            style={{ width: "100%", marginBottom: 8 }}
          />
          <input
            placeholder="password"
            type="password"
            value={loginPassword}
            onChange={(e) => setLoginPassword(e.target.value)}
            style={{ width: "100%", marginBottom: 8 }}
          />
          <button onClick={login} style={{ width: "100%" }}>
            Login
          </button>
        </div>
      </div>

      <div style={{ marginTop: 20, padding: 10, background: "#f5f5f5" }}>
        <b>Result:</b>
        <div>{message}</div>
      </div>
    </div>
  );
}

export default App;
