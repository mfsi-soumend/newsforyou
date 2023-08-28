import { Header } from "antd/es/layout/layout";
import { UserOutlined, LoginOutlined } from "@ant-design/icons";
import { Button } from "antd";
import React, { useState } from "react";

function HeaderBar() {
  const [user, setUser] = useState(true);
  return (
    <Header
      style={{
        display: "flex",
        alignItems: "center",
        justifyContent: "space-between",
      }}
    >
      <div className="logo">NewsForYou</div>
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
          shape="circle"
          className="user-profile"
          icon={<LoginOutlined />}
          size={30}
        />
      )}
    </Header>
  );
}

export default HeaderBar;
