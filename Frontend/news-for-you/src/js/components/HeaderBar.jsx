import { Header } from "antd/es/layout/layout";
import { UserOutlined, LoginOutlined } from "@ant-design/icons";
import { Button, Dropdown } from "antd";
import React from "react";

function HeaderBar() {
  const items = [
    {
      key: "0",
      label: (
        <a rel="noopener noreferrer" href="/admin/dashboard">
          Dashboard
        </a>
      ),
    },
    {
      key: "1",
      label: (
        <a rel="noopener noreferrer" href="/admin/profile">
          Profile
        </a>
      ),
    },
    {
      key: "2",
      label: (
        <a
          rel="noopener noreferrer"
          onClick={() => {
            localStorage.removeItem("adminUserToken");
            window.location.href = "/";
          }}
        >
          Logout
        </a>
      ),
    },
  ];
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
      {localStorage.getItem("adminUserToken") ? (
        <Dropdown
          menu={{
            items,
          }}
          placement="bottomRight"
        >
          <Button
            type="secondary"
            shape="circle"
            className="user-profile"
            icon={<UserOutlined />}
            size={30}
          />
        </Dropdown>
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
