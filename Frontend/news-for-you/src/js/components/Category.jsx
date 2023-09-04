import React, { useEffect, useState } from "react";
import CategoryService from "../sevices/categoryService";
import { Button, Form, Input, Result, Skeleton, notification } from "antd";
import { SmileOutlined, SaveOutlined, CloseOutlined } from "@ant-design/icons";

function getRandomColor() {
  const letters = "0123456789ABCDEF";
  let color = "#";
  for (let i = 0; i < 6; i++) {
    color += letters[Math.floor(Math.random() * 16)];
  }
  return color;
}

function Category() {
  const [categories, setCategories] = useState([]);
  const [selected, setSelected] = useState(null);
  const [loading, setLoading] = useState(true);
  const [buttonLoading, setButtonLoading] = useState(false);
  const [updateLoading, setUpdateLoading] = useState(false);
  const [form] = Form.useForm();
  const [updateForm] = Form.useForm();

  const updateCategory = (value) => {
    let payload = value;
    payload["categoryId"] = selected;
    setUpdateLoading(true);
    CategoryService.updateCategory(payload)
      .then((res) => {
        notification.success({
          message: value.categoryTitle + " updated succesfully !!",
        });
        setLoading(true);
        CategoryService.getAllCategory().then((res) => {
          setCategories(res.data.data.categoryResponseList);
          setLoading(false);
        });
        setUpdateLoading(false);
        updateForm.resetFields();
        setSelected(null);
      })
      .catch((err) => {
        notification.error({ message: err.response.data.error.message });
        setUpdateLoading(false);
      });
  };

  const addCategory = (value) => {
    setButtonLoading(true);
    CategoryService.saveCategory(value)
      .then((res) => {
        notification.success({
          message: value.categoryTitle + " saved succesfully !!",
        });
        setLoading(true);
        CategoryService.getAllCategory().then((res) => {
          setCategories(res.data.data.categoryResponseList);
          setLoading(false);
        });
        setButtonLoading(false);
        form.resetFields();
      })
      .catch((err) => {
        notification.error({ message: err.response.data.error.message });
        setButtonLoading(false);
      });
  };

  useEffect(() => {
    CategoryService.getAllCategory().then((res) => {
      setCategories(res.data.data.categoryResponseList);
      setLoading(false);
    });
  }, []);
  return loading ? (
    <Skeleton active />
  ) : (
    <div style={{ minHeight: "78vh" }}>
      <div className="admin-right-section-title">Categories</div>
      <div className="category-section">
        <div className="category-tag-wrapper">
          {categories.length === 0 ? (
            <Result
              icon={<SmileOutlined />}
              title="No news to display right now !!! Our team is collecting news for you. Stay connected :)"
            />
          ) : (
            categories.map((category) => {
              const randomBorderColor = getRandomColor();
              return selected && selected === category.categoryId ? (
                <div
                  className="category-tag"
                  style={{ borderRadius: "0" }}
                  key={category.categoryId + "edit"}
                  onMouseEnter={() => {
                    document
                      .getElementById("categoryTitle" + category.categoryId)
                      .focus();
                  }}
                >
                  <Form
                    form={updateForm}
                    onFinish={updateCategory}
                    style={{ display: "flex", columnGap: "10px" }}
                    requiredMark={false}
                    disabled={updateLoading}
                  >
                    <Form.Item
                      name="categoryTitle"
                      style={{ marginBottom: "0" }}
                      rules={[
                        {
                          required: true,
                          message: "Enter Category Title !!",
                        },
                      ]}
                    >
                      <Input
                        className="login-input"
                        id={"categoryTitle" + category.categoryId}
                        placeholder="Enter Category Title"
                      />
                    </Form.Item>
                    <Form.Item style={{ margin: "0" }}>
                      <Button
                        type="secondary"
                        htmlType="submit"
                        shape="circle"
                        className="user-profile"
                        icon={<SaveOutlined />}
                        size={30}
                        style={{
                          border: "1px solid #8c8c8c",
                          marginTop: "0",
                        }}
                        loading={updateLoading}
                      />
                    </Form.Item>
                    <Form.Item style={{ margin: "0" }}>
                      <Button
                        type="secondary"
                        className="cancel-button"
                        shape="circle"
                        icon={<CloseOutlined style={{ color: "red" }} />}
                        onClick={() => {
                          setSelected(null);
                          updateForm.resetFields();
                        }}
                        size={30}
                      />
                    </Form.Item>
                  </Form>
                </div>
              ) : (
                <div
                  className="category-tag"
                  key={category.categoryId}
                  style={{ borderColor: randomBorderColor }}
                  onClick={() => {
                    setSelected(category.categoryId);
                    updateForm.setFields([
                      {
                        name: "categoryTitle",
                        value: category.categoryTitle,
                      },
                    ]);
                  }}
                >
                  {category.categoryTitle}
                </div>
              );
            })
          )}
        </div>
        <div className="add-category">
          <div className="admin-right-section-title">Add Categories</div>
          <Form
            form={form}
            onFinish={addCategory}
            layout="vertical"
            requiredMark={false}
            disabled={buttonLoading}
          >
            <Form.Item
              name="categoryTitle"
              label="Category Title :"
              rules={[
                {
                  required: true,
                  message: "Add Category Title !!",
                },
              ]}
            >
              <Input className="login-input" id="categoryTitle" />
            </Form.Item>
            <Form.Item>
              <Button
                type="secondary"
                htmlType="submit"
                className="user-profile login-button"
                icon={<SaveOutlined />}
                size={30}
                style={{ border: "1px solid #8c8c8c", marginTop: "5px" }}
                loading={buttonLoading}
              >
                Save{" "}
              </Button>
            </Form.Item>
          </Form>
        </div>
      </div>
    </div>
  );
}

export default Category;
