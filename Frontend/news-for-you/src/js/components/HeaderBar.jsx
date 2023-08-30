import { Header } from "antd/es/layout/layout";
import { UserOutlined, LoginOutlined } from "@ant-design/icons";
import { Button } from "antd";
import React, { useState } from "react";

function HeaderBar() {
  const [user, setUser] = useState(false);
  return (
    <Header
      style={{
        display: "flex",
        alignItems: "center",
        justifyContent: "space-between",
      }}
    >
      <div
        className="logo"
        onClick={() => {
          window.location.href = "/";
        }}
      >
        NewsForYou
      </div>
      {user ? (
        <Button
          type="secondary"
          shape="circle"
          className="user-profile"
          icon={<UserOutlined />}
          size={30}
        />
      ) : (
        <Button
          type="secondary"
          className="user-profile"
          icon={<LoginOutlined />}
          size={30}
          onClick={() => {
            window.location.href = "/login";
          }}
        >
          Login{" "}
        </Button>
      )}
    </Header>
  );
}

export default HeaderBar;
