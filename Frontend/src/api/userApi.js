import { API_BASE_URL, defaultHeaders } from "./apiConfig";

export const getUsersApi = async (token) => {
  const res = await fetch(`${API_BASE_URL}/api/users`, {
    headers: defaultHeaders(token),
  });

  return res.json();
};
