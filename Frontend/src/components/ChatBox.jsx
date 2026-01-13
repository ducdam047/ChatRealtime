import { useEffect, useRef } from "react";

export default function ChatBox({ messages, currentUser, loading }) {
  const endRef = useRef(null);

  // Auto scroll sau khi messages render
  useEffect(() => {
    if (!loading) {
      endRef.current?.scrollIntoView({ behavior: "smooth" });
    }
  }, [messages, loading]);

  // ===== LOADING STATE =====
  if (loading) {
    return (
      <div className="chat-container">
        <div className="loading">Loading messages...</div>
      </div>
    );
  }

  return (
    <div className="chat-container">
      {messages.length === 0 && (
        <div className="empty-chat">No messages yet</div>
      )}

      {messages.map((m, i) => {
        const isMe = m.sender === currentUser;

        return (
          <div
            key={i}
            className={`chat-message ${isMe ? "me" : "other"}`}
          >
            {!isMe && <div className="sender">{m.sender}</div>}
            <div className="bubble">{m.content}</div>
          </div>
        );
      })}

      <div ref={endRef} />
    </div>
  );
}
