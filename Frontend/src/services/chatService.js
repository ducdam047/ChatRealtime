import { Client } from "@stomp/stompjs";

let stompClient = null;

export const connectSocket = (token, onMessage) => {
  stompClient = new Client({
    brokerURL: `ws://localhost:8080/ws?token=${token}`,
    onConnect: () => {
      stompClient.subscribe("/user/queue/message", (msg) => {
        onMessage(JSON.parse(msg.body));
      });
    },
  });
  stompClient.activate();
};

export const disconnectSocket = () => {
  stompClient?.deactivate();
  stompClient = null;
};

export const sendMessage = (chatId, content) => {
  stompClient?.publish({
    destination: `/app/chat.send/${chatId}`,
    body: JSON.stringify({ content, type: "TEXT" }),
  });
};
