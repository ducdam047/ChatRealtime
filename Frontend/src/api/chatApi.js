import { API_BASE_URL, defaultHeaders } from "./apiConfig";

export const createChatRoomApi = async (username, token) => {
  const res = await fetch(
    `${API_BASE_URL}/api/chatrooms/create/${username}`,
    {
      method: "POST",
      headers: defaultHeaders(token),
    }
  );

  return res.json();
};

export const getMessagesApi = async (chatId, token) => {
  const res = await fetch(
    `${API_BASE_URL}/api/messages/${chatId}`,
    {
      headers: defaultHeaders(token),
    }
  );

  return res.json();
};
