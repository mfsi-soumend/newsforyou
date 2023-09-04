import React, { useEffect, useState } from "react";
import AgencyService from "../sevices/agencyService";
import CategoryService from "../sevices/categoryService";
import {
  Button,
  Card,
  Form,
  Select,
  Skeleton,
  Space,
  notification,
} from "antd";
import { SaveOutlined, CloseOutlined } from "@ant-design/icons";

function AgencyFeed() {
  const [agencyFeeds, setAgencyFeeds] = useState([]);
  const [allCategory, setAllCategory] = useState([]);
  const [allAgency, setAllAgency] = useState([]);
  const [buttonLoading, setButtonLoading] = useState(false);
  const [loading, setLoading] = useState(true);
  const [form] = Form.useForm();

  const createAgencyFeed = (values) => {
    setButtonLoading(true);
    AgencyService.createAgencyFeed(values)
      .then((res) => {
        notification.success({
          message: "New Agency Feed successfully created !!",
        });
        setButtonLoading(false);
        form.resetFields();
        setLoading(true);
        AgencyService.getAllAgencyFeed().then((res) => {
          setAgencyFeeds(res.data.data.agencyFeedResponseList);
          setLoading(false);
        });
      })
      .catch((err) => {
        notification.error({ message: err.response.data.error.message });
        setButtonLoading(false);
      });
  };
  useEffect(() => {
    AgencyService.getAllAgencyFeed().then((res) => {
      setAgencyFeeds(res.data.data.agencyFeedResponseList);

      CategoryService.getAllCategory().then((res) => {
        let catList = [];
        res.data.data.categoryResponseList.map((category) => {
          catList.push({
            label: category.categoryTitle,
            value: category.categoryId,
          });
        });
        setAllCategory(catList);
        AgencyService.getAllAgency().then((res) => {
          let agencyList = [];
          res.data.data.agencyResponseList.map((agency) => {
            agencyList.push({
              label: agency.agencyName,
              value: agency.agencyId,
              imgPath: agency.agencyLogoPath,
            });
          });
          setAllAgency(agencyList);
          setLoading(false);
        });
      });
    });
  }, []);

  return loading ? (
    <Skeleton active />
  ) : (
    <div style={{ minHeight: "78vh" }}>
      <div className="admin-right-section-title">Agency Feed</div>
      <div className="agency-card-wrapper">
        <Card className="agency-card" key="add-agency-feed">
          <div className="add-agency-title">Create Agency Feed</div>
          <Form
            form={form}
            onFinish={createAgencyFeed}
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
            >
              <Select
                className="login-input"
                placeholder="Select Agency"
                options={allAgency}
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
                options={allCategory}
              />
            </Form.Item>
            <Space>
              <Form.Item style={{ margin: "0" }}>
                <Button
                  type="secondary"
                  htmlType="submit"
                  className="user-profile login-button"
                  icon={<SaveOutlined />}
                  size={30}
                  style={{
                    border: "1px solid #8c8c8c",
                    marginTop: "5px",
                  }}
                  loading={buttonLoading}
                >
                  Save
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
        </Card>
        {agencyFeeds.map((agencyFeed) => {
          let category = allCategory.find(
            (item) => item.value === agencyFeed.categoryId
          );
          let agency = allAgency.find(
            (item) => item.value === agencyFeed.agencyId
          );
          return (
            <Card
              className="agency-card"
              key={agencyFeed.agencyFeedId}
              style={{ cursor: "pointer" }}
              onClick={() => {
                window.open(agencyFeed.agencyFeedUrl, "_blank");
              }}
            >
              <div className="agency-logo">
                {agency.imgPath ? (
                  <img src={agency.imgPath} alt={agency.label} />
                ) : (
                  <Skeleton.Image />
                )}
              </div>
              <div className="agency-name">{agency.label}</div>
              <div className="agency-category">{category.label}</div>
            </Card>
          );
        })}
      </div>
    </div>
  );
}

export default AgencyFeed;
