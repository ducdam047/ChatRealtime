import { useEffect, useState } from "react";
import UserList from "../components/UserList";
import ChatBox from "../components/ChatBox";
import {
    connectSocket,
    disconnectSocket,
    sendMessage,
} from "../services/chatService";

import { getUsersApi } from "../api/userApi";
import {
    createChatRoomApi,
    getMessagesApi,
} from "../api/chatApi";

export default function ChatPage({ token, onLogout }) {
    const [users, setUsers] = useState([]);
    const [selectedUser, setSelectedUser] = useState(null);
    const [chatId, setChatId] = useState(null);
    const [messages, setMessages] = useState([]);
    const [text, setText] = useState("");
    const [loadingMessages, setLoadingMessages] = useState(false);

    const currentUser = sessionStorage.getItem("username");

    // ===============================
    // 1️⃣ CONNECT WEBSOCKET
    // ===============================
    useEffect(() => {
        if (!token) return;

        connectSocket(token, (msg) => {
            setMessages((prev) => [...prev, msg]);
        });

        return () => {
            disconnectSocket();
        };
    }, [token]);

    // ===============================
    // 2️⃣ LOAD USER LIST
    // ===============================
    useEffect(() => {
        if (!token) return;

        getUsersApi(token).then((res) => {
            if (res && res.data) {
                setUsers(res.data);
            }
        });
    }, [token]);

    // ===============================
    // 3️⃣ OPEN CHAT WITH USER
    // ===============================
    const openChat = async (user) => {
        setSelectedUser(user);
        setMessages([]);
        setLoadingMessages(true);

        try {
            const chatRes = await createChatRoomApi(user.username, token);

            // 🔥 CHẶN NGAY
            if (!chatRes || !chatRes.data || !chatRes.data.chatId) {
                throw new Error("Create chat room failed");
            }

            const chatId = chatRes.data.chatId;
            setChatId(chatId);

            const msgRes = await getMessagesApi(chatId, token);
            setMessages(msgRes?.data || []);
        } catch (e) {
            console.error("OPEN CHAT FAILED", e);
            setMessages([]);
        } finally {
            setLoadingMessages(false); // ✅ LUÔN tắt loading
        }
    };

    // ===============================
    // RENDER
    // ===============================
    return (
        <div className="chat-layout">
            {/* ===== LEFT: USER LIST ===== */}
            <div className="sidebar">
                <h3 className="sidebar-title">Users</h3>

                <div className="user-list">
                    {users.map((u) => (
                        <div
                            key={u.id}
                            className={`user-item ${selectedUser?.id === u.id ? "active" : ""
                                }`}
                            onClick={() => openChat(u)}
                        >
                            <div className="avatar">
                                {u.fullName?.charAt(0) || u.username.charAt(0)}
                            </div>

                            <div className="user-info">
                                <div className="name">{u.fullName || u.username}</div>
                                <div className="username">@{u.username}</div>
                            </div>
                        </div>
                    ))}
                </div>

                <button className="logout-btn" onClick={onLogout}>
                    Logout
                </button>
            </div>

            {/* ===== RIGHT: CHAT AREA ===== */}
            <div className="chat-panel">
                {selectedUser ? (
                    <>
                        <div className="chat-header">
                            Chat with <b>{selectedUser.username}</b>
                        </div>

                        <ChatBox
                            messages={messages}
                            currentUser={currentUser}
                            loading={loadingMessages}
                        />

                        <div className="input-area">
                            <input
                                value={text}
                                onChange={(e) => setText(e.target.value)}
                                placeholder="Type message..."
                            />
                            <button
                                disabled={!text.trim()}
                                onClick={() => {
                                    sendMessage(chatId, text);
                                    setText("");
                                }}
                            >
                                Send
                            </button>
                        </div>
                    </>
                ) : (
                    <div className="empty-chat-panel">
                        👉 Select a user to start chatting
                    </div>
                )}
            </div>
        </div>
    );
}
