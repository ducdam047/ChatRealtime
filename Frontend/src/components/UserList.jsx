export default function UserList({ users, selectedUser, onSelect }) {
  if (!users || users.length === 0) {
    return <div className="empty">No users</div>;
  }

  return (
    <div className="user-list">
      {users.map((u) => (
        <div
          key={u.id}
          className={`user-item ${
            selectedUser?.id === u.id ? "active" : ""
          }`}
          onClick={() => onSelect(u)}
        >
          <div className="avatar">
            {u.username.charAt(0).toUpperCase()}
          </div>

          <div className="info">
            <div className="name">
              {u.fullName || u.username}
            </div>
            <div className="username">
              @{u.username}
            </div>
          </div>
        </div>
      ))}
    </div>
  );
}
