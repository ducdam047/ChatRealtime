export const API_BASE_URL = "http://localhost:8080";

export const defaultHeaders = (token) => ({
  "Content-Type": "application/json",
  ...(token && { Authorization: `Bearer ${token}` }),
});
