import { API_BASE_URL, defaultHeaders } from "./apiConfig";

export const loginApi = async (username, password) => {
  const res = await fetch(`${API_BASE_URL}/auth/login`, {
    method: "POST",
    headers: defaultHeaders(),
    body: JSON.stringify({ username, password }),
  });

  return res.json();
};
