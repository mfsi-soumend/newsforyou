import { Button, Form, Input, Modal, Select, Space, notification } from "antd";
import React, { useEffect, useState } from "react";
import CategoryService from "../sevices/categoryService";
import AgencyService from "../sevices/agencyService";
import { CloseOutlined } from "@ant-design/icons";
import NewsService from "../sevices/newsService";

function AddNews({ setAllNews, setNewsCount }) {
  const [open, setOpen] = useState(false);
  const [categories, setCategories] = useState([]);
  const [agencies, setAgencies] = useState([]);
  const [buttonLoading, setButtonLoading] = useState(false);
  const [form] = Form.useForm();

  const fetchNews = async (value) => {
    setButtonLoading(true);
    NewsService.fetchNews(value)
      .then((res) => {
        notification.success({ message: "News fetched successfully!!" });
        setButtonLoading(false);
        NewsService.getAllNewsForTable().then((res) => {
          setAllNews(res.data.data.newsList);
          setNewsCount(res.data.data.totalNewsCount);
        });
      })
      .catch((err) => {
        notification.error({ message: err.response.data.error.message });
        setButtonLoading(false);
      });
  };

  const validateURL = (_, value) => {
    if (!value) {
      return Promise.resolve();
    }
    const urlRegex =
      /^(?:(?:https?|ftp):\/\/)?(?:www\.)?([^\s/$.?#].[^\s]*\.[^\s]{2,})$/i;
    if (urlRegex.test(value)) {
      return Promise.resolve();
    }
    return Promise.reject("Please enter a valid RSS Feed URL");
  };

  useEffect(() => {
    CategoryService.getAllCategory().then((res) => {
      let catList = [];
      res.data.data.categoryResponseList.map((category) => {
        catList.push({
          label: category.categoryTitle,
          value: category.categoryId,
        });
      });
      setCategories(catList);
    });
    AgencyService.getAllAgency().then((res) => {
      let agencyList = [];
      res.data.data.agencyResponseList.map((agency) => {
        agencyList.push({
          label: agency.agencyName,
          value: agency.agencyId,
        });
      });
      setAgencies(agencyList);
    });
  }, []);
  return (
    <>
      <Button
        type="secondary"
        onClick={() => {
          form.resetFields();
          setOpen(true);
        }}
        className="add-news"
      >
        Add News
      </Button>
      <Modal
        title={<div className="modal-title">Add News from RSS Feed</div>}
        open={open}
        footer={false}
        onCancel={() => setOpen(false)}
        className="add-news-modal"
      >
        <Form
          form={form}
          onFinish={fetchNews}
          requiredMark={false}
          disabled={buttonLoading}
        >
          <Form.Item
            name="agencyId"
            rules={[
              {
                required: true,
                message: "Select Agency !!",
              },
            ]}
            style={{ marginTop: "20px" }}
          >
            <Select
              className="login-input"
              placeholder="Select Agency"
              options={agencies}
            />
          </Form.Item>
          <Form.Item
            name="categoryId"
            rules={[
              {
                required: true,
                message: "Select Category !!",
              },
            ]}
          >
            <Select
              className="login-input"
              placeholder="Select Category"
              options={categories}
            />
          </Form.Item>
          <Form.Item
            name="rssUrl"
            rules={[
              {
                required: true,
                message: "Please enter a RSS Feed URL",
              },
              {
                validator: validateURL,
              },
            ]}
          >
            <Input
              type="text"
              className="login-input"
              placeholder="Enter RSS Feed Url"
            />
          </Form.Item>
          <Space>
            <Form.Item style={{ margin: "0" }}>
              <Button
                type="secondary"
                htmlType="submit"
                className="user-profile login-button"
                size={30}
                style={{
                  border: "1px solid #8c8c8c",
                  marginTop: "5px",
                }}
                loading={buttonLoading}
              >
                Fetch
              </Button>
            </Form.Item>
            <Form.Item style={{ margin: "0" }}>
              <Button
                type="secondary"
                className="cancel-button login-button"
                icon={<CloseOutlined />}
                size={30}
                style={{
                  border: "1px solid #8c8c8c",
                  marginTop: "5px",
                }}
                onClick={() => {
                  if (!buttonLoading) {
                    form.resetFields();
                  }
                }}
                disabled={buttonLoading}
              >
                Cancel
              </Button>
            </Form.Item>
          </Space>
        </Form>
      </Modal>
    </>
  );
}

export default AddNews;
