import { Button, Card, Form, Input, notification } from "antd";
import React, { useEffect, useState } from "react";
import { LoginOutlined } from "@ant-design/icons";
import UserService from "../sevices/userService";

function Login() {
  const [loginForm] = Form.useForm();
  const [loading, setLoading] = useState(false);
  const login = (values) => {
    setLoading(true);
    UserService.login(values)
      .then((res) => {
        localStorage.setItem("adminUserToken", res.data.data);
        setLoading(false);
        window.location.href = "/admin/dashboard";
      })
      .catch((err) => {
        setLoading(false);
        notification.error({
          message: "Wrong credentials !! Login Failed ...",
        });
      });
  };
  useEffect(() => {
    if (localStorage.getItem("adminUserToken")) {
      window.location.href = "/admin/dashboard";
    }
  }, []);
  return (
    <div className="login-page">
      <Card className="login-card">
        <div className="login-title">Admin Login</div>
        <Form
          form={loginForm}
          onFinish={login}
          layout="vertical"
          requiredMark={false}
        >
          <Form.Item
            name="email"
            label="Email Address"
            rules={[
              {
                required: true,
                message: "Please enter your email address",
              },
              {
                type: "email",
                message:
                  "Invalid email address. Only use numbers, letters, or + - _ characters",
              },
            ]}
          >
            <Input className="login-input" id="email" />
          </Form.Item>
          <Form.Item
            name="password"
            label="Password"
            rules={[
              {
                required: true,
                message: "Please enter your password",
              },
            ]}
          >
            <Input.Password className="login-input" id="password" />
          </Form.Item>
          <Form.Item style={{ textAlign: "center" }}>
            <Button
              type="secondary"
              htmlType="submit"
              className="user-profile login-button"
              icon={<LoginOutlined />}
              size={30}
              style={{ border: "1px solid #8c8c8c" }}
              loading={loading}
            >
              Login{" "}
            </Button>
          </Form.Item>
        </Form>
      </Card>
    </div>
  );
}

export default Login;
